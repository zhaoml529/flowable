package com.vk.flowable.controller;

import com.vk.flowable.domain.User;
import com.vk.flowable.service.UserService;
import com.vk.flowable.shiro.AdminSecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zml on 2018/10/17.
 */
@Controller
@Slf4j
public class PassportController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 首页
     * @return
     */
    @GetMapping("/main")
    public ModelAndView main() {
        return new ModelAndView("main");
    }

    /**
     * 系统后台登录页
     * @return
     */
    @GetMapping("/login")
    public ModelAndView login(String tab) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("tab", tab);
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(String userName, String password, String tab, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("login");
        if(StringUtils.isBlank(userName)) {
            modelAndView.addObject("errorMessage", "用户名或密码不能为空！");
            return  modelAndView;
        }
        if(StringUtils.isBlank(password)) {
            modelAndView.addObject("errorMessage", "用户名或密码不能为空！");
            return  modelAndView;
        }
        User user = userService.getByUserName(userName);
        if(user == null) {
            log.error("==> 用户不存在，用户名：{}", userName);
            modelAndView.addObject("errorMessage", "用户名或密码不正确！");
            return modelAndView;
        }
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()) { // 已经授权
            return modelAndView;
        }
        try {
            AdminSecurityUtils.encryptPassword(user);
            UsernamePasswordToken token = new UsernamePasswordToken(userName, user.getPassword());
            subject.login(token);
        } catch (Exception e) {
            log.error("==> shiro 登录认证失败，用户名：{}", userName);
            modelAndView.addObject("errorMessage", "用户名或密码不正确！");
            return modelAndView;
        }
        if(StringUtils.isBlank(tab)) {
            modelAndView.setViewName("index");
        } else {
            modelAndView.setViewName("index_tab");
        }
        modelAndView.addObject("manager", user);
        return modelAndView;
    }
}
