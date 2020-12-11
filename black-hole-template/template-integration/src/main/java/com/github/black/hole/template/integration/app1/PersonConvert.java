package com.github.black.hole.template.integration.app1;

import com.github.black.hole.dubbo.api.dto.PersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author hairen.long
 * @date 2020/12/8
 */
@Mapper(componentModel = "spring")
public interface PersonConvert {

    PersonConvert instance = Mappers.getMapper(PersonConvert.class);

    /**
     * convert
     *
     * @param person
     * @return
     */
    InternalPersonDTO convert(PersonDTO person);

    /**
     * convert
     *
     * @param personList
     * @return
     */
    List<InternalPersonDTO> convert(List<PersonDTO> personList);
}
