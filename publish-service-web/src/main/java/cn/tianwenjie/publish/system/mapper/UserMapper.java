package cn.tianwenjie.publish.system.mapper;

import cn.tianwenjie.publish.system.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * Created by tianwj on 2017/12/1.
 */
public interface UserMapper {
  @Insert("INSERT INTO user (account, password, nickname, github_id, creater, create_time, update_time) VALUES (#{user.account}, #{user.password}, #{user.nickname}, ${user.githubId}, #{user.creater}, #{user.createTime}, #{user.updateTime})")
  int insertUser(@Param("user") User user);
}
