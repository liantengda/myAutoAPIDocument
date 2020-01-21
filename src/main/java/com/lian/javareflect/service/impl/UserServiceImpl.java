package com.lian.javareflect.service.impl;


import com.lian.javareflect.domutil.annotion.MyClass;
import com.lian.javareflect.domutil.annotion.MyMethod;
import com.lian.javareflect.domutil.annotion.MyParam;
import com.lian.javareflect.mapper.UserMapper;
import com.lian.javareflect.mapper.impl.UserMapperImpl;
import com.lian.javareflect.model.User;
import com.lian.javareflect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Service
@MyClass(classInfo = "用户信息服务")
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    @MyMethod(function = "查询用户信息")
    public User sel(@MyParam(comment = "用户主键标识") int id){
        return userMapper.sel(id);
    }

    @Override
    @MyMethod(function = "添加用户信息")
    public int add(@MyParam(comment = "添加的用户") User user) {
        return userMapper.add(user);
    }

    /**
     * 添加用户
     * @param user  用户
     * @param jdbcTemplate  数据源获取类
     * @return
     * @throws ClassNotFoundException   未找到类异常
     * @throws NoSuchMethodException    未找到方法异常
     * @throws IllegalAccessException   不合法连接异常
     * @throws InstantiationException   构造异常
     * @throws InvocationTargetException    呵呵
     */
    @Override
    @MyMethod(function = "使用jdbc来添加用户")
    public int addUserByJdbc(@MyParam(comment = "添加的用户") User user, @MyParam(comment = "数据源操作对象") JdbcTemplate jdbcTemplate) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        System.out.println("我找到这个方法了----->");
        Class<?> aClass = Class.forName("com.lian.javareflect.mapper.impl.UserMapperImpl");
        Method jdbcAdd = aClass.getDeclaredMethod("jdbcAdd", User.class,JdbcTemplate.class);
        UserMapperImpl userMapper = (UserMapperImpl)aClass.newInstance();
        jdbcAdd.invoke(userMapper,user,jdbcTemplate);
        return 0;
    }

    @Override
    @MyMethod(function = "更新用户信息")
    public User upd(@MyParam(comment = "更新的用户") User user) {
        return userMapper.upd(user);
    }

    @Override
    @MyMethod(function = "删除用户信息")
    public User del(@MyParam(comment = "用户主键标识") int id) {
        return userMapper.del(id);
    }
}
