package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.heeexy.example.service.BusinessService;
import com.heeexy.example.util.CommonUtil;
import org.apache.el.parser.JJTELParserState;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/addBusiness")
public class BusinessController {
    @Autowired
    private BusinessService businessService;

    @GetMapping("/listCommodity")
    public JSONObject listCommodity(HttpServletRequest request) {
        JSONObject jsonObject = CommonUtil.request2Json(request);
        return businessService.listCommodity(jsonObject);
    }

    @PostMapping("/addCommodity")
    public JSONObject addCommodity(@RequestBody JSONObject jsonObject) {
        return businessService.addCommodity(jsonObject);
    }

    @GetMapping("/selectBusiness")
    public JSONObject selectBusiness(HttpServletRequest request) {
        JSONObject jsonObject = CommonUtil.request2Json(request);
        System.err.println(jsonObject);
        return businessService.selectBusiness(jsonObject);
    }

    @PostMapping("/updateBusinessInformation")
    public JSONObject updateBusinessInformation(@RequestBody JSONObject jsonObject) {
        System.err.println(jsonObject.entrySet());
        return businessService.updateBusinessInformation(jsonObject);
    }

    @PostMapping("/deleteCommodity")
    public JSONObject deleteCommodity(@RequestBody JSONObject jsonObject) {
        return businessService.deleteCommodity(jsonObject);
    }

    @PostMapping("/updateCommodity")
    public JSONObject updateCommodity(@RequestBody JSONObject jsonObject) {
        return businessService.updateCommodity(jsonObject);
    }

    @PostMapping("/selectCommodity")
    public JSONObject selectCommodity(@RequestBody JSONObject jsonObject) {
        return businessService.selectCommodity(jsonObject);
    }

    @GetMapping("/listCategory")
    public JSONObject listCategory(HttpServletRequest request) {
        JSONObject jsonObject = CommonUtil.request2Json(request);
        return businessService.listCategory(jsonObject);
    }

    @PostMapping("/addCategory")
    public JSONObject addCategory(@RequestBody JSONObject jsonObject) {
        return businessService.addCategory(jsonObject);
    }

    @PostMapping("/deleteCategory")
    public JSONObject deleteCategory(@RequestBody JSONObject jsonObject) {
        return businessService.deleteCategory(jsonObject);
    }

    @PostMapping("/updateCategory")
    public JSONObject updateCategory(@RequestBody JSONObject jsonObject) {
        return businessService.updateCategory(jsonObject);
    }

    @PostMapping("/selectCategory")
    public JSONObject selectCategory(@RequestBody JSONObject jsonObject) {
        return businessService.selectCategory(jsonObject);
    }

    @PostMapping("/pictureUpload")
    public JSONObject pictureUpload(MultipartFile file) {
        //一. 校验图片类型  1.利用集合校验   2.正则表达式(
        //1.1 获取文件名称  1.jpg  1.JPG
        JSONObject jsonObject = new JSONObject();

        String fileDir = "E:/images/";
        String urlPath = "http://image.jiangtao.com/";
        String fileName = file.getOriginalFilename();

        fileName = fileName.toLowerCase();
        int index = fileName.lastIndexOf(".");
        //1.2 获取下标 获取文件后缀类型
        String fileType = fileName.substring(index);
        //1.3 判断是否为图片类型
        if ((!fileType.equals(".jpg") && (!fileType.equals(".png")))) {
            System.out.println("图片类型有误，请重新上传！");
            return CommonUtil.successJson();
        }

        //二.如果是图片  高度/宽度
        //2.1将数据转化为图片对象
        OutputStream fouts = null;
        String uuidName = null;
        try {
            BufferedImage bufferedImage =
                    ImageIO.read(file.getInputStream());
            double width = bufferedImage.getWidth();
            double height = bufferedImage.getHeight();

            if (width == 0 || height == 0) {
                System.out.println("图片大小有误，请重新上传！");
                return CommonUtil.successJson();
            }

            //三 实现分目录存储
            //3.1按照/yyyy/MM/dd/的方式进行目录划分

            String fileDirPath = fileDir;

            File dirFile = new File(fileDirPath);
            //3.2 创建目录
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }

            //四. 实现文件上传 20-18-3.3
            //4.1 准备文件名称  UUID  522f88bb-33b2-11eb-93fb-00d861eaf238
            String uuid = UUID.randomUUID()
                    .toString().replace("-", "");
            //4.2 动态生成文件名称  uuid.jpg
            uuidName = uuid + fileType;
            /*判断是否需要压缩*/
            File realFile = new File(fileDirPath + uuidName);
            file.transferTo(realFile);
//            if (width >= 1000 && height >= 1500) {
//                /* 建立传输通道，文件输出流，最后以流的形式把文件内容传输过去*/
//                fouts = new FileOutputStream(fileDirPath + uuidName);
//                /* 绘制新图使用image.SCALE_SMOOTH算法，压缩后的图片质量相对光滑没有
//                 * 明显的锯齿，又叫图片光滑压缩算法*/
//                Image image = bufferedImage.getScaledInstance(1000, 1500, Image.SCALE_SMOOTH);
//                BufferedImage bufferedImage1 = new BufferedImage(1000, 1500, BufferedImage.TYPE_INT_RGB);
//                Graphics g = bufferedImage1.getGraphics();
//                g.setColor(Color.RED);
//                /*绘制处理后的图*/
//                g.drawImage(image, 0, 0, null);
//                g.dispose();
//                ImageIO.write(bufferedImage1, fileType, fouts);
//                System.out.println("压缩完毕！");
//            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            if (fouts != null) {
//                try {
//                    fouts.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
        }
        //本地磁盘地址:       E:\images\2020\12\02\a.jpg
        //网络访问虚拟地址:   http://image.jt.com\2020\12\02\a.jpg
        //String url = "http://image.jiangtao.com/2020/12/17/80fa7259235c42ff8154b5ae82c39d6b.jpg";

