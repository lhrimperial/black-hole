package com.github.black.hole.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoMain.class)
public class CallPythonTest {

    private static final Logger logger = LoggerFactory.getLogger(CallPythonTest.class);

    @Test
    public void callPython() throws Exception {
        String commandLine = "python /Users/longhairen/Documents/idea/github/black-hole/black-hole-demo/src/test/resources/python/ping_ip.py";
        final Process finalProcess = Runtime.getRuntime().exec(commandLine);
        InputStream stdInputStream = finalProcess.getInputStream();
        if (stdInputStream != null) {
            BufferedReader in = new BufferedReader(new InputStreamReader(finalProcess.getInputStream()));
            //logger.info("get output of stdioStr:");
            StringBuilder builder = new StringBuilder();
            String stdioStr;
            while ((stdioStr = in.readLine()) != null) {
                logger.info(">>>>>" + stdioStr);
                stdioStr = trim(stdioStr);
                builder.append(stdioStr);
            }
            logger.info("result={}", builder.toString());
        }
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }
}
