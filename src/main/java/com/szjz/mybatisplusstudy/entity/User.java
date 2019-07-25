package com.szjz.mybatisplusstudy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * author:szjz
 * date:2019/7/25
 */

@Data
//@TableName("mp_user") //默认类名驼峰转下划线为表名 也可指定表名
public class User {

    /** 主键
     * 默认为id字段
     * */
//    @TableId //指定id
//    private Long userId;

    /** 主键 */
    private Long id;

    /** 姓名 */
//    @TableField("nickname") //指定数据库中的列名
    private String name;

    /** 年龄 */
    private Integer age;

    /** 邮箱 */
    private String email;

    /** 直属上级 */
    private Long managerId;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 备注 */
    //不要求数据库表中有对用的列 不进行序列化
//    1,@TableField(exist = false)
    private transient String remark;
//    private  String remark;

}
