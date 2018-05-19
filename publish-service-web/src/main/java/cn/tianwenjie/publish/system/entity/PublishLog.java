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
public class PublishLog {
  private Integer id;
  private Integer projectId;
  private Integer publishConfId;
  private Integer publishHistoryId;
  private String stepName;
  /**
   * 步骤顺序
   */
  private Integer stepOrder;
  private String stepLog;
  private String remark;
  private Integer status;
  private String createUser;
  private Date createTime;
  private String updateUser;
  private Date updateTime;
}
