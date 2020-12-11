package com.github.black.hole.template.integration.app1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hairen.long
 * @date 2020/12/8
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InternalPersonDTO {
    /** id */
    private Long personId;
    /** name */
    private String personName;
}
