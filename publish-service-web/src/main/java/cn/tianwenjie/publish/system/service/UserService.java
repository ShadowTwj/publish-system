package cn.tianwenjie.publish.system.service;

import cn.tianwenjie.publish.system.entity.User;
import cn.tianwenjie.publish.system.exception.LogInException;
import cn.tianwenjie.publish.system.exception.PasswordException;
import cn.tianwenjie.publish.system.exception.PasswordNotFoundException;
import cn.tianwenjie.publish.system.exception.UserNameException;
import cn.tianwenjie.publish.system.exception.UserNameNotFoundException;
import cn.tianwenjie.publish.system.mapper.UserMapper;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by tianwj on 2017/12/4.
 */
@Service
@Slf4j
public class UserService {
  @Resource
  private UserMapper userMapper;

  public User login(String account, String password) throws LogInException {
    if (account == null || account.trim().isEmpty()) {
      throw new UserNameNotFoundException("用户名为空");
    }

    if (password == null || password.trim().isEmpty()) {
      throw new PasswordNotFoundException("密码为空");
    }

    User user = userMapper.findByAccount(account.trim());

    if (user == null) {
      throw new UserNameException("用户名错误");
    }

    if (password.equals(user.getPassword())) {
      return user;
    }

    throw new PasswordException("密码错误");
  }
}
