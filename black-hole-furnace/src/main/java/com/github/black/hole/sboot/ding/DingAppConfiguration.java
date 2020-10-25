package com.github.black.hole.sboot.ding;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hairen.long
 * @date 2020/5/7
 */
@Configuration
public class DingAppConfiguration {

    @Value("${ifarmshop.notice.agentId}")
    private String NOTICE_AGENT_ID;

    @Value("${ifarmshop.notice.appKey}")
    private String NOTICE_APP_KEY;

    @Value("${ifarmshop.notice.appSecret}")
    private String NOTICE_APP_SECRET;

    @Value("${crowd.agentId}")
    private String CROWD_ID;

    @Value("${crowd.appKey}")
    private String CROWD_KEY;

    @Value("${crowd.appSecret}")
    private String CROWD_SECRET;

    @Value("${crowd.token}")
    private String token;

    @Value("${crowd.aesKey}")
    private String aesKey;

    @Bean(name = "noticeConfig")
    public DingAppConfig getNoticeConfig() {
        return DingAppConfig.builder()
                .agentId(NOTICE_AGENT_ID)
                .appKey(NOTICE_APP_KEY)
                .appSecret(NOTICE_APP_SECRET)
                .build();
    }

    @Bean(name = "crowdOrgConfig")
    public DingAppConfig getCrowdOrgConfig() {
        return DingAppConfig.builder()
                .agentId(CROWD_ID)
                .appKey(CROWD_KEY)
                .appSecret(CROWD_SECRET)
                .token(token)
                .aesKey(aesKey)
                .build();
    }

    @Bean
    @SuppressWarnings({"rawtypes", "unchecked"})
    public ServletRegistrationBean callBackServlet() {
        ServletRegistrationBean servletRegistrationBean =
                new ServletRegistrationBean(new CallBackServlet(), "/call/servlet");
        return servletRegistrationBean;
    }
}