        String url = urlPath + uuidName;
        jsonObject.put("pictureUrl", url);
        return CommonUtil.successJson(jsonObject);
    }

    /**
     * @return com.alibaba.fastjson.JSONObject
     * @Author Tao:3301846209@qq.com
     * @Description: {
     * //TODO
     * }
     * @Date 18:24 2020/12/13
     * @Param [file]
     **/
    @PostMapping
    @RequestMapping("/importExcel")
    public JSONObject importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        /*
         * 2.判定类型为xls或xlsx
         * 3.获取sheet
         * 4.获取属性名
         * 5.获取每一行的数据，直到没有数据时结束读取
         * 6.将属性名和数据装入JSONObject（注意修改对应字段的属性）
         * 7.通过dao层找到sql语句并完成数据写入*/
        JSONObject jsonObject1 = new JSONObject();

//        1.读取文件流
        InputStream is = file.getInputStream();
//        获取文件名
        String fileName = file.getOriginalFilename();

        boolean nutNull = false;
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            System.out.println("上传文件格式不正确！");
            return CommonUtil.successJson();
        }
        Workbook wb = null;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
//         当文件格式为xlsx时
            wb = new XSSFWorkbook(is);
        } else {
//         当文件格式为xls时
            wb = new HSSFWorkbook(is);
        }
        if (null != wb) {
//            默认获取第一个sheet（表格中的第一个表单）
            Sheet sheet = wb.getSheetAt(0);
//            获取sheet的行数（）
            int rows = sheet.getLastRowNum();
//            验证表格是否为空
            if (rows == 0) {
                throw new Exception("导入表格为空，请填写数据！");
            }
//            获取sheet的第一行
            Row firstRow = sheet.getRow(0);
//            判断表头格式
            if (firstRow.getCell(0).getStringCellValue().equals("商品名称")
                    && firstRow.getCell(1).getStringCellValue().equals("商品价格")
                    && firstRow.getCell(2).getStringCellValue().equals("商品数量")
                    && firstRow.getCell(3).getStringCellValue().equals("商品种类")
                    && firstRow.getCell(4).getStringCellValue().equals("商品状态")
                    && firstRow.getCell(5).getStringCellValue().equals("商品简介")
                    && firstRow.getCell(6).getStringCellValue().equals("商家id")
            ) {
            } else {
                System.out.println("格式不正确，请下载模板进行参考");
                return CommonUtil.successJson();
            }
            String[] firstRowName = {"goodsName", "goodsPrice", "goodsNumbers", "goodsCategoryId",
                    "goodsSaleStatus", "goodsBrief", "userId"};
            String[] excelName = {"商品名称", "商品价格", "商品数量", "商品种类", "商品状态", "商品简介", "商家id"};
            System.out.println("rows=" + rows);
            JSONObject[] cellJson = new JSONObject[rows + 2];
//            循环遍历每一行
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                int theRow = i + 1;
//                判断行是否为空
                JSONObject jsonObject = new JSONObject();
                if (row.getCell(0) != null) {
                    row.getCell(0).setCellType(CellType.STRING);
//                        将得到的单元格值以列名称为key，以单元格值为value存入jsonObject
                    jsonObject.put("goodsName", row.getCell(0).getStringCellValue());
                } else {
                    System.out.println("导入失败（第" + theRow + "行，商品名称不得为空！）");
                    return CommonUtil.successJson();
                }
                if (row.getCell(1) != null) {
                    row.getCell(1).setCellType(CellType.NUMERIC);
//                        将得到的单元格值以列名称为key，以单元格值为value存入jsonObject
                    jsonObject.put("goodsPrice", row.getCell(1).getNumericCellValue());
                } else {
                    System.out.println("导入失败（第" + theRow + "行，商品价格不得为空！）");
                    return CommonUtil.successJson();
                }
                if (row.getCell(2) != null) {
                    row.getCell(2).setCellType(CellType.NUMERIC);
//                        将得到的单元格值以列名称为key，以单元格值为value存入jsonObject
                    jsonObject.put("goodsNumbers", row.getCell(2).getNumericCellValue());
                } else {
                    System.out.println("导入失败（第" + theRow + "行，商品数量不得为空！）");
                    return CommonUtil.successJson();
                }
                if (row.getCell(3) != null) {
                    row.getCell(3).setCellType(CellType.NUMERIC);
//                        将得到的单元格值以列名称为key，以单元格值为value存入jsonObject
                    jsonObject.put("goodsCategoryId", row.getCell(3).getNumericCellValue());
                } else {
                    System.out.println("导入失败（第" + theRow + "行，商品种类不得为空！）");
                    return CommonUtil.successJson();
                }
                if (row.getCell(4) != null) {
                    row.getCell(4).setCellType(CellType.NUMERIC);
//                        将得到的单元格值以列名称为key，以单元格值为value存入jsonObject
                    jsonObject.put("goodsSaleStatus", row.getCell(4).getNumericCellValue());
                } else {
                    System.out.println("导入失败（第" + theRow + "行，商品状态不得为空！）");
                    return CommonUtil.successJson();
                }
                if (row.getCell(5) != null) {
                    row.getCell(5).setCellType(CellType.STRING);
//                        将得到的单元格值以列名称为key，以单元格值为value存入jsonObject
                    jsonObject.put("goodsBrief", row.getCell(5).getStringCellValue());
                } else {
                    System.out.println("导入失败（第" + theRow + "行，商品简介不得为空！）");
                    return CommonUtil.successJson();
                }
                if (row.getCell(6) != null) {
                    row.getCell(6).setCellType(CellType.NUMERIC);
//                        将得到的单元格值以列名称为key，以单元格值为value存入jsonObject
                    jsonObject.put("userId", row.getCell(6).getNumericCellValue());
                    cellJson[i - 1] = jsonObject;

                } else {
                    System.out.println("导入失败（第" + theRow + "行，商品ID不得为空！）");
                    return CommonUtil.successJson();
                }

                jsonObject1.put("jsonObject" + (i - 1), cellJson[i - 1]);
            }
            jsonObject1.put("rows", rows);
        } else {
            System.out.println("导入数据失败！wb数据为空");
            return CommonUtil.successJson();
        }
        return businessService.importExcel(jsonObject1);
    }

    /**
     * @return
     * @Author Tao:3301846209@qq.com
     * @Description: {
     * //TODO
     * }
     * @Date 15:34 2020/12/15
     * @Param
     **/
    @GetMapping
    @RequestMapping("/mallCategoryDisplay")
    public JSONObject mallCategoryDisplay(HttpServletRequest request) {
        JSONObject jsonObject = CommonUtil.request2Json(request);
        return businessService.mallCategoryDisplay(jsonObject);
    }

    /**
     * @return com.alibaba.fastjson.JSONObject
     * 走马灯详情
     * @Author Tao:3301846209@qq.com
     * @Description: {
     * //TODO
     * }
     * @Date 11:52 2020/12/18
     * @Param [request]
     **/
    @GetMapping
    @RequestMapping("/walkHorseLight")
    public JSONObject walkHorseLight(HttpServletRequest request) {
        JSONObject jsonObject = CommonUtil.request2Json(request);
        return businessService.walkHorseLight(jsonObject);
    }

    @GetMapping
    @RequestMapping("/commodityDetails")
    public JSONObject commodityDetails(HttpServletRequest request) {
        JSONObject jsonObject = CommonUtil.request2Json(request);
        return businessService.commodityDetails(jsonObject);
    }

    /*
     * 购物车
     * */
    @GetMapping
    @RequestMapping("/shoppingCart")
    public JSONObject shoppingCart(HttpServletRequest request) {
        JSONObject jsonObject = CommonUtil.request2Json(request);
        return businessService.shoppingCart(jsonObject);
    }

    /**
     * @return
     * @Author Tao:3301846209@qq.com
     * @Description: {
     * //TODO
     * }
     * @Date 12:56 2020/12/19
     * @Param
     **/
    @PostMapping
    @RequestMapping("/addShoppingCart")
    public JSONObject addShoppingCart(@RequestBody JSONObject jsonObject) {
        return businessService.addShoppingCart(jsonObject);
    }
    @PostMapping
    @RequestMapping("/deleteShoppingCart")
    public JSONObject deleteShoppingCart(@RequestBody JSONObject jsonObject){
        return businessService.deleteShoppingCart(jsonObject);
    }
}

