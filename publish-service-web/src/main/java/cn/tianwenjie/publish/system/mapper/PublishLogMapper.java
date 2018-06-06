package cn.tianwenjie.publish.system.mapper;

import cn.tianwenjie.publish.system.entity.PublishLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * @author tianwj
 * @date 2018/6/2
 */
public interface PublishLogMapper {

  /**
   * insert
   *
   * @param publishLog
   * @return
   */
  @Insert(
    "INSERT INTO publish_log (project_id, publish_conf_id, publish_history_id, step_name, step_order, step_log, remark, status, create_user, create_time, update_user, update_time) \n" +
      "VALUES(${publishLog.projectId}, ${publishLog.publishConfId}, ${publishLog.publishHistoryId}, #{publishLog.stepName}, ${publishLog.stepOrder}, #{publishLog.stepLog}, #{publishLog.remark}, \n" +
      "${publishLog.status}, #{publishLog.createUser}, #{publishLog.createTime}, #{publishLog.updateUser}, #{publishLog.updateTime})")
  Integer insert(@Param("publishLog") PublishLog publishLog);
}
