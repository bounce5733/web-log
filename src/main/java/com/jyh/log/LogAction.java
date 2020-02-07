package com.jyh.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @Author jiangyonghua
 * @Date 2020/2/5 21:30
 * @Version 1.0
 **/
@RestController
@RequestMapping("action")
@Slf4j
public class LogAction {

    @GetMapping("/refresh/{level}")
    public ResponseEntity<Object> refresh(@PathVariable("level") String level) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = loggerContext.getLogger(Const.CUSTOM_LOG_NAME);
        logger.setLevel(Level.valueOf(level));
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<Object> test() {
        log.trace("系统trace日志...");
        log.debug("系统debug日志...");
        log.info("系统info日志...");
        log.warn("系统warn日志...");
        log.error("系统error日志...");
        return new ResponseEntity<Object>(HttpStatus.OK);
    }
}
