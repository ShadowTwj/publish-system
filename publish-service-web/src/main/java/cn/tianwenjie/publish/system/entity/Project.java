package cn.tianwenjie.publish.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by tianwj on 2018/1/3.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
  private int id;
  private String uniqueName;
  private String git;
  private String module;
  private String manager;
  private String remark;
  private int status;
  private Date createTime;
  private String createUser;
  private Date updateTime;
  private String updateUser;
  private int timeRestrict;
}
