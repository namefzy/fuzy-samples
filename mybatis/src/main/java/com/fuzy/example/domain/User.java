package com.fuzy.example.domain;

import lombok.Data;

/**
 * @author fuzy
 * @version 1.0
 * @Description
  
 * @date 2021/5/20 15:55
 */
@Data
public class User {
    private Integer id;
    private String userName;
    private String realName;
    private String password;
    private Integer age;
    private Integer dId;
}

