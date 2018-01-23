package cn.tianwenjie.publish.system.web;

import cn.tianwenjie.publish.system.bean.BaseResult;
import cn.tianwenjie.publish.system.entity.User;
import cn.tianwenjie.publish.system.service.UserService;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tianwj on 2017/12/21.
 */
@RestController
@Slf4j
@RequestMapping(value = "/user/")
public class UserController {
  @Resource
  private UserService userService;

  private static final String SUCCESS = "success";
  private static final String ERROR = "error";
  private static final String WARNING = "warning";


  @RequestMapping(value = "list", method = RequestMethod.GET)
  public List<User> getUserList() {
    try {
      return userService.getUserList();
    } catch (Exception e) {
      log.error("getUserList error", e);
      return Lists.newArrayList();
    }
  }

  @RequestMapping(value = "add", method = RequestMethod.POST)
  public BaseResult addUser(@RequestBody User user) {
    try {
      if (!user.getPassword().trim().equals(user.getCheckPass().trim())) {
        log.error("password checkPass is no equals, user={}", user);
        return BaseResult.builder().message("提交失败").type(ERROR).build();
      }
      int result = userService.addUser(user);
      if (result > 0) {
        return BaseResult.builder().message("提交成功").type(SUCCESS).build();
      } else {
        log.error("addUser error, user={}", user);
        return BaseResult.builder().message("提交失败").type(ERROR).build();
      }
    } catch (Exception e) {
      log.error("addUser error, user={}", user, e);
      return BaseResult.builder().message("提交失败").type(ERROR).build();
    }
  }

  @RequestMapping(value = "findAccount", method = RequestMethod.GET)
  public BaseResult findAccount(@RequestParam String account) {
    try {
      User user = userService.findAccount(account);
      if (user == null) {
        return BaseResult.builder().message("该账号可用").type(SUCCESS).build();
      } else {
        return BaseResult.builder().message("该账号已存在").type(WARNING).build();
      }
    } catch (Exception e) {
      log.error("findAccount error, account={}", account, e);
      return BaseResult.builder().message("查询出错").type(ERROR).build();
    }
  }

  @RequestMapping(value = "remove", method = RequestMethod.POST)
  public BaseResult remove(@RequestBody String data) {
    try {
      String id = JSONObject.parseObject(data).getString("id");
      int result = userService.remove(id);
      if (result > 0) {
        return BaseResult.builder().message("删除成功").type(SUCCESS).build();
      } else {
        log.error("deleteUser error, userId={}", id);
        return BaseResult.builder().message("删除失败").type(ERROR).build();
      }
    } catch (Exception e) {
      log.error("deleteUser error, {}", data, e);
      return BaseResult.builder().message("删除失败").type(ERROR).build();
    }
  }

  @RequestMapping(value = "edit", method = RequestMethod.POST)
  public BaseResult edit(@RequestBody User user) {
    try {
      int result = userService.edit(user);
      if (result > 0) {
        return BaseResult.builder().message("更新成功").type(SUCCESS).build();
      } else {
        log.error("editUser error, user={}", user);
        return BaseResult.builder().message("更新失败").type(ERROR).build();
      }
    } catch (Exception e) {
      log.error("editUser error, user={}", user, e);
      return BaseResult.builder().message("更新失败").type(ERROR).build();
    }
  }

  @RequestMapping(value = "batchRemove", method = RequestMethod.POST)
  public BaseResult batchRemove(@RequestBody String data) {
    try {
      String ids = JSONObject.parseObject(data).getString("ids");
      List<String> idList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(ids);
      int result = userService.batchRemove(idList);
      if (result > 0) {
        return BaseResult.builder().message("批量删除成功").type(SUCCESS).build();
      } else {
        log.error("batchDeleteUser error, userIds={}", ids);
        return BaseResult.builder().message("批量删除失败").type(ERROR).build();
      }
    } catch (Exception e) {
      log.error("batchDeleteUser error, data={}", data, e);
      return BaseResult.builder().message("批量删除失败").type(ERROR).build();
    }
  }
}
