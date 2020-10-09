package com.github.black.hole.dubbo;

import com.alibaba.fastjson.JSON;
import com.github.black.hole.dubbo.api.dto.UserDTO;
import com.github.black.hole.dubbo.consumer.DubboConsumerApplication;
import com.github.black.hole.dubbo.consumer.reference.UserQueryHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author hairen.long
 * @date 2020/10/9
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DubboConsumerApplication.class)
public class DubboConsumerTest {

    @Autowired private UserQueryHandler userQueryHandler;

    @Test
    public void testUserList() {
        List<UserDTO> users = userQueryHandler.getAllUser();
        System.out.println(JSON.toJSONString(users));
    }
}
