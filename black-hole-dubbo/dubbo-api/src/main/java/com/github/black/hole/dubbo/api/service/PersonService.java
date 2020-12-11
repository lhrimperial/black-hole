package com.github.black.hole.dubbo.api.service;

import com.github.black.hole.dubbo.api.dto.PersonDTO;

import java.util.List;

/**
 * @author hairen.long
 * @date 2020/12/8
 */
public interface PersonService {
    /**
     * list
     *
     * @return
     */
    List<PersonDTO> listPerson();
}
