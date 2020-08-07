package com.github.black.hole.netty.simulate;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author hairen.long
 * @date 2020/7/14
 */
@Component
public class GradeCalculateHandlerFactory implements ApplicationContextAware {

    private CalculateHandlerPipeline pipeline;

    public CalculateHandlerPipeline pipeline() {
        return pipeline;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.pipeline =
                new DefaultCalculateHandlerPipeline()
                        .addLast(applicationContext.getBean(HelloWorldCalculateHandler.class));
    }
}
