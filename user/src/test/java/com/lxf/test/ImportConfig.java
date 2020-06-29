package com.lxf.test;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author: lxf
 * @create: 2020-05-13 21:31
 * @description:
 */
@Configuration
@Import({TestA.class})
public class ImportConfig {
}
