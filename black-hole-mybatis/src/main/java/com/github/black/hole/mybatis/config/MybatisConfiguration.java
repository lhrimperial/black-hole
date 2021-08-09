package com.github.black.hole.mybatis.config;

import com.github.black.hole.mybatis.common.PageInterceptor;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

/**
 * @author hairen.long
 * @date 2021/8/8
 */
@Configurable
@EnableTransactionManagement
@MapperScan("com.github.black.hole.mybatis.mapper")
public class MybatisConfiguration {

    // 将插件加入到mybatis插件拦截链中
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(Configuration configuration) {
                // 插件拦截链采用了责任链模式，执行顺序和加入连接链的顺序有关
                PageInterceptor interceptor = new PageInterceptor();
                // 设置参数，比如阈值等，可以在配置文件中配置，这里直接写死便于测试

                configuration.addInterceptor(interceptor);
            }
        };
    }
}
