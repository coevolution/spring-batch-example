package com.treefinance.payment.batch.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lxp
 * @date 2019/11/28 上午10:45
 * @Version 1.0
 */
@RestController
@RequestMapping("/inner")
public class InnerController {
    private static final Logger logger = LoggerFactory.getLogger(InnerController.class);
    @RequestMapping("/log/{target}")
    public Object setLoggerLever(@PathVariable String target, @RequestParam String level, HttpRequest request) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.getLogger(target).setLevel(Level.valueOf(level));
        String result =
            String.format("log level配置更新:%s log level set to %s", target, loggerContext.getLogger(target).getLevel());
        logger.info(result);
        return result;
    }
}
