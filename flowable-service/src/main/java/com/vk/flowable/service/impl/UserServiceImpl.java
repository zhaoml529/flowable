package com.vk.flowable.service.impl;

import com.vk.flowable.domain.User;
import com.vk.flowable.mapper.UserMapper;
import com.vk.flowable.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zml
 * @since 2018-10-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
