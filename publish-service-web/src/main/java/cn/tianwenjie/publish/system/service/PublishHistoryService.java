package cn.tianwenjie.publish.system.service;

import cn.tianwenjie.publish.system.entity.PublishHistory;
import cn.tianwenjie.publish.system.mapper.PublishHistoryMapper;
import lombok.NonNull;
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
public class PublishHistoryService {
  @Resource
  private PublishHistoryMapper publishHistoryMapper;

  public int insert(@NonNull PublishHistory publishHistory) {
    return publishHistoryMapper.insert(publishHistory);
  }

  public List<PublishHistory> getPublishHistoryList() {
    return publishHistoryMapper.getPublishHistoryList();
  }
}
