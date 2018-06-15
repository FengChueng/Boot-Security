package com.zyl.sercurity.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {

    @RequestMapping(value= {"/","index"})
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping("/login")
    public ModelAndView home() {
        return new ModelAndView("login");
    }
}