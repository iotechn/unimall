package com.dobbinsoft.unimall.runner.controller;


import com.dobbinsoft.fw.core.Const;
import com.dobbinsoft.fw.core.entiy.inter.IdentityOwner;
import com.dobbinsoft.fw.core.entiy.inter.PermissionOwner;
import com.dobbinsoft.fw.launcher.inter.AfterFileUpload;
import com.dobbinsoft.fw.launcher.inter.BeforeFileUpload;
import com.dobbinsoft.fw.launcher.inter.BeforeFileUploadPath;
import com.dobbinsoft.fw.launcher.permission.IAdminAuthenticator;
import com.dobbinsoft.fw.launcher.permission.IUserAuthenticator;
import com.dobbinsoft.fw.support.storage.StorageClient;
import com.dobbinsoft.fw.support.storage.StoragePrivateResult;
import com.dobbinsoft.fw.support.storage.StorageRequest;
import com.dobbinsoft.fw.support.storage.StorageResult;
import com.dobbinsoft.fw.support.utils.JacksonUtil;
import com.dobbinsoft.fw.support.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rize on 2019/2/13.
 */
@Controller
@RequestMapping("/upload")
public class FileUploadController {

    @Autowired
    private IUserAuthenticator userAuthenticator;

    @Autowired
    private StorageClient storageClient;

    @Autowired
    private IAdminAuthenticator adminAuthenticator;

    @Autowired(required = false)
    private AfterFileUpload afterFileUpload;

    @Autowired(required = false)
    private BeforeFileUpload beforeFileUpload;

    @Autowired(required = false)
    private BeforeFileUploadPath beforeFileUploadPath;

