package com.github.black.hole.gateway.entity;

import com.github.black.hole.gateway.enums.ResultCode;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
@Data
@SuperBuilder
public class Result implements Serializable {

    private String code;

    private String message;

    protected boolean isSuccess(){
        return Objects.equals(this.code, ResultCode.SUCCESS.getCode());
    }
}
