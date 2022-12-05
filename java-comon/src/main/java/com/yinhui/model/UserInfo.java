package com.yinhui.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yinhui
 * @date 2022/8/24
 */
@Data
public class UserInfo implements Serializable {

    private String name;

    private Integer age;

    private String address;

    private String phone;

}
