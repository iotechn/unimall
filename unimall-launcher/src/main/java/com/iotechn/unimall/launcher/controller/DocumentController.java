package com.iotechn.unimall.launcher.controller;

import com.iotechn.unimall.launcher.exception.LauncherExceptionDefinition;
import com.iotechn.unimall.launcher.manager.ApiManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-09-13
 * Time: 上午9:31
 */
@Controller
@RequestMapping("/info")
public class DocumentController implements InitializingBean {

    @Autowired
    private ApiManager apiManager;

    private List exceptionDefinitionList;

    @RequestMapping("/")
    public ModelAndView index() {
        return group("user");
    }

    @RequestMapping("/group/{gp}")
    public ModelAndView group(@PathVariable("gp") String group) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("document");
        modelAndView.addObject("model", apiManager.generateGroupModel(group));
        modelAndView.addObject("groupNames", apiManager.generateDocumentModel().getGroups());
        return modelAndView;
    }


    @RequestMapping("/api/{gp}/{mt}")
    public ModelAndView api(@PathVariable("gp") String gp, @PathVariable("mt") String mt) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("method");
        modelAndView.addObject("methods", apiManager.methods(gp));
        modelAndView.addObject("model",apiManager.generateMethodModel(gp,mt));
        modelAndView.addObject("gp",gp);
        modelAndView.addObject("exceptionList", exceptionDefinitionList);
        return modelAndView;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        exceptionDefinitionList = new LinkedList<>();
        Field[] fields = LauncherExceptionDefinition.class.getFields();
        for (Field field : fields) {
            exceptionDefinitionList.add(field.get(LauncherExceptionDefinition.class));
        }
    }
}
