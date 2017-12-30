package cn.tianwenjie.publish.system.service;

import cn.tianwenjie.publish.system.entity.User;
import cn.tianwenjie.publish.system.exception.*;
import cn.tianwenjie.publish.system.mapper.UserMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by tianwj on 2017/12/4.
 */
@Service
@Slf4j
public class UserService {
  @Resource
  private UserMapper userMapper;

  public User login(@NonNull String account, @NonNull String password) throws LogInException {
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

  public List<User> getUserList() {
    return userMapper.findAll();
  }

  public int addUser(@NonNull User user) {
    if (user.getNickname() == null || user.getNickname().isEmpty()) {
      user.setNickname(user.getAccount());
    }
    user.setCreateTime(new Date());
    user.setUpdateTime(new Date());
    return userMapper.insertUser(user);
  }

  public User findAccount(@NonNull String account) {
    return userMapper.findByAccount(account);
  }

  public int remove(@NonNull String id) {
    return userMapper.deleteUser(Integer.parseInt(id));
  }

  public int edit(@NonNull User user) {
    return userMapper.updateUser(user);
  }

  public int batchRemove(@Nonnull List<String> idList){
    return userMapper.batchDeleteUser(idList);
  }
}
