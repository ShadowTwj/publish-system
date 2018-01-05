package cn.tianwenjie.publish.system.service;

import cn.tianwenjie.publish.system.entity.Project;
import cn.tianwenjie.publish.system.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by tianwj on 2018/1/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ProjectServiceTest {
  @Resource
  ProjectService projectService;

  @Test
  public void addProjectTest(){
    Project project = Project.builder().uniqueName("test2").git("https://github.com/ShadowTwj/webChat.git").manager("tianwj").remark("用来测试").createUser("tianwj").updateUser("tianwj").timeRestrict(0).build();
    System.out.println("------------");
    System.out.println(projectService.addProject(project));
    System.out.println("------------");
  }

}
