package cn.tianwenjie.publish.system.mapper;

import cn.tianwenjie.publish.system.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author tianwj
 * @date 2017/12/1
 */
public interface UserMapper {
  /**
   * insert user
   *
   * @param user
   * @return
   */
  @Insert("INSERT INTO `user` (account, `password`, nickname, token, creater, create_time, update_time) VALUES (#{user.account}, #{user.password}, #{user.nickname}, #{user.token}, #{user.creater}, #{user.createTime}, #{user.updateTime})")
  int insertUser(@Param("user") User user);

  /**
   * 根据account获取user
   *
   * @param account
   * @return
   */
  @Select("SELECT * FROM `user` WHERE account = #{account}")
  User findByAccount(@Param("account") String account);

  /**
   * findAll
   *
   * @return
   */
  @Select("SELECT * FROM `user`")
  List<User> findAll();

  /**
   * delete user
   *
   * @param id
   * @return
   */
  @Delete("DELETE FROM `user` WHERE id = ${id}")
  int deleteUser(@Param("id") int id);

  /**
   * update user
   *
   * @param user
   * @return
   */
  @Update("UPDATE `user` SET account=#{user.account}, `password`=#{user.password}, nickname=#{user.nickname}, token=#{user.token}, creater=#{user.creater}, create_time=#{user.createTime}, update_time=#{user.updateTime} WHERE id=${user.id}")
  int updateUser(@Param("user") User user);

  /**
   * update token by account
   *
   * @param account
   * @param token
   * @return
   */
  @Update("UPDATE `user` SET token=#{token} WHERE account=#{account}")
  int updateToken(@Param("account") String account, @Param("token") String token);

  /**
   * 批量删除
   *
   * @param idList
   * @return
   */
  int batchDeleteUser(@Param("idList") List<String> idList);

  /**
   * 根据account获取token
   *
   * @param account
   * @return
   */
  @Select("SELECT token FROM user WHERE account=#{account}")
  String getTokenByAccount(@Param("account") String account);
}