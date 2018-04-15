package cn.tianwenjie.publish.system.web;

import cn.tianwenjie.publish.system.entity.PublishHistory;
import cn.tianwenjie.publish.system.service.PublishHistoryService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tianwj
 * @date 2018/4/15
 */
@RestController
@Slf4j
@RequestMapping(value = "/publish-history/")
public class PublishHistoryController {

  @Resource
  private PublishHistoryService publishHistoryService;

  @RequestMapping("list")
  public List<PublishHistory> getPublishHistoryList() {
    try {
      return publishHistoryService.getPublishHistoryList();
    } catch (Exception e) {
      log.error("getPublishHistoryList is error", e);
      return Lists.newArrayList();
    }
  }
}
