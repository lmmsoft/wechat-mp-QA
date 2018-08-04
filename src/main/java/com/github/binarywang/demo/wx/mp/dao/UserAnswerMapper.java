package com.github.binarywang.demo.wx.mp.dao;

import com.github.binarywang.demo.wx.mp.model.UserAnswer;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserAnswerMapper {

    @Insert("INSERT INTO t_user_answer(wechatUserId, questionId, userAnswerIndex, updateTime)" +
            " VALUES( #{wechatUserId}, #{questionId}, #{userAnswerIndex}, #{updateTime} )")
    void insert(UserAnswer userAnswer);

    @Insert("REPLACE INTO t_user_answer(wechatUserId, questionId, userAnswerIndex, updateTime)" +
            " VALUES( #{wechatUserId}, #{questionId}, #{userAnswerIndex}, #{updateTime} )")
    boolean replace(UserAnswer userAnswer);

    @Update("UPDATE t_user_answer" +
            " SET questionId = #{questionId}, userAnswerIndex = #{userAnswerIndex}, updateTime = #{updateTime}" +
            " WHERE wechatUserId = #{wechatUserId}")
    void update(UserAnswer userAnswer);

    @Select("SELECT *" +
            " FROM t_user_answer" +
            " WHERE userAnswerIndex = #{userAnswerIndex}")
    List<UserAnswer> findByUserAnswerIndex(@Param("userAnswerIndex") int userAnswerIndex);

    @Select("SELECT *" +
            " FROM t_user_answer" +
            " WHERE questionId = #{questionId}")
    List<UserAnswer> findByQuestionId(@Param("questionId") int questionId);

    @Select("SELECT *" +
            " FROM t_user_answer")
    List<UserAnswer> findAll();

    @Delete("DELETE" +
            " FROM t_user_answer" +
            " WHERE id = #{id}")
    void deleteById(int id);
}