    /**
     * 后台通过服务器间接传文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/admin")
    @ResponseBody
    public Object createAdmin(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (this.beforeFileUpload != null) {
            this.beforeFileUpload.before(request);
        }
        String accessToken = request.getHeader(Const.ADMIN_ACCESS_TOKEN);
        PermissionOwner admin = adminAuthenticator.getAdmin(accessToken);
        if (admin != null) {
            return commonsUpload(file);
        }
        throw new RuntimeException("权限不足");
    }

    /**
     * 后台通过服务器代理间接上传 私密文件（非公开URL）
     *
     * @param file
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @PostMapping("/admin/private")
    @ResponseBody
    public Object createAdminPrivate(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (this.beforeFileUpload != null) {
            this.beforeFileUpload.before(request);
        }
        String accessToken = request.getHeader(Const.ADMIN_ACCESS_TOKEN);
        PermissionOwner admin = adminAuthenticator.getAdmin(accessToken);
        if (admin != null) {
            InputStream inputStream = null;
            Map<String, Object> data = new HashMap<>();
            try {
                String ext = StringUtils.getFileExtension(file.getOriginalFilename());
                String uuid = StringUtils.uuid();
                StorageRequest storageRequest = new StorageRequest();
                storageRequest.setContentType(file.getContentType());
                storageRequest.setFilename(uuid + "." + ext);
                storageRequest.setSize(file.getSize());
                inputStream = file.getInputStream();
                storageRequest.setIs(inputStream);
                storageRequest.setPath("private");
                StoragePrivateResult result = storageClient.savePrivate(storageRequest);
                if (this.afterFileUpload != null) {
                    this.afterFileUpload.afterPrivate(storageRequest.getFilename(), result.getUrl(), result.getKey(), file.getSize());
                }
                if (result.isSuc()) {
                    data.put("key", result.getKey());
                    data.put("url", result.getUrl());
                    data.put("errno", 200);
                    data.put("errmsg", "成功");
                    return data;
                }
            } catch (IOException e) {
                throw new RuntimeException("网络错误");
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }
        throw new RuntimeException("权限不足");
    }

    @PostMapping("/user")
    @ResponseBody
    public Object createUser(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        if (this.beforeFileUpload != null) {
            this.beforeFileUpload.before(request);
        }
        String accessToken = request.getHeader(Const.USER_ACCESS_TOKEN);
        IdentityOwner user = userAuthenticator.getUser(accessToken);
        if (user != null) {
            return commonsUpload(file);
        }
        throw new RuntimeException("权限不足");
    }

    private Object commonsUpload(MultipartFile file) throws IOException {
        InputStream inputStream = null;
        Map<String, Object> data = new HashMap<>();
        try {
            String ext = StringUtils.getFileExtension(file.getOriginalFilename());
            String uuid = StringUtils.uuid();
            StorageRequest storageRequest = new StorageRequest();
            storageRequest.setContentType(file.getContentType());
            storageRequest.setFilename(uuid + "." + ext);
            storageRequest.setSize(file.getSize());
            inputStream = file.getInputStream();
            storageRequest.setIs(inputStream);
            if (this.beforeFileUploadPath != null) {
                storageRequest.setPath(this.beforeFileUploadPath.setPath("commons"));
            } else {
                storageRequest.setPath("commons");
            }
            StorageResult result = storageClient.save(storageRequest);
            if (afterFileUpload != null) {
                afterFileUpload.afterPublic(storageRequest.getFilename(), result.getUrl(), file.getSize());
            }
            if (result.isSuc()) {
                data.put("name", file.getOriginalFilename());
                data.put("url", result.getUrl());
                data.put("errno", 200);
                data.put("size", file.getSize());
                data.put("errmsg", "成功");
            }
        } catch (IOException e) {
            throw new RuntimeException("网络错误");
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return data;
    }

    /**
     * 上传文件到文件系统
     *
     * @param file
     * @param fsf  FileSystem File 上传覆盖具体某个文件
     * @return
     */
    @PostMapping("/local")
    @ResponseBody
    public Object local(@RequestParam("file") MultipartFile file, String fsf, HttpServletRequest request) throws IOException {
        String accessToken = request.getHeader(Const.ADMIN_ACCESS_TOKEN);
        IdentityOwner user = userAuthenticator.getUser(accessToken);
        if (user != null) {
            int i = fsf.lastIndexOf("/");
            if (i > 0) {
                String substring = fsf.substring(0, i);
                File dir = new File(substring);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                File fsFile = new File(fsf);
                if (fsFile.exists()) {
                    fsFile.delete();
                }
                InputStream fis = file.getInputStream();
                OutputStream os = new FileOutputStream(fsf);
                FileCopyUtils.copy(fis, os);
                fis.close();
                os.close();
                Map<String, Object> data = new HashMap<>();
                data.put("errno", 200);
                data.put("errmsg", "成功");
                return data;
            } else {
                throw new RuntimeException("文件系统路径不正确");
            }
        }
        throw new RuntimeException("权限不足");
    }


    /**
     * Post请求
     */
    @RequestMapping(method = RequestMethod.POST)
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> responseJson = new HashMap<>();
        responseJson.put("code", 200);
        for (String key : parameterMap.keySet()) {
            responseJson.put(key, parameterMap.get(key)[0]);
        }
        response(request, response, JacksonUtil.toJSONString(responseJson), HttpServletResponse.SC_OK);
    }

    /**
     * 服务器响应结果
     *
     * @param request
     * @param response
     * @param results
     * @param status
     * @throws IOException
     */
    private void response(HttpServletRequest request, HttpServletResponse response, String results, int status)
            throws IOException {
        String callbackFunName = request.getParameter("callback");
        response.addHeader("Content-Length", String.valueOf(results.length()));
        if (callbackFunName == null || callbackFunName.equalsIgnoreCase(""))
            response.getWriter().println(results);
        else
            response.getWriter().println(callbackFunName + "( " + results + " )");
        response.setStatus(status);
        response.flushBuffer();
    }

    /**
     * 服务器响应结果
     */
    private void response(HttpServletRequest request, HttpServletResponse response, String results) throws IOException {
        String callbackFunName = request.getParameter("callback");
        if (callbackFunName == null || callbackFunName.equalsIgnoreCase(""))
            response.getWriter().println(results);
        else
            response.getWriter().println(callbackFunName + "( " + results + " )");
        response.setStatus(HttpServletResponse.SC_OK);
        response.flushBuffer();
    }

}
