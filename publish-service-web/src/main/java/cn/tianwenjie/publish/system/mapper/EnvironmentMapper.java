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
   * insert environment
   *
   * @param environment
   * @return
   */
  @Insert("INSERT INTO environment (uniqueName, ip, user_name, `password`, `status`, remark, create_time, create_user, update_user) \n" +
            "VALUES(#{environment.uniqueName}, #{environment.ip}, #{environment.user_name}, #{environment.password}, ${environment.status}, " +
            "#{environment.remark}, #{environment.create_time}, #{environment.create_user}, #{environment.update_user})")
  int insertEnvironment(@Param("environment") Environment environment);

  /**
   * update environment
   *
   * @param environment
   * @return
   */
  @Update("UPDATE environment SET tag=#{environment.tag}, ip=#{environment.ip}, user_name=#{environment.user_name}, `password`=#{environment.password}, \n" +
            "`status`=${environment.status}, remark=#{environment.remark}, create_time=#{environment.create_time}, create_user=#{environment.create_user}, \n" +
            "update_user=#{environment.update_user} WHERE id=${environment.id}")
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
