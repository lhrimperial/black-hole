package com.github.black.hole.excel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hairen.long
 * @date 2020/5/26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GreyAgency {
    private String type;
    private Integer cityId;
    private String cityName;
    private Long agencyId;
    private String agencyName;
    private Integer count;
    private String dateStr;
}
