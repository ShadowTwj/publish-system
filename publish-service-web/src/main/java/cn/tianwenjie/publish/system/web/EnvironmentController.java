package cn.tianwenjie.publish.system.web;

import cn.tianwenjie.publish.system.bean.BaseResult;
import cn.tianwenjie.publish.system.entity.Environment;
import cn.tianwenjie.publish.system.service.EnvironmentService;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tianwj
 * @date 2018/1/23
 */
@RestController
@Slf4j
@RequestMapping(value = "/environment/")
@SuppressWarnings("Duplicates")
public class EnvironmentController {
  @Resource
  private EnvironmentService environmentService;

  private static final String SUCCESS = "success";
  private static final String WARNING = "warning";
  private static final String ERROR = "error";

  @RequestMapping(value = "list", method = RequestMethod.GET)
  public List<Environment> getEnvironmentList() {
    try {
      return environmentService.getEnvironmentList();
    } catch (Exception e) {
      log.error("getEnvironmentList error ", e);
      return Lists.newArrayList();
    }
  }

  @RequestMapping(value = "add", method = RequestMethod.POST)
  public BaseResult addEnvironment(@RequestBody Environment environment) {
    BaseResult baseResult = BaseResult.builder().build();
    if (environment == null) {
      baseResult.setType(ERROR);
      baseResult.setMessage("缺少环境信息");
      return baseResult;
    }

    if (Strings.isNullOrEmpty(environment.getIp())) {
      baseResult.setType(WARNING);
      baseResult.setMessage("请填写ip地址");
      return baseResult;
    }

    if (Strings.isNullOrEmpty(environment.getUserName())) {
      baseResult.setType(WARNING);
      baseResult.setMessage("请填写该服务器的登录用户名");
      return baseResult;
    }

    if (Strings.isNullOrEmpty(environment.getPassword())) {
      baseResult.setType(WARNING);
      baseResult.setMessage("请填写该服务器的登录密码");
      return baseResult;
    }

    //todo 判断ip是否正确，是否能登录成功

    try {
      int result = environmentService.insertEnvironment(environment);
      if (result == 1) {
        baseResult.setType(SUCCESS);
        baseResult.setMessage("新增成功");
      } else {
        baseResult.setType(WARNING);
        baseResult.setMessage("保存失败");
      }
    } catch (Exception e) {
      log.error("addEnvironment error, environment={} ", environment, e);
      baseResult.setType(ERROR);
      baseResult.setMessage("新增失败");
    }
    return baseResult;
  }

  @RequestMapping(value = "edit", method = RequestMethod.POST)
  public BaseResult editEnvironment(@RequestBody Environment environment) {
    BaseResult baseResult = BaseResult.builder().build();
    if (environment == null) {
      baseResult.setType(ERROR);
      baseResult.setMessage("缺少环境信息");
      return baseResult;
    }

    if (Strings.isNullOrEmpty(environment.getIp())) {
      baseResult.setType(WARNING);
      baseResult.setMessage("请填写ip地址");
      return baseResult;
    }

    if (Strings.isNullOrEmpty(environment.getUserName())) {
      baseResult.setType(WARNING);
      baseResult.setMessage("请填写该服务器的登录用户名");
      return baseResult;
    }

    if (Strings.isNullOrEmpty(environment.getPassword())) {
      baseResult.setType(WARNING);
      baseResult.setMessage("请填写该服务器的登录密码");
      return baseResult;
    }

    //todo 判断ip是否正确，是否能登录成功

    try {
      int result = environmentService.updateEnvironment(environment);
      if (result == 1) {
        baseResult.setType(SUCCESS);
        baseResult.setMessage("编辑成功");
      } else {
        baseResult.setType(WARNING);
        baseResult.setMessage("保存失败");
      }
    } catch (Exception e) {
      log.error("editEnvironment error, environment={} ", environment, e);
      baseResult.setType(ERROR);
      baseResult.setMessage("编辑失败");
    }
    return baseResult;
  }

  @RequestMapping(value = "remove", method = RequestMethod.POST)
  public BaseResult removeEnvironment(@RequestBody String data){
    BaseResult baseResult = BaseResult.builder().build();
    try {
      int id = JSONObject.parseObject(data).getInteger("id");
      int result = environmentService.remove(id);
      if (result == 1) {
        baseResult.setType(SUCCESS);
        baseResult.setMessage("删除成功");
      } else {
        baseResult.setType(WARNING);
        baseResult.setMessage("删除失败");
      }
    } catch (Exception e) {
      log.error("removeEnvironment error, {}", data, e);
      baseResult.setType(ERROR);
      baseResult.setMessage("删除失败");
    }
    return baseResult;
  }
}
