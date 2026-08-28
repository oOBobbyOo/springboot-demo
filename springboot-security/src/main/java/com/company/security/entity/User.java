package com.company.security.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Henry
 */
@Data
@TableName("sys_user")
public class User extends Base {

    /**
     * 用户名 (登录账号)
     * 数据库需建立唯一索引 (UNIQUE INDEX)
     */
    @TableField("username")
    private String username;

    /**
     * 登录密码 (BCrypt 加密存储)
     * 必须加 @JsonIgnore！防止某些全局序列化配置导致密码明文/密文被输出到日志或前端。
     * 数据库字段长度建议 VARCHAR(100)，BCrypt 加密后固定为 60 位。
     */
    @JsonIgnore
    @TableField("password")
    private String password;

    /**
     * 用户昵称 (展示名称)
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 用户邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 手机号码
     * 若支持手机号登录，数据库需建立唯一索引。
     */
    @TableField("phone")
    private String phone;

    /**
     * 头像 URL
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 性别 (0:未知, 1:男, 2:女)
     * 使用 TINYINT 存储，代码中通过枚举或字典翻译，不要直接存 "男"/"女"。
     */
    @TableField("gender")
    private Integer gender;

    /**
     * 帐号状态 (0:正常, 1:停用)
     * 区分于 BaseEntity 中的 deleted (逻辑删除)。
     * 停用是指账号存在但禁止登录，删除是指数据彻底失效。
     */
    @TableField("status")
    private Integer status;

    /**
     * 最后登录 IP
     */
    @TableField("login_ip")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @TableField("login_time")
    private LocalDateTime loginTime;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}
