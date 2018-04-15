package cn.tianwenjie.publish.system.service;

import cn.tianwenjie.publish.system.entity.Project;
import cn.tianwenjie.publish.system.entity.PublishHistory;
import cn.tianwenjie.publish.system.entity.PublishConf;
import cn.tianwenjie.publish.system.mapper.ProjectMapper;
import cn.tianwenjie.publish.system.mapper.PublishConfMapper;
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
  private PublishHistoryService publishHistoryService;
  @Resource
  private PublishConfMapper publishConfMapper;
  @Resource
  private ProjectMapper projectMapper;

  public Integer publish(@NonNull PublishHistory publishHistory) {
    PublishConf publishConf = publishConfMapper.findById(publishHistory.getPublishConfId());
    publishHistory.setEnvironmentId(publishConf.getEnvironmentId());
    publishHistory.setEnvironmentName(publishConf.getEnvironmentUniqueName());
    Project project = projectMapper.findById(publishHistory.getProjectId());
    publishHistory.setProjectName(project.getUniqueName());
    publishHistory.setStatus(0);
    publishHistory.setCreateTime(new Date());
    publishHistory.setUpdateTime(new Date());
    publishHistory.setCostTime(0L);
    return publishHistoryService.insert(publishHistory);
  }
}
