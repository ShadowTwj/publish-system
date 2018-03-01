package cn.tianwenjie.publish.system.mapper;

import cn.tianwenjie.publish.system.entity.Publish;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * @author tianwj
 * @date 2018/2/28
 */
public interface PublishMapper {

  /**
   * insert publish
   *
   * @param publish
   * @return
   */
  @Insert("INSERT INTO publish " +
            "(publish_conf_id, project_id, project_name, environment_id, environment_name, branch, remark, status, cost_time, create_user, create_time, update_user, update_time) " +
            "VALUES " +
            "(${publish.publishConfId}, ${publish.projectId}, #{publish.projectName}, ${publish.environmentId}, #{publish.environmentName}, #{publish.branch}, " +
            "#{publish.remark}, ${publish.status}, ${publish.costTime}, #{publish.createUser}, #{publish.createTime}, #{publish.updateUser}, #{publish.updateTime})")
  Integer insert(@Param("publish") Publish publish);
}
