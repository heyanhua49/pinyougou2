package cn.itcast.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-redis.xml")
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    //字符串
    @Test
    public void string(){
        redisTemplate.boundValueOps("string_key").set("i_am_ljb can we chat.传智播客");
        Object obj = redisTemplate.boundValueOps("string_key").get();
        System.out.println(obj);
    }

    //散列
    @Test
    public void hash(){
        redisTemplate.boundHashOps("h_key").put("f1", "v1");
        redisTemplate.boundHashOps("h_key").put("f2", "v2");
        redisTemplate.boundHashOps("h_key").put("f3", "v3");
        Object obj = redisTemplate.boundHashOps("h_key").values();
        System.out.println(obj);
    }

    //列表
    @Test
    public void list(){
        redisTemplate.boundListOps("l_key").leftPush("b");
        redisTemplate.boundListOps("l_key").leftPush("a");
        redisTemplate.boundListOps("l_key").rightPush("c");
        //起始索引号，结束索引号；-1表示最后的索引号
        Object obj = redisTemplate.boundListOps("l_key").range(0, -1);
        System.out.println(obj);
    }

    //集合
    @Test
    public void set(){
        redisTemplate.boundSetOps("set_key").add("a","b", "c");
        Object obj = redisTemplate.boundSetOps("set_key").members();
        System.out.println(obj);
    }

    //有序集合；按照分值升序排序
    @Test
    public void sortedSet(){
        redisTemplate.boundZSetOps("z_key").add("c", 10);
        redisTemplate.boundZSetOps("z_key").add("a", 20);
        redisTemplate.boundZSetOps("z_key").add("b", 15);
        Object obj = redisTemplate.boundZSetOps("z_key").range(0, -1);
        System.out.println(obj);
    }
}
