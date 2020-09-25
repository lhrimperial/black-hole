package com.github.black.hole.sboot.server.validateor;

import com.alibaba.fastjson.JSON;
import com.github.black.hole.sboot.common.ServiceException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Objects;
import java.util.Set;

/**
 * @author hairen.long
 * @date 2020/8/30
 */
@Aspect
@Component
public class ParamCheckAspect {
    private static final Logger LOG = LoggerFactory.getLogger(ParamCheckAspect.class);
    private static final Validator VALIDATOR;

    static {
        VALIDATOR =
                Validation.byProvider(HibernateValidator.class)
                        .configure()
                        .failFast(true)
                        .buildValidatorFactory()
                        .getValidator();
    }

    @Before("execution(public * com.github.black.hole.sboot.server.service.*.*())")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("do in Aspect before method called! args: " + JSON.toJSONString(joinPoint.getArgs()));
    }

    @Before("@annotation(paramCheck)")
    public void before(JoinPoint joinPoint, ParamCheck paramCheck) throws ServiceException {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 执行方法参数的校验
        Set<ConstraintViolation<Object>> constraintViolations =
                VALIDATOR
                        .forExecutables()
                        .validateParameters(joinPoint.getThis(), signature.getMethod(), args);
        if (CollectionUtils.isEmpty(constraintViolations)) {
            return;
        }
        ConstraintViolation<Object> violation = constraintViolations.iterator().next();
        if (Objects.nonNull(violation)) {
            LOG.warn("参数校验失败：message:{}", JSON.toJSONString(violation.getMessage()));
            throw new ServiceException(violation.getMessage());
        }
    }
}
