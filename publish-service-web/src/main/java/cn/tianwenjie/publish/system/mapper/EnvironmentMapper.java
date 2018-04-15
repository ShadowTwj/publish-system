package cn.tianwenjie.publish.system.mapper;

import cn.tianwenjie.publish.system.entity.Environment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author tianwj
 * @date 2018/1/23
 */
public interface EnvironmentMapper {

  /**
   * 获取所有的environment
   *
   * @return
   */
  @Select("SELECT * FROM environment")
  List<Environment> findAll();

  /**
   * 根据environmentId获取environment unique_name
   *
   * @param id
   * @return
   */
  @Select("SELECT unique_name FROM environment WHERE id = ${id}")
  String findNameById(@Param("id") Integer id);

  /**
   * insert environment
   *
   * @param environment
   * @return
   */
  @Insert("INSERT INTO environment (unique_name, ip, user_name, password, status, remark, create_time, create_user, update_time, update_user) \n" +
            "VALUES(#{environment.uniqueName}, #{environment.ip}, #{environment.userName}, #{environment.password}, ${environment.status}, " +
            "#{environment.remark}, #{environment.createTime}, #{environment.createUser}, #{environment.updateTime}, #{environment.updateUser})")
  int insertEnvironment(@Param("environment") Environment environment);

  /**
   * update environment
   *
   * @param environment
   * @return
   */
  @Update(
    "UPDATE environment SET unique_name=#{environment.uniqueName}, ip=#{environment.ip}, user_name=#{environment.userName}, `password`=#{environment.password}, \n" +
      "`status`=${environment.status}, remark=#{environment.remark}, create_time=#{environment.createTime}, create_user=#{environment.createUser}, update_time=#{environment.updateTime}, \n" +
      "update_user=#{environment.updateUser} WHERE id=${environment.id}")
  int updateEnvironment(@Param("environment") Environment environment);

  /**
   * delete environment
   *
   * @param id
   * @return
   */
  @Delete("DELETE FROM environment WHERE id=${id}")
  int removeEnvironment(@Param("id") int id);
}
