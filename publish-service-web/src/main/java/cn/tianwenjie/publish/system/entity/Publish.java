package cn.tianwenjie.publish.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author tianwj
 * @date 2018/2/5
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publish {
  private Integer id;
  private Integer publishConfId;
  private Integer projectId;
  private String projectName;
  private Integer environmentId;
  private String environmentName;
  private String branch;
  private String remark;
  private Integer status;
  /**
   * 耗时
   */
  private Long costTime;
  private String createUser;
  private Date createTime;
  private String updateUser;
  private Date updateTime;
}
