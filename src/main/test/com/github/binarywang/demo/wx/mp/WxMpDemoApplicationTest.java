package com.github.binarywang.demo.wx.mp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WxMpDemoApplicationTest {
    @Test
    public void helloWorld() {
        assertEquals(3, 1 + 2);
        assertNotEquals(1, 1 + 1);
    }
}