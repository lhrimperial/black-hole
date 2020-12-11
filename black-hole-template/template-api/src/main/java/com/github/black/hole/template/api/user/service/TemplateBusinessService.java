package com.github.black.hole.template.api.user.service;

import com.github.black.hole.template.api.user.dto.BusinessDTO;

/**
 * @author hairen.long
 * @date 2020/11/27
 */
public interface TemplateBusinessService {
    /**
     * find user
     *
     * @param userId
     * @return
     */
    BusinessDTO findUserById(Long userId);
}
