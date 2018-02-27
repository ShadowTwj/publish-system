package cn.tianwenjie.publish.system.mapper;

import cn.tianwenjie.publish.system.entity.PublishConf;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author tianwj
 * @date 2018/2/23
 */
public interface PublishConfMapper {
  /**
   * 根据projectId来获取config
   *
   * @param projectId
   * @return
   */
  @Select("SELECT * FROM publish_conf WHERE project_id=${projectId}")
  List<PublishConf> findByProjectId(@Param("projectId") Integer projectId);

  /**
   * insert publishConfig
   *
   * @param publishConf
   * @return
   */
  @Insert("INSERT INTO publish_conf\n" +
            "    (project_id, environment_id, environment_unique_name, tomcat_context_path, replicas, ports, remark, create_user, create_time, update_user, update_time)\n" +
            "VALUES\n" +
            "    (${publishConfig.projectId}, ${publishConfig.environmentId}, #{publishConfig.environmentUniqueName}, #{publishConfig.tomcatContextPath}, " +
            "${publishConfig.replicas}, #{publishConfig.ports}, #{publishConfig.remark}, #{publishConfig.createUser}, #{publishConfig.createTime}, #{publishConfig.updateUser}, #{publishConfig.updateTime})")
  Integer insert(@Param("publishConfig") PublishConf publishConf);

  /**
   * update publishConfig
   *
   * @param publishConf
   * @return
   */
  @Update(
    "UPDATE publish_conf SET project_id = ${publishConfig.projectId}, environment_id = ${publishConfig.environmentId}, environment_unique_name = #{publishConfig.environmentUniqueName}, \n" +
      "tomcat_context_path = #{publishConfig.tomcatContextPath}, replicas = ${publishConfig.replicas}, ports = #{publishConfig.ports}, remark = #{publishConfig.remark}, \n" +
      "create_user = #{publishConfig.createUser}, create_time = #{publishConfig.createTime}, update_user = #{publishConfig.updateUser}, update_time = #{publishConfig.updateTime} WHERE id = ${publishConfig.id}")
  Integer update(@Param("publishConfig") PublishConf publishConf);

  @Delete("DELETE FROM publish_conf WHERE id = ${id}")
  Integer delete(@Param("id") Integer id);
}
