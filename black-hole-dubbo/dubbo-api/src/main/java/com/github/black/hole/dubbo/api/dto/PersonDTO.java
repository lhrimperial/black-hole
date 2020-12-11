package com.github.black.hole.dubbo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author hairen.long
 * @date 2020/12/8
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO implements Serializable {
    private static final long serialVersionUID = 488241760393229496L;
    /** id */
    private Long personId;
    /** name */
    private String personName;
}
