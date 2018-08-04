package com.lmm333.weixin.mp.dao;

import com.lmm333.weixin.mp.model.HelloWorldModel;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HelloWorldMapper {

    @Select("SELECT * FROM t_hello_world WHERE NAME = #{name}")
    HelloWorldModel findByName(@Param("name") String name);

    @Select("SELECT * FROM t_hello_world")
    List<HelloWorldModel> findAll();

//    @Update("UPDATE t_hello_world SET NAME = #{name}, WEIGHT = #{weight} WHERE NAME = #{name}")
//    void update(@Param("name") String name, @Param("weight") float weight);

    @Insert("INSERT INTO t_hello_world(NAME,WEIGHT) VALUES(#{name}, #{weight})")
    void insert(HelloWorldModel helloWorldModel);

    @Update("UPDATE t_hello_world SET NAME = #{name}, WEIGHT = #{weight} WHERE NAME = #{name}")
    void update(HelloWorldModel helloWorldModel);

    @Delete("DELETE FROM t_hello_world WHERE ID = #{id}")
    void delete(Integer id);
}
