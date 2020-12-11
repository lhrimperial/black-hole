package com.github.black.hole.template.integration.app1;

import com.github.black.hole.dubbo.api.dto.PersonDTO;
import com.github.black.hole.dubbo.api.service.PersonService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hairen.long
 * @date 2020/12/8
 */
@Component
public class RpcPersonServiceHandler {

    @Reference PersonService personService;

    public List<InternalPersonDTO> findPersonList() {
        List<PersonDTO> persons = personService.listPerson();
        return PersonConvert.instance.convert(persons);
    }
}
