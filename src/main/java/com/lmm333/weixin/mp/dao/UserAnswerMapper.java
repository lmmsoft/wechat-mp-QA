package com.lmm333.weixin.mp.dao;

import com.lmm333.weixin.mp.model.Answer;
import com.lmm333.weixin.mp.model.User;
import com.lmm333.weixin.mp.model.UserAnswer;

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

    @Select("SELECT * " +
            " FROM t_user, t_user_answer " +
            " WHERE t_user.wechatUserId = t_user_answer.wechatUserId AND t_user_answer.userAnswerIndex = #{userAnswerIndex}")
    List<User> findUserByAnswerId(@Param("userAnswerIndex") int userAnswerIndex);

    @Select("SELECT * " +
            " FROM t_user, t_user_answer " +
            " WHERE t_user.wechatUserId = t_user_answer.wechatUserId AND t_user_answer.questionId = #{questionId}")
    List<User> findUserByQuestionId(@Param("questionId") int questionId);

    // answerId count
    // 11       2
    // 12       1
    @Select("SELECT userAnswerIndex AS answerId, count(*) AS userCount" +
            " FROM t_user_answer " +
            " WHERE questionId = #{questionId} " +
            " GROUP BY userAnswerIndex")
    List<Answer> findAnswerListByQuestionId(@Param("questionId") int questionId);
}
