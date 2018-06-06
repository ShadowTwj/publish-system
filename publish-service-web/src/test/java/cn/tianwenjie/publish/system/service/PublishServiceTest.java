package cn.tianwenjie.publish.system.service;

import cn.tianwenjie.publish.system.entity.PublishHistory;
import cn.tianwenjie.publish.system.mapper.PublishHistoryMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author tianwj
 * @date 2018/6/2
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PublishServiceTest {
  @Resource
  private PublishService publishService;
  @Resource
  private PublishHistoryMapper publishHistoryMapper;

  @Test
  public void pullProjectTest() {
    PublishHistory publishHistory = publishHistoryMapper.findById(14);
    publishService.pullProject(publishHistory);
  }

  @Test
  public void compileTest() {
    PublishHistory publishHistory = publishHistoryMapper.findById(14);
    publishService.compile(publishHistory);
  }

  @Test
  public void deployTest() {
    PublishHistory publishHistory = publishHistoryMapper.findById(14);
    publishService.deploy(publishHistory);
  }
}
