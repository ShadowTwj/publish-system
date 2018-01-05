package cn.tianwenjie.publish.system.web;

import cn.tianwenjie.publish.system.bean.BaseResult;
import cn.tianwenjie.publish.system.entity.Project;
import cn.tianwenjie.publish.system.service.ProjectService;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.SQLNonTransientException;
import java.util.List;

/**
 * Created by tianwj on 2018/1/3.
 */
@RestController
@Slf4j
@RequestMapping(value = "/project/")
public class ProjectController {
  @Resource
  private ProjectService projectService;

  public static final String SUCCESS = "success";
  public static final String WARNING = "warning";
  public static final String ERROR = "error";

  @RequestMapping(value = "list", method = RequestMethod.GET)
  public List<Project> getProjectList() {
    try {
      return projectService.getProjectList();
    } catch (Exception e) {
      log.error("getProjectList error", e);
      return Lists.newArrayList();
    }
  }

  @RequestMapping(value = "add", method = RequestMethod.POST)
  public BaseResult addProject(@RequestBody Project project) {
    BaseResult baseResult = BaseResult.builder().build();
    if (project == null) {
      baseResult.setType(ERROR);
      baseResult.setMessage("缺少项目信息");
      return baseResult;
    }
    if (Strings.isNullOrEmpty(project.getUniqueName())) {
      baseResult.setType(WARNING);
      baseResult.setMessage("请填写唯一标识");
      return baseResult;
    }
    if (Strings.isNullOrEmpty(project.getGit())) {
      baseResult.setType(WARNING);
      baseResult.setMessage("请填写Git地址");
      return baseResult;
    }
    if (Strings.isNullOrEmpty(project.getManager())) {
      baseResult.setType(WARNING);
      baseResult.setMessage("请填写负责人");
      return baseResult;
    }
    //todo:判断Git地址是否正确
    try {
      int result = projectService.addProject(project);
      if (result == 1) {
        baseResult.setType(SUCCESS);
        baseResult.setMessage("新增成功");
      } else {
        baseResult.setType(WARNING);
        baseResult.setMessage("保存失败");
      }
    } catch (Exception e) {
      log.error("addProject, project{}", project, e);
      if (e.getCause() instanceof SQLNonTransientException) {//使用数据库唯一索引
        baseResult.setType(WARNING);
        baseResult.setMessage("唯一标识已存在");
      } else {
        baseResult.setType(ERROR);
        baseResult.setMessage("新增失败");
      }
    }
    return baseResult;
  }

  @RequestMapping(value = "edit", method = RequestMethod.POST)
  public BaseResult editProject(@RequestBody Project project) {
    BaseResult baseResult = BaseResult.builder().build();
    if (project == null) {
      baseResult.setType(ERROR);
      baseResult.setMessage("缺少项目信息");
      return baseResult;
    }
    if (Strings.isNullOrEmpty(project.getUniqueName())) {
      baseResult.setType(WARNING);
      baseResult.setMessage("请填写唯一标识");
      return baseResult;
    }
    if (Strings.isNullOrEmpty(project.getGit())) {
      baseResult.setType(WARNING);
      baseResult.setMessage("请填写Git地址");
      return baseResult;
    }
    if (Strings.isNullOrEmpty(project.getManager())) {
      baseResult.setType(WARNING);
      baseResult.setMessage("请填写负责人");
      return baseResult;
    }
    //todo:判断Git地址是否正确
    try {
      int result = projectService.editProject(project);
      if (result == 1) {
        baseResult.setType(SUCCESS);
        baseResult.setMessage("编辑成功");
      } else {
        baseResult.setType(WARNING);
        baseResult.setMessage("保存失败");
      }
    } catch (Exception e) {
      log.error("editProject, project{}", project, e);
      if (e.getCause() instanceof SQLNonTransientException) {//使用数据库唯一索引
        baseResult.setType(WARNING);
        baseResult.setMessage("唯一标识已存在");
      } else {
        baseResult.setType(ERROR);
        baseResult.setMessage("编辑失败");
      }
    }
    return baseResult;
  }

  @RequestMapping(value = "remove", method = RequestMethod.POST)
  public BaseResult removeProject(@RequestBody String data) {
    BaseResult baseResult = BaseResult.builder().build();
    try {
      int id = JSONObject.parseObject(data).getInteger("id");
      int result = projectService.remove(id);
      if (result == 1) {
        baseResult.setType(SUCCESS);
        baseResult.setMessage("删除成功");
      } else {
        baseResult.setType(WARNING);
        baseResult.setMessage("删除失败");
      }
    } catch (Exception e) {
      log.error("removeProject error, {}", data, e);
      baseResult.setType(ERROR);
      baseResult.setMessage("删除失败");
    }
    return baseResult;
  }


}
