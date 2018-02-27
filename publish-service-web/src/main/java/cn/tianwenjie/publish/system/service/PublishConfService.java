package cn.tianwenjie.publish.system.service;

import cn.tianwenjie.publish.system.entity.PublishConf;
import cn.tianwenjie.publish.system.mapper.EnvironmentMapper;
import cn.tianwenjie.publish.system.mapper.PublishConfMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author tianwj
 * @date 2018/2/6
 */
@Service
@Slf4j
public class PublishConfService {
  @Resource
  private PublishConfMapper publishConfMapper;
  @Resource
  private EnvironmentMapper environmentMapper;

  public List<PublishConf> findByProjectId(@NonNull Integer projectId) {
    return publishConfMapper.findByProjectId(projectId);
  }

  public int insertPublishConfig(@NonNull PublishConf publishConf) {
    String environmentUniqueName = environmentMapper.findNameById(publishConf.getEnvironmentId());
    publishConf.setEnvironmentUniqueName(environmentUniqueName);
    publishConf.setCreateTime(new Date());
    publishConf.setUpdateTime(new Date());
    return publishConfMapper.insert(publishConf);
  }

  public int updatePublishConfig(@NonNull PublishConf publishConf) {
    String environmentUniqueName = environmentMapper.findNameById(publishConf.getEnvironmentId());
    publishConf.setEnvironmentUniqueName(environmentUniqueName);
    publishConf.setUpdateTime(new Date());
    return publishConfMapper.update(publishConf);
  }

  public int deletePublishConfig(@NonNull Integer publishConfigId) {
    return publishConfMapper.delete(publishConfigId);
  }
}
