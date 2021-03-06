package cn.tianwenjie.publish.system.web;

import cn.tianwenjie.publish.system.bean.LogInResult;
import cn.tianwenjie.publish.system.entity.User;
import cn.tianwenjie.publish.system.exception.LogInException;
import cn.tianwenjie.publish.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by tianwj on 2017/12/4.
 */
@Controller
@Slf4j
public class HomeController {
  @Resource
  private UserService userService;

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseBody
  public LogInResult login(@RequestBody User user) throws LogInException {
    return LogInResult.builder().msg("登录成功").code(HttpStatus.OK.value()).user(userService.login(user.getAccount(), user.getPassword())).build();
  }

  @ExceptionHandler(LogInException.class)
  @ResponseBody
  public LogInResult handleUserNameNotFound(LogInException e) {
    return LogInResult.builder().msg("账号或密码错误").code(HttpStatus.BAD_REQUEST.value()).user(null).build();
  }

  @RequestMapping(value = {"/"})
  public String home() {
    return "index";
  }

}
