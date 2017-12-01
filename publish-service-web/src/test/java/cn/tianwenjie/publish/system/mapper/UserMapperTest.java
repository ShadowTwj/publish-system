package cn.tianwenjie.publish.system.mapper;

import cn.tianwenjie.publish.system.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by tianwj on 2017/12/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserMapperTest {
  @Resource
  UserMapper userMapper;

  @Test
  public void insertUserTest(){
    userMapper.insertUser(User.builder().account("admin").password("123455").nickname("admin").creater("system").createTime(new Date()).updateTime(new Date()).build());
  }
}
