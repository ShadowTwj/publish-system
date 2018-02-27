package cn.tianwenjie.publish.system.service;

import cn.tianwenjie.publish.system.entity.Project;
import cn.tianwenjie.publish.system.mapper.ProjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *
 * @author tianwj
 * @date 2018/1/3
 */
@Service
@Slf4j
public class ProjectService {
  @Resource
  private ProjectMapper projectMapper;

  public int addProject(@NonNull Project project) {
    project.setCreateTime(new Date());
    project.setUpdateTime(new Date());
    return projectMapper.insertProject(project);
  }

  public List<Project> getProjectList() {
    return projectMapper.findAll();
  }

  public int editProject(@NonNull Project project) {
    project.setUpdateTime(new Date());
    return projectMapper.updateProjecct(project);
  }

  public int remove(@NonNull int id) {
    return projectMapper.deleteProject(id);
  }

  public String getGit(Integer id){
    return projectMapper.getGitById(id);
  }
}
