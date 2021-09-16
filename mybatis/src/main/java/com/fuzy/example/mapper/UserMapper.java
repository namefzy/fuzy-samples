package com.fuzy.example.mapper;

import com.fuzy.example.domain.User;

import java.util.List;

/**
 * @author fuzy
 * @version 1.0
 * @Description
  
 * @date 2021/5/20 17:00
 */
public interface UserMapper {

    List<User> selectUserList();
}
