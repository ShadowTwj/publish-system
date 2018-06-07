package cn.tianwenjie.publish.system.web;

import cn.tianwenjie.publish.system.bean.HttpResult;
import cn.tianwenjie.publish.system.entity.Environment;
import cn.tianwenjie.publish.system.entity.Project;
import cn.tianwenjie.publish.system.entity.PublishHistory;
import cn.tianwenjie.publish.system.entity.PublishConf;
import cn.tianwenjie.publish.system.service.EnvironmentService;
import cn.tianwenjie.publish.system.service.ProjectService;
import cn.tianwenjie.publish.system.service.PublishConfService;
import cn.tianwenjie.publish.system.service.PublishHistoryService;
import cn.tianwenjie.publish.system.service.UserService;
import cn.tianwenjie.publish.system.utils.GitHubUtils;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
  @Resource
  private UserService userService;
  @Resource
  private PublishConfService publishConfService;
  @Resource
  private EnvironmentService environmentService;
  @Resource
  private PublishHistoryService publishHistoryService;

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

  @RequestMapping(value = "init/branch", method = RequestMethod.POST)
  public HttpResult initPublishBranch(@RequestBody String data) {
    HttpResult httpResult = new HttpResult();
    Map<String, Object> map = Maps.newHashMap();

    Integer projectId = JSONObject.parseObject(data).getInteger("projectId");
    String userName = JSONObject.parseObject(data).getString("userName");

    if (projectId == null) {
      httpResult.error();
      httpResult.setMessage("错误：项目ID缺失");
      return httpResult;
    }

    String git = projectService.getGit(projectId);
    String token = userService.getToken(userName);

    try {
      Set<String> branches = GitHubUtils.getBranches(token, git);
      List<String> tags = GitHubUtils.getTags(token, git);

      List<Map> codes = Lists.newArrayList();
      Map<String, Object> tempMap = Maps.newHashMap();
      tempMap.put("group", "branches");
      tempMap.put("options", branches);
      codes.add(tempMap);

      tempMap = Maps.newHashMap();
      tempMap.put("group", "tags");
      tempMap.put("options", tags);
      codes.add(tempMap);

      map.put("codes", codes);
    } catch (Exception e) {
      log.error("init publish conf error,git={},token={}", git, token, e);
      httpResult.error();
      httpResult.setMessage("错误：获取分支、Tag失败，请检查git地址等是否正确或token是否过期");
      return httpResult;
    }

    httpResult.success();
    httpResult.setData(map);

    return httpResult;
  }

  @RequestMapping(value = "init/config", method = RequestMethod.GET)
  public HttpResult initPublishConfig(@RequestParam Integer projectId) {
    HttpResult httpResult = new HttpResult();

    if (projectId == null) {
      httpResult.error();
      httpResult.setMessage("错误：项目ID缺失");
      return httpResult;
    }

    try {
      Map<String, Object> map = Maps.newHashMap();
      List<PublishConf> publishConfList = publishConfService.findByProjectId(projectId);
      map.put("publishConfList", publishConfList);
      httpResult.success();
      httpResult.setData(map);
    } catch (Exception e) {
      log.error("get publish config error, projectId={}", projectId, e);
      httpResult.error();
      httpResult.setMessage("错误：获取publishConfig失败");
      return httpResult;
    }

    return httpResult;
  }

  @RequestMapping(value = "init/environment", method = RequestMethod.GET)
  public HttpResult initPublishEnvironment() {
    HttpResult httpResult = new HttpResult();

    try {
      List<Environment> environmentList = environmentService.getEnvironmentList();
      httpResult.success();
      httpResult.setData(environmentList);
    } catch (Exception e) {
      log.error("get environment error", e);
      httpResult.error();
      httpResult.setMessage("错误：获取环境失败");
      return httpResult;
    }
    return httpResult;
  }

  @RequestMapping(value = "config/add-config", method = RequestMethod.POST)
  public HttpResult addPublishConfig(@RequestBody PublishConf publishConf) {
    HttpResult httpResult = new HttpResult();

    if (publishConf == null) {
      httpResult.error();
      httpResult.setMessage("缺少配置信息");
      return httpResult;
    }

    if (publishConf.getProjectId() == null) {
      httpResult.warning();
      httpResult.setMessage("请选择项目");
      return httpResult;
    }

    if (publishConf.getEnvironmentId() == null) {
      httpResult.warning();
      httpResult.setMessage("请选择环境");
      return httpResult;
    }

    try {
      int result = publishConfService.insertPublishConfig(publishConf);
      if (result == 1) {
        httpResult.success();
        httpResult.setMessage("新增成功");
      } else {
        httpResult.warning();
        httpResult.setMessage("保存失败");
      }
    } catch (Exception e) {
      log.error("insert publishConfig error,publishConfig={}", publishConf, e);
      httpResult.error();
      httpResult.setMessage("新增配置失败");
    }

    return httpResult;
  }

  @RequestMapping(value = "config/edit-config", method = RequestMethod.POST)
  public HttpResult editPublishConfig(@RequestBody PublishConf publishConf) {
    HttpResult httpResult = new HttpResult();

    if (publishConf == null) {
      httpResult.error();
      httpResult.setMessage("缺少配置信息");
      return httpResult;
    }

    if (publishConf.getProjectId() == null) {
      httpResult.warning();
      httpResult.setMessage("请选择项目");
      return httpResult;
    }

    if (publishConf.getEnvironmentId() == null) {
      httpResult.warning();
      httpResult.setMessage("请选择环境");
      return httpResult;
    }

    try {
      int result = publishConfService.updatePublishConfig(publishConf);
      if (result == 1) {
        httpResult.success();
        httpResult.setMessage("编辑成功");
      } else {
        httpResult.warning();
        httpResult.setMessage("更新失败");
      }
    } catch (Exception e) {
      log.error("update publishConfig error,publishConfig={}", publishConf, e);
      httpResult.error();
      httpResult.setMessage("编辑配置失败");
    }

    return httpResult;
  }

  @RequestMapping(value = "config/del-config", method = RequestMethod.POST)
  public HttpResult delPublishConfig(@RequestBody Integer id) {
    HttpResult httpResult = new HttpResult();

    if (id == null) {
      httpResult.error();
      httpResult.setMessage("缺少配置Id");
      return httpResult;
    }

    try {
      int result = publishConfService.deletePublishConfig(id);
      if (result == 1) {
        httpResult.success();
        httpResult.setMessage("删除成功");
      } else {
        httpResult.warning();
        httpResult.setMessage("删除失败");
      }
    } catch (Exception e) {
      log.error("update publishConfig error,publishConfigId={}", id, e);
      httpResult.error();
      httpResult.setMessage("删除配置失败");
    }

    return httpResult;
  }

  @RequestMapping(value = "publish", method = RequestMethod.POST)
  public HttpResult publish(@RequestBody PublishHistory publishHistory) {
    HttpResult httpResult = new HttpResult();

    if (publishHistory == null) {
      httpResult.error();
      httpResult.setMessage("缺少发布信息");
      return httpResult;
    }

    if (publishHistory.getProjectId() == null) {
      httpResult.warning();
      httpResult.setMessage("请选择项目");
      return httpResult;
    }

    if (Strings.isNullOrEmpty(publishHistory.getBranch())) {
      httpResult.warning();
      httpResult.setMessage("请选择分支");
      return httpResult;
    }

    if (publishHistory.getPublishConfId() == null) {
      httpResult.warning();
      httpResult.setMessage("请选择配置");
      return httpResult;
    }

    try {
      Integer result = publishHistoryService.publishProject(publishHistory);
      if (result != null) {
        httpResult.success();
        httpResult.setData(result);
      } else {
        httpResult.warning();
        httpResult.setMessage("发布失败");
      }

    } catch (Exception e) {
      log.error("publish error, publishHistory={}", publishHistory, e);
      httpResult.error();
      httpResult.setMessage("发布服务失败");
    }

    return httpResult;
  }
}
