package com.lian.javareflect.controller;

import com.lian.javareflect.model.User;
import com.lian.javareflect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author:wjup
 * @Date: 2018/9/26 0026
 * @Time: 14:42
 */

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "user/{id}",method = RequestMethod.GET)
    public User GetUser(@PathVariable int id){
        return userService.sel(id);
    }

    @RequestMapping(value = "user/{id}",method = RequestMethod.POST)
    public int addUser(@RequestBody User user){
        return userService.add(user);
    }

    @RequestMapping(value = "user/{id}",method = RequestMethod.PUT)
    public User updUser(@PathVariable int id){
        return userService.sel(id);
    }

    @RequestMapping(value = "user/{id}",method = RequestMethod.DELETE)
    public User delUser(@PathVariable int id){
        return userService.sel(id);
    }
}
