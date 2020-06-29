package com.lxf.vo.entity.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: lxf
 * @create: 2020-06-04 22:18
 * @description:用于格式转换：可以处理类似枚举字段的处理
 */
@Slf4j
public class CustomStringStringConverter   implements Converter<String> {
    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        log.info("获取到的地址是cellData"+cellData.getStringValue());
        return String.valueOf(cellData.getStringValue())+"---后缀";
    }

    @Override
    public CellData convertToExcelData(String s, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return  new CellData("---前缀-"+s);
    }
}
