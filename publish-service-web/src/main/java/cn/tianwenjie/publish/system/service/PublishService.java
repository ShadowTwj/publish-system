package cn.tianwenjie.publish.system.service;

import cn.tianwenjie.publish.system.entity.Project;
import cn.tianwenjie.publish.system.entity.Publish;
import cn.tianwenjie.publish.system.entity.PublishConf;
import cn.tianwenjie.publish.system.mapper.ProjectMapper;
import cn.tianwenjie.publish.system.mapper.PublishConfMapper;
import cn.tianwenjie.publish.system.mapper.PublishMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author tianwj
 * @date 2018/2/6
 */
@Service
@Slf4j
public class PublishService {
  @Resource
  private PublishMapper publishMapper;
  @Resource
  private PublishConfMapper publishConfMapper;
  @Resource
  private ProjectMapper projectMapper;

  public Integer insert(@NonNull Publish publish) {
    PublishConf publishConf = publishConfMapper.findById(publish.getPublishConfId());
    publish.setEnvironmentId(publishConf.getEnvironmentId());
    publish.setEnvironmentName(publishConf.getEnvironmentUniqueName());
    Project project = projectMapper.findById(publish.getProjectId());
    publish.setProjectName(project.getUniqueName());
    publish.setStatus(0);
    publish.setCreateTime(new Date());
    publish.setUpdateTime(new Date());
    publish.setCostTime(0L);
    return publishMapper.insert(publish);
  }
}
