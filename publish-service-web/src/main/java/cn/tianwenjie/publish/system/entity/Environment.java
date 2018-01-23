package cn.tianwenjie.publish.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author tianwj
 * @date 2018/1/5
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Environment {
  private int id;
  private String uniqueName;
  private String ip;
  private String userName;
  private String password;
  private int status;
  private String remark;
  private Date createTime;
  private String createUser;
  private Date updateTime;
  private String updateUser;
}
