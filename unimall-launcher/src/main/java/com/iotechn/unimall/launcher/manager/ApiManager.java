package com.iotechn.unimall.launcher.manager;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.launcher.exception.ExceptionDefinition;
import com.iotechn.unimall.launcher.exception.LauncherServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-08
 * Time: 下午10:52
 */
@Component
public class ApiManager implements InitializingBean,ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ApiManager.class);

    private Map<String, Map<String, Method>> methodCacheMap = new HashMap<>();

    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<Class> classList = new LinkedList<>();
        String[] serviceArray = applicationContext.getBeanNamesForAnnotation(Service.class);
        for (String service : serviceArray) {
            Object bean = applicationContext.getBean(service);
            Class<?>[] interfaces = bean.getClass().getInterfaces();
            if (interfaces != null && interfaces.length > 0) {
                for (Class clazz : interfaces) {
                    if (clazz.getAnnotation(HttpOpenApi.class) != null) {
                        classList.add(clazz);
                    }
                }
            }
        }

        for (Class clazz : classList) {
            this.registerService(clazz);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void registerService(Class<?> targetClass) throws ServiceException {
        HttpOpenApi httpOpenApiAnnotation = targetClass.getDeclaredAnnotation(HttpOpenApi.class);
        if (httpOpenApiAnnotation != null) {
            String group = httpOpenApiAnnotation.group();
            Method[] methods = targetClass.getMethods();
            Map<String, Method> tempMap = methodCacheMap.get(group);
            if (tempMap == null) {
                tempMap = new TreeMap<>();
                methodCacheMap.put(group, tempMap);
            }
            for (Method method : methods) {
                HttpMethod httpMethod = method.getAnnotation(HttpMethod.class);
                if(httpMethod != null){
                    String key = method.getName();
                    Method methodQuery = tempMap.get(key);
                    if (methodQuery != null) {
                        throw new LauncherServiceException(ExceptionDefinition.LAUNCHER_API_REGISTER_FAILED);
                    }
                    tempMap.put(key, method);
                    logger.info("[注册OpenApi] " + group + "." + method.getName());
                }else{
                    logger.info("[注册OpenApi 失败] 没有注解." + method.getName());
                }

            }
        } else {
            throw new LauncherServiceException(ExceptionDefinition.LAUNCHER_API_REGISTER_FAILED);
        }
    }


    public Method getMethod(String group, String name) {
        Map<String, Method> tempMap = methodCacheMap.get(group);
        if (tempMap != null) {
            return tempMap.get(name);
        }
        return null;
    }

    /**
     * 获取文档模型的方法
     * @return
     */
    private ApiDocumentModel apiDocumentModelCache = null;

    public ApiDocumentModel generateDocumentModel() {
        if(apiDocumentModelCache != null){
            return apiDocumentModelCache;
        }
        Set<String> gpKeys = methodCacheMap.keySet();
        ApiDocumentModel apiDocumentModel = new ApiDocumentModel();
        List<ApiDocumentModel.Group> groups = new LinkedList<>();
        apiDocumentModel.setGroups(groups);
        for(String gpKey : gpKeys){
            ApiDocumentModel.Group group = new ApiDocumentModel.Group();
            groups.add(group);
            group.setName(gpKey);
            List<ApiDocumentModel.Method> docMethods = new LinkedList<>();
            group.setMethods(docMethods);
            Map<String, Method> methodMap = methodCacheMap.get(gpKey);
            Set<String> methodNameKeys = methodMap.keySet();
            for(String methodNameKey : methodNameKeys){
                Method method = methodMap.get(methodNameKey);
                //获取参数
                List<ApiDocumentModel.Parameter> docParameters = new LinkedList<>();
                Parameter[] parameters = method.getParameters();
                if(parameters != null && parameters.length > 0) {
                    for(Parameter parameter : parameters){
                        HttpParam httpParam = parameter.getAnnotation(HttpParam.class);
                        ApiDocumentModel.Parameter docParameter = new ApiDocumentModel.Parameter();
                        if (docParameter == null) {
                            logger.info("[Api] 参数未注解:" + method.getName());
                        }
                        docParameter.setName(httpParam.name());
                        docParameter.setDescription(httpParam.description());
                        String typeName = parameter.getType().getTypeName();
                        if(typeName.startsWith("[L")){
                            typeName = typeName.substring(2) + "[]";
                        }
                        docParameter.setParamType(typeName);
                        docParameter.setType(httpParam.type());
                        docParameter.setRequired(parameter.getAnnotation(NotNull.class) != null);
                        docParameters.add(docParameter);
                    }
                }
                ApiDocumentModel.Method docMethod = new ApiDocumentModel.Method();
                docMethod.setParameters(docParameters);
                HttpMethod httpMethod = method.getAnnotation(HttpMethod.class);
                if(httpMethod != null){
                    docMethod.setDescription(httpMethod.description());
                    docMethod.setName(method.getName());
                    Type returnType = method.getGenericReturnType();
                    String retType = returnType.toString();
                    if(retType.startsWith("interface")){
                        if(retType.startsWith("interface [L")){
                            retType = retType.substring(12);
                        }else {
                            retType = retType.substring(10);
                        }
                    }else if(retType.startsWith("class")){
                        if(retType.startsWith("class [L")) {
                            retType = retType.substring(8);
                        }else {
                            retType = retType.substring(6);
                        }
                    }
                    docMethod.setRetType(retType);
                    if(retType.startsWith("cn.easycampus")){
                        //若返回值类型为复杂类型
                        List<ApiDocumentModel.Field> fieldList = new ArrayList<>();
                        Class returnClass = null;
                        if(returnType instanceof Class){
                            returnClass = (Class) returnType;
                        }else if(returnType instanceof ParameterizedType){
                            ParameterizedType parameterizedType = (ParameterizedType) returnType;
                            returnClass = (Class) parameterizedType.getActualTypeArguments()[0];
                        }
                        if(returnClass != null){
                            Field[] declaredFields = returnClass.getDeclaredFields();
                            for(Field field : declaredFields){
                                ApiDocumentModel.Field docField = new ApiDocumentModel.Field();
                                docField.setName(field.getName());
                                docField.setType(field.getType().toString());
                                fieldList.add(docField);
                            }
                            docMethod.setRetObj(fieldList);
                        }

                    }
                    docMethods.add(docMethod);

                }else{
                    logger.info("生成文档失败:" + method.getName());
                }
            }
        }
        apiDocumentModelCache = apiDocumentModel;
        return apiDocumentModel;
    }

    public ApiDocumentModel.Group generateGroupModel(String group) {
        ApiDocumentModel apiDocumentModel = generateDocumentModel();
        List<ApiDocumentModel.Group> groups = apiDocumentModel.getGroups();
        for (ApiDocumentModel.Group gp : groups){
            if(gp.getName().equals(group)){
                return gp;
            }
        }
        return null;
    }

    public ApiDocumentModel.Method generateMethodModel(String gp, String mt){
        ApiDocumentModel apiDocumentModel = generateDocumentModel();
        List<ApiDocumentModel.Group> groups = apiDocumentModel.getGroups();
        for(ApiDocumentModel.Group group : groups){
            if(group.getName().equals(gp)){
                List<ApiDocumentModel.Method> methods = group.getMethods();
                for(ApiDocumentModel.Method method : methods){
                    if(method.getName().equals(mt)){
                        return method;
                    }
                }
            }
        }
        return null;
    }


    public List<ApiDocumentModel.Method> methods(String gp){
        for(ApiDocumentModel.Group group : generateDocumentModel().getGroups()){
            if(group.getName().equals(gp)){
                return group.getMethods();
            }
        }
        return null;
    }

}
