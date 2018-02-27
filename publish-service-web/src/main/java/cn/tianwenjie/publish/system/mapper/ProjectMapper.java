package cn.tianwenjie.publish.system.mapper;

import cn.tianwenjie.publish.system.entity.Project;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by tianwj on 2018/1/3.
 */
public interface ProjectMapper {
  @Insert("INSERT INTO project (unique_name, git, module, manager, remark, `status`, create_time, create_user, update_time, update_user, time_restrict) " +
            "VALUES (#{project.uniqueName}, #{project.git}, #{project.module}, #{project.manager}, #{project.remark}, ${project.status}, #{project.createTime}, " +
            "#{project.createUser}, #{project.updateTime}, #{project.updateUser}, ${project.timeRestrict})")
  int insertProject(@Param("project") Project project);

  @Select("SELECT * FROM project")
  List<Project> findAll();

  @Update("UPDATE project SET unique_name=#{project.uniqueName}, git=#{project.git}, module=#{project.module}, manager=#{project.manager}, " +
            "remark=#{project.remark}, `status`=${project.status}, create_time=#{project.createTime}, create_user=#{project.createUser}, " +
            "update_time=#{project.updateTime}, update_user=#{project.updateUser}, time_restrict=${project.timeRestrict} WHERE id=${project.id}")
  int updateProjecct(@Param("project") Project project);

  @Delete("DELETE FROM project WHERE id=${id}")
  int deleteProject(@Param("id") int id);

  @Select("SELECT git FROM project WHERE id=${id}")
  String getGitById(@Param("id") Integer id);
}
