package com.example.loginjwtdemo.requestCached;

import javax.servlet.http.HttpServletRequest;

public interface RequestMatcher {
    boolean matches(HttpServletRequest var1);
}
