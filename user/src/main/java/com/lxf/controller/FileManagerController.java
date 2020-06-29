package com.lxf.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lxf.baseEntity.SystemException;
import com.lxf.vo.entity.excel.ExcelDemo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: lxf
 * @create: 2020-06-04 21:51
 * @description: Excel导入功能，格式类型转换
 */
@RestController
@RequestMapping("/file/")
@Slf4j
public class FileManagerController {
    @PostMapping("excel/upload")
    public List<ExcelDemo> uploadExcel(MultipartFile  file){
        if(file==null){
            throw  new SystemException("传入的文件不能为空","user1");
        }
        InputStream  input=null;
        try {
              input=file.getInputStream();
        }catch (IOException ex){
            throw  new SystemException("IOE异常","user1");
        }
        AnalysisEventListener  readListener=new ReadListener();
        EasyExcel.read(input,ExcelDemo.class,readListener).sheet().doRead();
        List<ExcelDemo> lists= ((ReadListener) readListener).getHasReadData();
        return lists;
    };
    @PostMapping("excel/download")
    public String downLoadExcel(HttpServletResponse response){
        return  null;
    }
    private class ReadListener   extends AnalysisEventListener<ExcelDemo> {
        private List<ExcelDemo> hasReadData=new ArrayList<>();
        @Override
        public void invoke(ExcelDemo excelDemo, AnalysisContext analysisContext) {
            log.info("读取第"+analysisContext.getCurrentRowNum()+"行，对应的数据为"+ JSONObject.toJSONString(excelDemo)+",标记字段为"+excelDemo.getAddress());
            hasReadData.add(excelDemo);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            log.info("读取数据"+analysisContext.getTotalCount()+",读取完毕");

        }
        @Override
        public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
            log.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
        }
        public List<ExcelDemo> getHasReadData() {
            return hasReadData;
        }

        public void setHasReadData(List<ExcelDemo> hasReadData) {
            this.hasReadData = hasReadData;
        }
    }
}
