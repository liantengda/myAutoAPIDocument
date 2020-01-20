package com.lian.javareflect.mapper.impl;

import com.lian.javareflect.mapper.UserMapper;
import com.lian.javareflect.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 手写jdbc层
 */
@Component
public class UserMapperImpl implements UserMapper {


    @Override
    public User sel(int id) {
        return null;
    }

    @Override
    public int add(User user) {
        return 0;
    }

    @Override
    public int jdbcAdd(User user,JdbcTemplate jdbcTemplate) {
        System.out.println("我找到jdbc底层了---->");
        if(jdbcTemplate!=null){
            System.out.println("数据源信息--->"+jdbcTemplate.getDataSource());
        }
        String sql = "insert into user (userName,passWord,realName) values (?,?,?);";

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,user.getUserName());
                preparedStatement.setString(2,user.getPassWord());
                preparedStatement.setString(3,user.getRealName());
                return preparedStatement;
            }
        });
        return 1;
    }

    @Override
    public User upd(User user) {
        return null;
    }

    @Override
    public User del(int id) {
        return null;
    }


}
