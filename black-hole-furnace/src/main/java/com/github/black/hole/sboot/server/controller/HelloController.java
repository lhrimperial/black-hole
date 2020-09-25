package com.github.black.hole.sboot.server.controller;

import com.github.black.hole.sboot.server.service.GradeScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hairen.long
 * @date 2020/8/30
 */
@RestController
public class HelloController {

    @Autowired GradeScoreService gradeScoreService;

    @RequestMapping("/hello/{name}")
    public String sayHello(@PathVariable String name) {
        return "hello " + name;
    }

    @RequestMapping("/grade/save")
    public Object save() {
        gradeScoreService.save();
        return gradeScoreService.listScore();
    }

    @RequestMapping("/grade/update")
    public Object update() {
        gradeScoreService.update();
        return gradeScoreService.listScore();
    }

    @RequestMapping("/grade/list")
    public Object list() {
        return gradeScoreService.listScore();
    }
}
