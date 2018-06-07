package cn.tianwenjie.publish.system.web;

import cn.tianwenjie.publish.system.entity.PublishLog;
import cn.tianwenjie.publish.system.service.PublishLogService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tianwj
 * @date 2018/4/15
 */
@RestController
@Slf4j
@RequestMapping(value = "/publish-log")
public class PublishLogController {

  @Resource
  private PublishLogService publishLogService;

  @RequestMapping("")
  public List<PublishLog> getPublishLogList(@RequestParam int id) {
    try {
      return publishLogService.getPublishLogList(id);
    } catch (Exception e) {
      log.error("getPublishLogList is error", e);
      return Lists.newArrayList();
    }
  }
}
