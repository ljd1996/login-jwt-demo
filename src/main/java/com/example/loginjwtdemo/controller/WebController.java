package com.example.loginjwtdemo.controller;

import com.example.loginjwtdemo.requestCached.HttpSessionRequestCache;
import com.example.loginjwtdemo.requestCached.SavedRequest;
import com.example.loginjwtdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class WebController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public String all() {
        return "all";
    }

    @GetMapping("/home")
    public String home(@RequestParam(value = "v", required = false) String v) {
        System.out.println("value:" + v);
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        System.out.println("login");
        return "login";
    }

    @GetMapping("/404")
    public String login404() {
        return "404";
    }

    @PostMapping("/loginConfirm")
    public String loginConfirm(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam("name") String name, @RequestParam("password") String password) throws IOException {
        String token = userService.login(name, password);
        if (token == null) {
            return "redirect:/login";
        } else {
            HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
            SavedRequest saved = requestCache.getRequest(request, response);

            if (saved != null) {
                String urlCached = saved.getRedirectUrl();
                String url = urlCached.substring(urlCached.indexOf("/", urlCached.indexOf("//")+2));
                return "redirect:" + url;
            } else {
                return "redirect:/home";
            }
        }
    }
}
