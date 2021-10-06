package com.iotechn.unimall.data.open;

import cn.hutool.crypto.asymmetric.RSA;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.data.domain.OpenPlatformClientDO;
import com.iotechn.unimall.data.domain.OpenPlatformNotifyDO;
import com.iotechn.unimall.data.mapper.OpenPlatformClientMapper;
import com.iotechn.unimall.data.mapper.OpenPlatformNotifyMapper;
import com.dobbinsoft.fw.support.component.open.OpenPlatformStorageStrategy;
import com.dobbinsoft.fw.support.component.open.model.OPClient;
import com.dobbinsoft.fw.support.component.open.model.OPClientPermission;
import com.dobbinsoft.fw.support.component.open.model.OPNotify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: OpenPlatformStorageDatabaseImpl
 * Description: TODO 缓存
 *
 * @author: e-weichaozheng
 * @date: 2021-04-25
 */
@Component
public class OpenPlatformStorageDatabaseImpl implements OpenPlatformStorageStrategy {

    @Autowired
    private OpenPlatformClientMapper openPlatformClientMapper;

    @Autowired
    private OpenPlatformNotifyMapper openPlatformNotifyMapper;

    @Override
    public boolean checkClientExists(String clientCode) {
        return openPlatformClientMapper.selectCount(new QueryWrapper<OpenPlatformClientDO>().eq("code", clientCode)) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OPClient initClient(String clientCode, String notifyUrl, String publicKey) {
        if (openPlatformClientMapper.selectCount(new QueryWrapper<OpenPlatformClientDO>().eq("code", clientCode)) > 0) {
            return null;
        }
        RSA rsa = new RSA();
        // 生成一对RSA密钥
        String privateKeyBase64 = rsa.getPrivateKeyBase64();
        String publicKeyBase64 = rsa.getPublicKeyBase64();
        OpenPlatformClientDO openPlatformClientDO = new OpenPlatformClientDO();
        openPlatformClientDO.setCode(clientCode);
        openPlatformClientDO.setNotifyUrl(notifyUrl);
        openPlatformClientDO.setPublicKey1(publicKeyBase64);
        openPlatformClientDO.setPublicKey2(publicKey);
        openPlatformClientDO.setPrivateKey1(privateKeyBase64);
        openPlatformClientDO.setPermissionList("[]");
        if (openPlatformClientMapper.insert(openPlatformClientDO) > 0) {
            OPClient opClient = new OPClient();
            opClient.setCode(clientCode);
            opClient.setNotifyUrl(notifyUrl);
            opClient.setPublicKey1(publicKeyBase64);
            opClient.setPublicKey2(publicKey);
            opClient.setPermissionList(new ArrayList<>());
            openPlatformClientDO.setPrivateKey1(privateKeyBase64);
            return opClient;
        }
        return null;
    }

    @Override
    public boolean setClientNotifyUrl(String clientCode, String notifyUrl) {
        OpenPlatformClientDO openPlatformClientDO = new OpenPlatformClientDO();
        openPlatformClientDO.setNotifyUrl(notifyUrl);
        return openPlatformClientMapper.update(openPlatformClientDO, new QueryWrapper<OpenPlatformClientDO>().eq("code", clientCode)) > 0;
    }

    @Override
    public boolean setClientPublicKey(String clientCode, String publicKey) {
        OpenPlatformClientDO openPlatformClientDO = new OpenPlatformClientDO();
        openPlatformClientDO.setPublicKey2(publicKey);
        return openPlatformClientMapper.update(openPlatformClientDO, new QueryWrapper<OpenPlatformClientDO>().eq("code", clientCode)) > 0;
    }

    @Override
    public OPClient getClient(String clientCode) {
        OpenPlatformClientDO openPlatformClientDO = openPlatformClientMapper.selectOne(new QueryWrapper<OpenPlatformClientDO>().eq("code", clientCode));
        OPClient opClient = new OPClient();
        opClient.setPublicKey1(openPlatformClientDO.getPublicKey1());
        opClient.setPublicKey2(openPlatformClientDO.getPublicKey2());
        opClient.setCode(openPlatformClientDO.getCode());
        if (StringUtils.isEmpty(openPlatformClientDO.getPermissionList())) {
            opClient.setPermissionList(new ArrayList<>());
        } else {
            List<OPClientPermission> permissionList = JSONObject.parseArray(openPlatformClientDO.getPermissionList(), OPClientPermission.class);
            opClient.setPermissionList(permissionList);
        }
        return opClient;
    }

    @Override
    public boolean setClientPermissionList(String clientCode, List<OPClientPermission> permissionList) {
        OpenPlatformClientDO openPlatformClientDO = new OpenPlatformClientDO();
        openPlatformClientDO.setPermissionList(JSONObject.toJSONString(permissionList));
        return openPlatformClientMapper.update(openPlatformClientDO, new QueryWrapper<OpenPlatformClientDO>().eq("code", clientCode)) > 0;
    }

    @Override
    public boolean checkApiPermission(String clientCode, String group, String method) {
        OpenPlatformClientDO openPlatformClientDO = openPlatformClientMapper.selectOne(new QueryWrapper<OpenPlatformClientDO>().eq("code", clientCode));
        List<OPClientPermission> permissionList = JSONObject.parseArray(openPlatformClientDO.getPermissionList(), OPClientPermission.class);
        OPClientPermission permission = new OPClientPermission();
        permission.setGroup(group);
        permission.setMethod(method);
        return permissionList.contains(permission);
    }

    @Override
    public OPClient getClientWithPK(String clientCode) {
        OpenPlatformClientDO openPlatformClientDO = openPlatformClientMapper.selectOne(new QueryWrapper<OpenPlatformClientDO>().eq("code", clientCode));
        OPClient opClient = new OPClient();
        opClient.setPublicKey1(openPlatformClientDO.getPublicKey1());
        opClient.setPrivateKey1(openPlatformClientDO.getPrivateKey1());
        opClient.setPublicKey2(openPlatformClientDO.getPublicKey2());
        opClient.setCode(openPlatformClientDO.getCode());
        if (StringUtils.isEmpty(openPlatformClientDO.getPermissionList())) {
            opClient.setPermissionList(new ArrayList<>());
        } else {
            List<OPClientPermission> permissionList = JSONObject.parseArray(openPlatformClientDO.getPermissionList(), OPClientPermission.class);
            opClient.setPermissionList(permissionList);
        }
        return opClient;
    }

    @Override
    public String storeNotify(OPNotify opNotify) {
        OpenPlatformNotifyDO openPlatformNotifyDO = new OpenPlatformNotifyDO();
        openPlatformNotifyDO.setClientCode(opNotify.getClientCode());
        openPlatformNotifyDO.setNextNotify(opNotify.getNextNotify());
        openPlatformNotifyDO.setParams(JSONObject.toJSONString(opNotify.getParams()));
        openPlatformNotifyDO.setStatus(opNotify.getStatus());
        openPlatformNotifyDO.setTimes(opNotify.getTimes());

        if (openPlatformNotifyMapper.insert(openPlatformNotifyDO) > 0) {
            return openPlatformNotifyDO.getId() + "";
        }
        return null;
    }

    @Override
    public List<OPNotify> getNeedNotify() {
        QueryWrapper<OpenPlatformNotifyDO> queryWrapper = new QueryWrapper<OpenPlatformNotifyDO>().eq("status", 0).gt("next_notify", new Date());
        Integer count = openPlatformNotifyMapper.selectCount(queryWrapper);
        if (count == 0) {
            return new ArrayList<>();
        }
        List<OpenPlatformNotifyDO> openPlatformNotifyDOS = openPlatformNotifyMapper.selectList(queryWrapper);
        return openPlatformNotifyDOS.stream().map(item -> {
            OPNotify opNotify = new OPNotify();
            opNotify.setId(item.getId() + "");
            opNotify.setStatus(item.getStatus());
            opNotify.setTimes(item.getTimes());
            opNotify.setNextNotify(item.getNextNotify());
            opNotify.setClientCode(item.getClientCode());
            opNotify.setParams(JSONObject.parseArray(item.getParams(), String.class));
            return opNotify;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean updateNotify(OPNotify opNotify) {
        OpenPlatformNotifyDO openPlatformNotifyDO = new OpenPlatformNotifyDO();
        openPlatformNotifyDO.setClientCode(opNotify.getClientCode());
        openPlatformNotifyDO.setNextNotify(opNotify.getNextNotify());
        openPlatformNotifyDO.setParams(opNotify.getParams() == null ? null : JSONObject.toJSONString(opNotify.getParams()));
        openPlatformNotifyDO.setStatus(opNotify.getStatus());
        openPlatformNotifyDO.setTimes(opNotify.getTimes());
        openPlatformNotifyDO.setId(Long.parseLong(opNotify.getId()));
        return openPlatformNotifyMapper.updateById(openPlatformNotifyDO) > 0;
    }

    @Override
    public boolean customTryNotify() {
        return false;
    }
}
