package cn.tianwenjie.publish.system.service;

import cn.tianwenjie.publish.system.entity.PublishLog;
import cn.tianwenjie.publish.system.mapper.PublishLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tianwj
 * @date 2018/2/6
 */
@Service
@Slf4j
public class PublishLogService {
  @Resource
  private PublishLogMapper publishLogMapper;

  public List<PublishLog> getPublishLogList(int id) {
    return publishLogMapper.getListByHistoryId(id);
  }

}
