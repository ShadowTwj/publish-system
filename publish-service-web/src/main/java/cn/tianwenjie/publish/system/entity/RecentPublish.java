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
public class RecentPublish {
  private Integer id;
  private Integer projectId;
  private String uniqueName;
  private Integer userId;
  private String account;
  private Date updateTime;
}
