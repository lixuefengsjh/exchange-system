package com.lxf.baseEntity;

import cn.hutool.core.util.StrUtil;

/**
 * @author: lxf
 * @create: 2020-04-19 09:15
 * @description: redis的key基本类
 * 所有的redis的key生成策略都将继承该类
 */
public class BaseRedisKey {
    private  String prefix;
    protected String key(String key) {
        if (StrUtil.isEmpty(prefix)) {
            prefix = this.getClass().getSimpleName() + ":";
        }
        return prefix + key;
    }

}
