package com.vk.flowable.controller;


import com.vk.flowable.domain.User;
import com.vk.flowable.response.BaseResult;
import com.vk.flowable.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zml
 * @since 2018-10-11
 */
@RestController
@RequestMapping("/user")
public class UserController {

    public BaseResult add(User user) {
        SecurityUtils.encryptPassword(user);
        return BaseResult.success();
    }

}

