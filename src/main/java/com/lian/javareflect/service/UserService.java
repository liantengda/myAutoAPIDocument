package com.lian.javareflect.service;

import com.lian.javareflect.model.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.InvocationTargetException;

/**
 *
 */
public interface UserService {
   /**
    * 查询用户信息
    *
    * @param id 用户信息主键
    * @return
    */
   User sel(int id);

   /**
    * 添加用户信息
    *
    * @param user 新增用户信息
    * @return
    */
   int add(User user);

   /**
    * 使用jdbc添加用户信息
    *
    * @param user 新增用户信息
    * @param jdbcTemplate 数据源操作类
    * @return  返回操作值
    * @throws ClassNotFoundException
    * @throws NoSuchMethodException
    * @throws IllegalAccessException
    * @throws InstantiationException
    * @throws InvocationTargetException
    */
   int addUserByJdbc(User user, JdbcTemplate jdbcTemplate) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException;

   /**
    * 更新用户信息
    * @param user
    * @return
    */
   User upd(User user);

   /**
    * 根据id删除用户信息
    * @param id
    * @return
    */
   User del(int id);
}
