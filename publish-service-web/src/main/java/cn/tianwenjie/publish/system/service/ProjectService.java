package cn.tianwenjie.publish.system.service;

import cn.tianwenjie.publish.system.entity.Project;
import cn.tianwenjie.publish.system.mapper.ProjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by tianwj on 2018/1/3.
 */
@Service
@Slf4j
public class ProjectService {
  @Resource
  private ProjectMapper projectMapper;

  public int addProject(@Nonnull Project project) {
    project.setCreateTime(new Date());
    project.setUpdateTime(new Date());
    return projectMapper.insertProject(project);
  }

  public List<Project> getProjectList() {
    return projectMapper.findAll();
  }

  public int editProject(@Nonnull Project project) {
    project.setUpdateTime(new Date());
    return projectMapper.updateProjecct(project);
  }

  public int remove(@Nonnull int id) {
    return projectMapper.deleteProject(id);
  }
}
