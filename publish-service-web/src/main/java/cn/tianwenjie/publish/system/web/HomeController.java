package cn.tianwenjie.publish.system.web;

import cn.tianwenjie.publish.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
  public String login(String account, String password) {
    userService.login(account, password);
    return null;
  }
}