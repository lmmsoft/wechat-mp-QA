package com.github.binarywang.demo.wx.mp;

import com.github.binarywang.demo.wx.mp.dao.HelloWorldMapper;
import com.github.binarywang.demo.wx.mp.model.HelloWorldModel;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisTests {

    @Autowired
    private HelloWorldMapper helloWorldMapper;

    @Test
    public void test() {

        helloWorldMapper.insert(new HelloWorldModel(1, "tom", 81.8f));
        List<HelloWorldModel> list = helloWorldMapper.findAll();
        Assert.assertEquals(1, list.size());

//        helloWorldMapper.update("tom", 81.8f);

        HelloWorldModel helloWorldModel = helloWorldMapper.findByName("tom");
        Assert.assertEquals("tom", helloWorldModel.getName());
        Assert.assertEquals(81.8f, helloWorldModel.getWeight(), 0.01f);

        helloWorldModel.setWeight(88.8f);
        helloWorldMapper.update(helloWorldModel);
        Assert.assertEquals(88.8f, helloWorldModel.getWeight(), 0.01f);

        Assert.assertEquals(1, helloWorldMapper.findAll().size());

        helloWorldMapper.delete(helloWorldModel.getId());
        Assert.assertEquals(0, helloWorldMapper.findAll().size());
    }
}
