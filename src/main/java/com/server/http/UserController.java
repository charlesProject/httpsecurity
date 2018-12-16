package com.server.http;

import com.server.http.entity.AjaxReult;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @PostMapping(value = "/login")
    public AjaxReult login(@Param("username") String username, @Param("password") String password) {
        //此处密码没加密，实际应用中密码一定要加密处理
        AjaxReult ajaxReult = new AjaxReult("", 1000, null);
        if ("admin".equals(username) && "123456".equals(password)) {
            ajaxReult.setObject("/index");
        } else {
            ajaxReult.setCode(1001);
            ajaxReult.setMessage("用户名或密码错误");
        }
        return ajaxReult;
    }
}