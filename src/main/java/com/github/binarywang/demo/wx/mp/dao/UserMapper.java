package com.github.binarywang.demo.wx.mp.dao;

import com.github.binarywang.demo.wx.mp.model.User;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {

    @Insert("INSERT INTO t_user (wechatUserId, wechatName, wechatImageUrl, userName, registerType)" +
            " VALUES( #{wechatUserId}, #{wechatName}, #{wechatImageUrl}, #{userName}, #{registerType} )")
    void insert(User user);

    @Update("UPDATE t_user" +
            " SET wechatName = #{wechatName}, wechatImageUrl = #{wechatImageUrl}, userName = #{userName}, registerType = #{registerType}" +
            " WHERE wechatUserId = #{wechatUserId}")
    void update(User user);

    @Delete("DELETE FROM t_user" +
            " WHERE id = #{id}")
    void deleteById(int id);

    @Delete("DELETE" +
            " FROM t_user" +
            " WHERE wechatUserId = #{wechatUserId}")
    void deleteByWechatUserId(String wechatUserId);

    @Select("SELECT *" +
            " FROM t_user" +
            " WHERE wechatUserId = #{wechatUserId}")
    User findByWechatUserId(@Param("wechatUserId") String wechatUserId);

    @Select("SELECT * FROM t_user")
    List<User> findAll();
}
