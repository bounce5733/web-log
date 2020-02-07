package com.jyh.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * TODO
 *
 * @Author jiangyonghua
 * @Date 2020/2/5 13:47
 * @Version 1.0
 **/
@Controller
@Slf4j
public class LogController {

    @RequestMapping("/")
    public String index() {
        return "log";
    }
}
