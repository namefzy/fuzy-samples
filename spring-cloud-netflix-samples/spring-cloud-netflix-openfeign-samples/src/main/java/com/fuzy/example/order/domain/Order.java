package com.fuzy.example.order.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author fuzy
 * @version 1.0
 * @Description
  
 * @date 2021/5/7 16:52
 */
@Data
public class Order {

    private int id;

    private String userId;

    private String orderCode;

    private Date createDate;

    private String orderStatus;

    private String remark;
}
