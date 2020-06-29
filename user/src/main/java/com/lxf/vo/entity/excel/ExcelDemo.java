package com.lxf.vo.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import lombok.Data;

/**
 * @author: lxf
 * @create: 2020-06-04 22:13
 * @description:
 */
@Data
public class ExcelDemo {
    @ExcelProperty(index = 0)
    private String date;

    @ExcelProperty(index = 1)
    private String age;

    @ExcelProperty(index = 2,converter = CustomStringStringConverter.class)
    private String address;

    @NumberFormat("#.##%")
    @ExcelProperty(index = 3)
    private String score;
}
