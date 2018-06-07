package cn.tianwenjie.publish.system.mapper;

import cn.tianwenjie.publish.system.entity.PublishHistory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by tianwj on 2017/12/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PublishHistoryMapperTest {
  @Resource
  PublishHistoryMapper publishHistoryMapper;

  @Test
  public void insertTest() {
    PublishHistory publishHistory = PublishHistory.builder().publishConfId(1).projectId(1).environmentId(1).branch("test").status(0).costTime(0L).build();
    int result = publishHistoryMapper.insert(publishHistory);
    System.out.println("------" + publishHistory.getId());
    System.out.println("------" + result);
  }
}
