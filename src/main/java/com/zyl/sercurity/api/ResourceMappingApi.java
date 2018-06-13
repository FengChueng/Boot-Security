package com.zyl.sercurity.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

import com.zyl.sercurity.pojo.UserDto;

@Controller
public class ResourceMappingApi {
    @GetMapping(value= {"/admin"})
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String admin() {
//        return "redirect:http://127.0.0.1:9001/index.html";
        return "admin";
    }

    @GetMapping(value= {"/","/index"})
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public String index() {
//        return "redirect:http://127.0.0.1:9001/index.html";
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
//        return "redirect:http://127.0.0.1:9001/login.html";
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "login";
    }
    
    @GetMapping("/403")
    public String forbidden() {
        return "403";
    }
    
    @GetMapping("/register")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register";
    }

}
