package com.iotechn.unimall.data;

import com.alibaba.fastjson.JSONObject;
import com.iotechn.unimall.data.dto.order.OrderRequestDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UnimallDataApplicationTests {

	@Test
	public void contextLoads() {
		OrderRequestDTO temp = JSONObject.parseObject("{\"skuList\":[{\"skuId\":2784,\"num\":1,\"title\":\"俏尾巴SmartTail LOVECAT同厂 环保2.0小颗粒豆腐猫砂 原味 2.5kg\",\"originalPrice\":100,\"price\":1,\"vipPrice\":1,\"skuTitle\":\"标准\",\"spuImg\":\"https://unimall-demo.oss-cn-shenzhen.aliyuncs.com/bg/a407352b0c6745ceb3be31a8fa50820f.jpg\",\"stock\":732,\"spuId\":1236775,\"categoryId\":1036533,\"categoryIdList\":[1036533,1036531,1036530]}],\"totalOriginalPrice\":100,\"totalPrice\":1,\"mono\":\"\",\"takeWay\":\"buy\",\"freightPrice\":0}",OrderRequestDTO.class);

	}



}
