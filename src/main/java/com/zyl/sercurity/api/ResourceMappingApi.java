package com.zyl.sercurity.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResourceMappingApi {
    @GetMapping(value= {"/admin"})
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String admin() {
//        return "redirect:http://127.0.0.1:9001/index.html";
        return "admin";
    }

    @GetMapping(value= {"/","/index"})
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String index() {
//        return "redirect:http://127.0.0.1:9001/index.html";
        return "index";
    }

    @GetMapping("/user/login")
    public String login() {
//        return "redirect:http://127.0.0.1:9001/login.html";
        return "login";
    }
    
    @GetMapping("/403")
    public String forbidden() {
        return "403";
    }
}
