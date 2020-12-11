package com.github.black.hole.simple.mapstruct.mapping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author hairen.long
 * @date 2020/12/8
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private Long studentId;
    private String studentName;
    private Integer age;
    private String ageLevel;
    private String sexName;
    private LocalDate admissionDate;
}
