package com.lxf.utils;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lxf
 * @create: 2020-05-19 16:57
 * @description: excel操作工具类
 */
@Slf4j
public class ExcelTools {
public static  <T>  List<T> readExcelToObj(){
    return  null;
}
private class ExcelListener extends AnalysisEventListener<T> {
    private List<T> list=new ArrayList<>();

    @Override
    public void invoke(T o, AnalysisContext analysisContext) {
        log.info("开始解析excel中的数据，读取的数据为：{}", JSON.toJSON(o));
        if(BeanUtil.isNotEmpty(o)){

        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
}
