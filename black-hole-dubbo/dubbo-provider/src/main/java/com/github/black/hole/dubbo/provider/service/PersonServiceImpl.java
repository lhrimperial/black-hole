package com.github.black.hole.dubbo.provider.service;

import com.github.black.hole.dubbo.api.dto.PersonDTO;
import com.github.black.hole.dubbo.api.service.PersonService;
import com.google.common.collect.Lists;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;

/**
 * @author hairen.long
 * @date 2020/12/8
 */
@Service
public class PersonServiceImpl implements PersonService {
    @Override
    public List<PersonDTO> listPerson() {
        return Lists.newArrayList(
                PersonDTO.builder().personId(1L).personName("andy").build(),
                PersonDTO.builder().personId(2L).personName("jack").build());
    }
}
