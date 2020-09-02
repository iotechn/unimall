package com.iotechn.unimall.launcher.crawler;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.iotechn.unimall.admin.api.product.AdminProductService;
import com.iotechn.unimall.data.domain.SkuDO;
import com.iotechn.unimall.data.domain.SpuSpecificationDO;
import com.iotechn.unimall.data.dto.goods.AdminSpuDTO;
import com.iotechn.unimall.data.enums.StatusType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Description:
 * User: rize
 * Date: 2020/8/17
 * Time: 19:34
 */
@Component
public class ProductCrawler {

    private static final Logger logger = LoggerFactory.getLogger(ProductCrawler.class);

    @Autowired
    private AdminProductService adminProductService;

    public void crawl() throws Exception {
        WebClient webClient = new WebClient();
        FileInputStream fis = new FileInputStream(new File("C:\\task.xlsx"));
        XSSFWorkbook hssfWorkbook  = new XSSFWorkbook (fis);
        // Read the Sheet
        XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        int lastRowNum = hssfSheet.getLastRowNum();
        for (int rowNum = 3; rowNum <= lastRowNum; rowNum++) {

            XSSFRow hssfRow = hssfSheet.getRow(rowNum);
//            https://detail.m.tmall.com/item.htm?id=615468618847
            if (hssfRow != null) {
                AdminSpuDTO adminSpuDTO = new AdminSpuDTO();
                XSSFCell title = hssfRow.getCell(0);
                adminSpuDTO.setTitle(getValue(title));
                XSSFCell description = hssfRow.getCell(20);
                adminSpuDTO.setDetail(getValue(description));
                XSSFCell outerId = hssfRow.getCell(33);
                adminSpuDTO.setOriginalPrice(10000);
                adminSpuDTO.setVipPrice(4990);
                adminSpuDTO.setPrice(4990);
                adminSpuDTO.setFreightTemplateId(33l);
                adminSpuDTO.setUnit("件");
                SpuSpecificationDO spuSpecificationDO1 = new SpuSpecificationDO();
                spuSpecificationDO1.setTitle("尺码");
                SpuSpecificationDO spuSpecificationDO2 = new SpuSpecificationDO();
                spuSpecificationDO2.setTitle("颜色");
                adminSpuDTO.setSpecificationList(Arrays.asList(spuSpecificationDO1, spuSpecificationDO2));
                adminSpuDTO.setStatus(StatusType.ACTIVE.getCode());
                // 请求手机详情
                String mobileDetailUrl = "https://detail.m.tmall.com/item.htm?id=" + getValue(outerId);
                HtmlPage page = webClient.getPage(mobileDetailUrl);
                DomNodeList<DomElement> imgTags = page.getElementsByTagName("img");
                List<String> imgList = new LinkedList<>();
                for (DomElement domElement : imgTags) {
                    HtmlElement htmlElement = (HtmlElement) domElement;
                    String clazzName = htmlElement.getAttribute("class");
                    if (!StringUtils.isEmpty(clazzName) && clazzName.equals("slider-item-img")) {
                        imgList.add("https:" + htmlElement.getAttribute("src"));
                    }
                }
                adminSpuDTO.setImg(imgList.get(0));
                adminSpuDTO.setImgList(imgList);
                adminSpuDTO.setCategoryId(1036587l);
                DomNodeList<HtmlElement> divForSkuList = page.getElementById("content").getElementsByTagName("div");
                for (DomElement domElement : divForSkuList) {
                    HtmlElement htmlElement = (HtmlElement) domElement;
                    String clazzName = htmlElement.getAttribute("class");
                    if (!StringUtils.isEmpty(clazzName) && clazzName.equals("skuText")) {
                        htmlElement.click();
                        Thread.sleep(5000);
                        break;
                    }
                }

                DomNodeList<DomElement> ulList = page.getElementsByTagName("ul");
                List<SkuDO> skuList = new LinkedList<>();
                for (DomElement domElement : ulList) {
                    HtmlElement htmlElement = (HtmlElement) domElement;
                    String clazzName = htmlElement.getAttribute("class");
                    if (!StringUtils.isEmpty(clazzName) && clazzName.equals("sku-list-wrap")) {
                        DomNodeList<HtmlElement> li = htmlElement.getElementsByTagName("li");
                        if (li.getLength() == 2) {
                            List<String> ma = Arrays.asList("S", "M", "L");
                            DomNodeList<HtmlElement> aFirst = li.get(0).getElementsByTagName("a");
                            // 取前11个
                            for (int i = 0; i <= 10; i++) {
                                HtmlElement aElement = aFirst.get(i);
                                String src = aElement.getElementsByTagName("img").get(0).getAttribute("src");
                                String span = aElement.getElementsByTagName("span").get(0).getTextContent();
                                for (String c : ma) {
                                    SkuDO skuDO = new SkuDO();
                                    skuDO.setImg(src);
                                    skuDO.setBarCode(System.nanoTime() + "");
                                    skuDO.setTitle(c+","+span);
                                    skuDO.setOriginalPrice(10000);
                                    skuDO.setVipPrice(4990);
                                    skuDO.setPrice(4990);
                                    skuDO.setSpecification("尺码_" + c + ",颜色_" + span);
                                    skuDO.setStock(999);
                                    skuDO.setWeight(200);
                                    skuList.add(skuDO);
                                }
                            }
                        }
                    }
                }

                adminSpuDTO.setSkuList(skuList);

                adminProductService.create(adminSpuDTO, 1l);

                Thread.sleep(5000);
                // 主图
//                adminSpuDTO.setImg();
//
//                HSSFCell holderRealName = hssfRow.getCell(1);
//
//                HSSFCell holderBirthday = hssfRow.getCell(2);
//                HSSFCell policyApplyDate = hssfRow.getCell(3);


            }
        }
    }
    private static String getValue(XSSFCell hssfCell) {

        if(hssfCell==null){
            return "";
        }

        if (hssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf((long)hssfCell.getNumericCellValue());
        }else {
            return String.valueOf(hssfCell.getStringCellValue()==null?"":hssfCell.getStringCellValue());
        }
    }

}
