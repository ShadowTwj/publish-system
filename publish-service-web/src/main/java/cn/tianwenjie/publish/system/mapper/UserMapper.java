package cn.tianwenjie.publish.system.mapper;

import cn.tianwenjie.publish.system.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by tianwj on 2017/12/1.
 */
public interface UserMapper {
  @Insert("INSERT INTO user (account, password, nickname, token, creater, create_time, update_time) VALUES (#{user.account}, #{user.password}, #{user.nickname}, #{user.token}, #{user.creater}, #{user.createTime}, #{user.updateTime})")
  int insertUser(@Param("user") User user);

  @Select("SELECT * FROM user WHERE account = #{account}")
  User findByAccount(@Param("account") String account);

  @Select("SELECT * FROM user")
  List<User> findAll();
}