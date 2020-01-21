package com.lian.javareflect.domutil.domInterface;

import com.lian.javareflect.domutil.annotion.MyClass;
import com.lian.javareflect.domutil.annotion.MyMethod;
import com.lian.javareflect.domutil.annotion.MyParam;
import com.lian.javareflect.domutil.annotion.MyReturn;
import com.lian.javareflect.model.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.InvocationTargetException;

/**
 * @Author Ted
 */
@MyClass(classInfo = "用户信息实现服务")
public interface UserService {
   /**
    * 查询用户信息
    *
    * @param id 用户信息主键
    * @return
    */
   @MyMethod(function = "查询用户信息")
   @MyReturn(comment = "查询到的用户信息")
   User sel(@MyParam(comment = "用户主键标识") int id);

   /**
    * 添加用户信息
    *
    * @param user 新增用户信息
    * @return
    */
   @MyMethod(function = "添加用户信息")
   @MyReturn(comment = "添加用户信息返回结果")
   int add(@MyParam(comment = "添加的用户") User user);

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
   @MyMethod(function = "使用jdbc来添加用户")
   @MyReturn(comment = "添加用户信息返回结果")
   int addUserByJdbc(@MyParam(comment = "添加的用户") User user, @MyParam(comment = "数据源操作对象") JdbcTemplate jdbcTemplate) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException;

   /**
    * 更新用户信息
    * @param user
    * @return
    */
   @MyMethod(function = "更新用户信息")
   @MyReturn(comment = "更新用户信息返回结果")
   User upd(@MyParam(comment = "更新的用户") User user);

   /**
    * 根据id删除用户信息
    * @param id
    * @return
    */
   @MyMethod(function = "删除用户信息")
   @MyReturn(comment = "删除用户信息返回结果")
   User del(@MyParam(comment = "用户主键标识") int id);
}
