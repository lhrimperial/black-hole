package com.github.black.hole.simple.mapstruct.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author hairen.long
 * @date 2020/12/8
 */
@Mapper(imports = {CustomMapping.class})
public interface StudentConvert {

    @Mapping(source = "id", target = "studentId")
    @Mapping(source = "name", target = "studentName")
    @Mapping(source = "age", target = "age")
    @Mapping(target = "ageLevel", expression = "java(CustomMapping.ageLevel(student.getAge()))")
    @Mapping(target = "sexName", expression = "java(CustomMapping.sexName(student.getSex()))")
    @Mapping(source = "admissionTime", target = "admissionDate", dateFormat = "yyyy-MM-dd")
    StudentDTO from(Student student);

    default LocalDate map(LocalDateTime time) {
        return time.toLocalDate();
    }
}
