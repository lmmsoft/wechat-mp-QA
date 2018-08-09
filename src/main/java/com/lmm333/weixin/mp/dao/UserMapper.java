package com.lmm333.weixin.mp.dao;

import com.lmm333.weixin.mp.model.User;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public interface UserMapper {

    @Insert("INSERT INTO t_user (wechatUserId, registerType, nickname, headimgurl, userName)" +
            " VALUES( #{wechatUserId}, #{registerType}, #{nickname}, #{headimgurl}, #{userName} )")
    void insert(User user);

    @Update("REPLACE INTO t_user (wechatUserId, registerType)" +
            " VALUES( #{wechatUserId}, #{registerType} )")
    void replaceUserRegisterTypeByWechatUserId(User user);

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


    @UpdateProvider(type = UserDaoProvider.class, method = "updateUser")
    void updateUser(User user);

    class UserDaoProvider {
        public String updateUser(final User user) {
            return new SQL() {{
                UPDATE("t_user");

                SET("registerType = #{registerType}");

                if (user.getWechatUserId() != null) {
                    SET("wechatUserId = #{wechatUserId}");
                }

                if (user.getNickname() != null) {
                    SET("nickname = #{nickname}");
                }

                if (user.getHeadimgurl() != null) {
                    SET("headimgurl = #{headimgurl}");
                }

                if (user.getUserName() != null) {
                    SET("userName = #{userName}");
                }

                if (user.getSex() != null) {
                    SET("sex = #{sex}");
                }

                if (user.getLanguage() != null) {
                    SET("language = #{language}");
                }

                if (user.getCity() != null) {
                    SET("city = #{city}");
                }

                if (user.getProvince() != null) {
                    SET("province = #{province}");
                }

                if (user.getWechatUserId() != null) {
                    SET("country = #{country}");
                }

                if (user.getAccess_token() != null) {
                    SET("access_token = #{access_token}");
                }

                if (user.getRefresh_token() != null) {
                    SET("refresh_token = #{refresh_token}");
                }

                if (user.getUnionid() != null) {
                    SET("unionid = #{unionid}");
                }

                if (user.getOpenid() != null) {
                    SET("openid = #{openid}");
                }

                if (user.getCode() != null) {
                    SET("code = #{code}");
                }

                WHERE("wechatUserId = #{wechatUserId}");
            }}.toString();
        }
    }
}
