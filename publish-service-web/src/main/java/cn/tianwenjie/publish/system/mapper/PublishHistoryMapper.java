package cn.tianwenjie.publish.system.mapper;

import cn.tianwenjie.publish.system.entity.PublishHistory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author tianwj
 * @date 2018/2/28
 */
public interface PublishHistoryMapper {

  /**
   * insert publish
   *
   * @param publishHistory
   * @return
   */
  @Insert("INSERT INTO publish_history " +
            "(publish_conf_id, project_id, project_name, environment_id, environment_name, branch, remark, status, cost_time, create_user, create_time, update_user, update_time) " +
            "VALUES " +
            "(${publishHistory.publishConfId}, ${publishHistory.projectId}, #{publishHistory.projectName}, ${publishHistory.environmentId}, #{publishHistory.environmentName}, #{publishHistory.branch}, " +
            "#{publishHistory.remark}, ${publishHistory.status}, ${publishHistory.costTime}, #{publishHistory.createUser}, #{publishHistory.createTime}, #{publishHistory.updateUser}, #{publishHistory.updateTime})")
  @SelectKey(statement = "SELECT max(id) id FROM publish_history", keyColumn = "id", keyProperty = "publishHistory.id", before = false, resultType = Integer.class)
  Integer insert(@Param("publishHistory") PublishHistory publishHistory);

  /**
   * 根据时间倒排序并取前1000条记录
   *
   * @return
   */
  @Select("SELECT * FROM publish_history ORDER BY create_time DESC LIMIT 1000")
  List<PublishHistory> getPublishHistoryList();

  /**
   * 根据ID获取发布历史
   *
   * @param id
   * @return
   */
  @Select("SELECT * FROM publish_history WHERE id = #{id}")
  PublishHistory findById(@Param("id") int id);

  /**
   * 根据id更新状态
   *
   * @param id
   * @param status
   * @return
   */
  @Update("UPDATE publish_history SET status = ${status} WHERE id = ${id}")
  Integer updateById(@Param("id") int id, @Param("status") int status);
}
