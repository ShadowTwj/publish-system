package cn.tianwenjie.publish.system.web;

import cn.tianwenjie.publish.system.bean.HttpResult;
import cn.tianwenjie.publish.system.entity.Project;
import cn.tianwenjie.publish.system.service.ProjectService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author tianwj
 * @date 2018/2/6
 */
@RestController
@Slf4j
@RequestMapping(value = "/publish/")
@SuppressWarnings("Duplicates")
public class PublishController {
  @Resource
  private ProjectService projectService;

  @RequestMapping(value = "init", method = RequestMethod.GET)
  public HttpResult initPublish() {
    HttpResult httpResult = new HttpResult();

    try {
      List<Project> projects = projectService.getProjectList();

      Map<String, Object> map = Maps.newHashMap();
      map.put("projects", projects);

      httpResult.success();
      httpResult.setData(map);
    } catch (Exception e) {
      log.error("publish init error ", e);
      httpResult.error();
      httpResult.setMessage("初始化失败");
    }
    return httpResult;
  }
}
