package com.szjz.mybatisplusstudy;

/**
 * author:szjz
 * date:2019/7/26
 *
 * MP sqlCondition 的增强版
 */
public class SqlCondition extends com.baomidou.mybatisplus.annotation.SqlCondition {

//    public static final String EQUAL = "%s=#{%s}";
//    public static final String NOT_EQUAL = "%s&lt;&gt;#{%s}";
//    public static final String LIKE = "%s LIKE CONCAT('%%',#{%s},'%%')";
//    public static final String LIKE_LEFT = "%s LIKE CONCAT('%%',#{%s})";
//    public static final String LIKE_RIGHT = "%s LIKE CONCAT(#{%s},'%%')";

//    public SqlCondition() {
//    }

    /** 大于 */
    public static final String GT = "%s&gt;#{%s}";
    /** 大于等于 */
    public static final String GE = "%s&gt;=#{%s}";
    /** 小于 */
    public static final String LT = "%s&lt;#{%s}";
    /** 小于等于 */
    public static final String LE = "%s&lt;=#{%s}";

}
