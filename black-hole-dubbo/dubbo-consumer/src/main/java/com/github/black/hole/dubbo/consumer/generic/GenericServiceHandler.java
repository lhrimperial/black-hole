package com.github.black.hole.dubbo.consumer.generic;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hairen.long
 * @date 2020/10/10
 */
public class GenericServiceHandler {

    public static void main(String[] args) {
        // 引用远程服务
        ApplicationConfig application = new ApplicationConfig();
        application.setName("api-generic-consumer");

        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://ifarmshop.com:2181");

        application.setRegistry(registry);
        // 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存
        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        // 弱类型接口名
        reference.setInterface("com.github.black.hole.dubbo.api.service.UserService");
//        reference.setVersion("1.0.0");
        // 声明为泛化接口
        reference.setGeneric(true);
        reference.setApplication(application);

        // 用org.apache.dubbo.rpc.service.GenericService可以替代所有接口引用
        GenericService genericService = reference.get();

        // 基本类型以及Date,List,Map等不需要转换，直接调用
        Object result = genericService.$invoke("listUser", null, null);

        // 用Map表示POJO参数，如果返回值为POJO也将自动转成Map
        Map<String, Object> person = new HashMap<String, Object>();
        person.put("userName", "andy");
        person.put("password", "123456");
        // 如果返回POJO将自动转成Map
        Object result1 = genericService.$invoke("saveAndReturn", new String[]
                {"com.github.black.hole.dubbo.api.dto.UserDTO"}, new Object[]{person});
    }
}
