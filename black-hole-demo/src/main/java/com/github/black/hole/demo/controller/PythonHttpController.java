package com.github.black.hole.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PythonHttpController {

    private static final Logger logger = LoggerFactory.getLogger(PythonHttpController.class);

    @RequestMapping("/hello")
    public String hello(){
        return "Hello Python";
    }

    @RequestMapping("/python/operateLog")
    public String receive(@RequestParam String requestId, @RequestParam String actionType,
                          @RequestParam String productType, @RequestParam String stepName, @RequestParam String stepMsg) {
        logger.info("requestId={},actionType={},productType={},stepName={},stepMsg={}", requestId, actionType, productType, stepName, stepMsg);
        return "OK";
    }
}
