package com.server.http.filter;

import com.alibaba.fastjson.JSON;
import com.server.http.entity.AjaxReult;
import jdk.nashorn.internal.parser.JSONParser;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Pattern;

@WebFilter(filterName="CustomFilter",urlPatterns="/*")
public class LogonFilter implements Filter {

    private static final String [] ignoreUrls = {"/login", "/js/"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("logon Filter init ==================================================");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest)request;
        //不拦截请求
        String path = httpReq.getServletPath();
        for (String ignoreUrl : ignoreUrls) {
            Pattern pattern = Pattern.compile(ignoreUrl, Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(path).find()) {
                chain.doFilter(request, response);
                return;
            }
        }

        HttpSession session = ((HttpServletRequest) request).getSession();
        Object token = session.getAttribute("token");
        if (null != token && "thisistest".equals(token.toString())) {
            //登录的
            chain.doFilter(request, response);
            return;
        } else {
            AjaxReult ajaxReult = new AjaxReult("", 1000, null);
            ajaxReult.setCode(1005);
            ajaxReult.setMessage("请先登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(ajaxReult));
            response.getWriter().close();
        }
    }

    @Override
    public void destroy() {
        System.out.println("logon Filter destroy ==============================================");
    }
}