package com.github.black.hole.sboot.excel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author hairen.long
 * @date 2021/3/15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradeStationDTO {
    private Long teamId;
    private String startDate;
    private String endDate;
    private Long level;
}
