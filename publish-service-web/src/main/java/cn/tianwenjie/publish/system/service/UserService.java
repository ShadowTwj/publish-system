package cn.tianwenjie.publish.system.service;

import cn.tianwenjie.publish.system.mapper.UserMapper;
import lombok.NonNull;
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

  public boolean login(@NonNull String account, @NonNull String password){
    return false;
  }
}
