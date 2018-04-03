package com.example.loginjwtdemo.requestCached;

import javax.servlet.ServletRequest;

public interface PortResolver {
    int getServerPort(ServletRequest var1);
}
