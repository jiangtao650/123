package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.OrderService;
import com.heeexy.example.util.CommonUtil;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/orderManage")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/submitOrder")
    public JSONObject submitOrder(@RequestBody JSONObject jsonObject) {
        return orderService.submitOrder(jsonObject);
    }

    @GetMapping("/listOrder")
    public JSONObject listOrder(HttpServletRequest request) {
        JSONObject jsonObject = CommonUtil.request2Json(request);
        return orderService.listOrder(jsonObject);
    }

    @PostMapping("/submitPaymentWay")
    public JSONObject submitPaymentWay(@RequestBody JSONObject jsonObject) {
        return orderService.submitPaymentWay(jsonObject);
    }

    @GetMapping("/singleOrder")
    public JSONObject singleOrder(HttpServletRequest request) {
        JSONObject jsonObject = CommonUtil.request2Json(request);
        return orderService.singleOrder(jsonObject);
    }

    @GetMapping("/orderManage")
    public JSONObject orderManage(HttpServletRequest request) {
        JSONObject jsonObject = CommonUtil.request2Json(request);
        return orderService.orderManage(jsonObject);
    }

    @GetMapping("/sendMessageToBusiness")
    public JSONObject sendMessageToBusiness(HttpServletRequest request) {
        JSONObject jsonObject = CommonUtil.request2Json(request);
        return orderService.sendMessageToBusiness(jsonObject);
    }

    private final static String rootPath = "E:/images/2020/";

    @PostMapping("/upload")
    public JSONObject uploadFile(MultipartFile multipartFiles) {
        File fileDir = new File(rootPath);
        if (!fileDir.exists() && !fileDir.isDirectory()) {
            fileDir.mkdirs();
        }
        try {
            if (multipartFiles != null) {
                try {
                    //以原来的名称命名,覆盖掉旧的，这里也可以使用UUID之类的方式命名，这里就没有处理了
                    String storagePath = rootPath + multipartFiles.getOriginalFilename();
                    System.out.println("上传的文件：" + multipartFiles.getName() + "," + multipartFiles.getContentType() + "," + multipartFiles.getOriginalFilename()
                            + "，保存的路径为：" + storagePath);
                    // 3种方法： 第1种
//                        Streams.copy(multipartFiles[i].getInputStream(), new FileOutputStream(storagePath), true);
                    // 第2种
//                        Path path = Paths.get(storagePath);
//                        Files.write(path,multipartFiles[i].getBytes());
                    // 第3种
                    multipartFiles.transferTo(new File(storagePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //前端可以通过状态码，判断文件是否上传成功
        return CommonUtil.successJson("上传成功！");
    }

    @GetMapping("/download")
    public void downloadFile(@RequestParam String fileName, HttpServletResponse response) {
        OutputStream os = null;
        InputStream is = null;
        try {
            File f = new File(rootPath + fileName);
            is = new FileInputStream(f);
            //读取流
            if (is == null) {
                System.out.println("下载附件失败，请检查文件“" + fileName + "”是否存在");
            } else {
                // 取得输出流
                os = response.getOutputStream();
                // 清空输出流
                response.reset();
                response.setContentType("application/x-download;charset=utf-8");
                //Content-Disposition中指定的类型是文件的扩展名，并且弹出的下载对话框中的文件类型图片是按照文件的扩展名显示的，点保存后，文件以filename的值命名，
                // 保存类型以Content中设置的为准。注意：在设置Content-Disposition头字段之前，一定要设置Content-Type头字段。
                //把文件名按UTF-8取出，并按ISO8859-1编码，保证弹出窗口中的文件名中文不乱码，中文不要太多，最多支持17个中文，因为header有150个字节限制。
                response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
                //复制
                IOUtils.copy(is, response.getOutputStream());
                response.getOutputStream().flush();

            }
        } catch (IOException e) {

        }
        //文件的关闭放在finally中
        finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //其实，这个返回什么都不重要
        //return CommonUtil.successJson("下载成功");
    }
}
