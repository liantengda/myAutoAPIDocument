package com.lian.javareflect.mapper;

import com.lian.javareflect.model.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {


    User sel(int id);

    int add(User user);

    int jdbcAdd(User user, JdbcTemplate jdbcTemplate);

    User upd(User user);

    User del(int id);
}
