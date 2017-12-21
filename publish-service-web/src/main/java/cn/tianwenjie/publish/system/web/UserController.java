package cn.tianwenjie.publish.system.web;

import cn.tianwenjie.publish.system.bean.BackResult;
import cn.tianwenjie.publish.system.entity.User;
import cn.tianwenjie.publish.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tianwj on 2017/12/21.
 */
@RestController
@Slf4j
@RequestMapping(value = "/user")
public class UserController {
  @Resource
  private UserService userService;


  @RequestMapping(value = "list", method = RequestMethod.GET)
  public List<User> getUserList() {
    try {
      return userService.getUserList();
    } catch (Exception e) {
      log.error("getUserList error", e);
      return null;
    }
  }

  @RequestMapping(value = "add", method = RequestMethod.POST)
  public BackResult addUser(@RequestBody User user) {
    try {
      int result = userService.addUser(user);
      if (result > 0) {
        return BackResult.builder().message("提交成功").type("success").code(HttpStatus.OK.value()).build();
      } else {
        return BackResult.builder().message("提交失败").type("error").code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
      }
    } catch (Exception e) {
      log.error("addUser error, user={}", user, e);
      return BackResult.builder().message("提交失败").type("error").code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
    }
  }
}
