package com.iotechn.unimall.runner.controller;

import com.alibaba.fastjson.JSONObject;
import com.dobbinsoft.fw.support.component.open.OpenPlatformUtil;
import com.iotechn.unimall.biz.client.erp.ErpClient;
import com.iotechn.unimall.data.dto.ErpStockNotifyDTO;
import com.iotechn.unimall.data.dto.ErpStockNotifyItemDTO;
import com.iotechn.unimall.data.properties.UnimallErpOpenPlatformProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by rize on 2019/7/10.
 */
@RestController
@RequestMapping("/cb")
public class CallbackController {

    @Autowired
    private UnimallErpOpenPlatformProperties unimallErpOpenPlatformProperties;

    @Autowired
    private ErpClient erpClient;

    @RequestMapping("/erp/stock")
    @Transactional(rollbackFor = Exception.class)
    public Object stock(@RequestBody String body) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(body);
        String ciphertext = jsonObject.getString("ciphertext");
        // 验签
        String publicKey = unimallErpOpenPlatformProperties.getDobbinServerPublicKey();
        Map<String, String> map = OpenPlatformUtil.parseAndCheckSign(ciphertext, publicKey);
        String notify = map.get("notify");
        // 编辑库存
        ErpStockNotifyDTO erpStockNotifyDTO = JSONObject.parseObject(notify, ErpStockNotifyDTO.class);
        List<ErpStockNotifyItemDTO> items = erpStockNotifyDTO.getItems();
        for (ErpStockNotifyItemDTO item : items) {
            erpClient.invokeStockChange(item.getBarCode(), item.getQuantity());
        }
        return "ok";
    }

}
