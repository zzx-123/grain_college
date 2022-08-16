package com.gzuniversity.commonutils.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.sun.istack.internal.logging.Logger;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;
import org.slf4j.LoggerFactory;
import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        System.out.println("开始插入日期");
        this.setFieldValByName("gmtCreate", new Date(), metaObject);
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        System.out.println("开始更新日期");
        this.setFieldValByName("gmtModified", new Date(), metaObject);
    }
}
