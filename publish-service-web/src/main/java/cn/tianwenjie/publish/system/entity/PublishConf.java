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
public class PublishConf {
  private Integer id;
  private Integer projectId;
  private Integer environmentId;
  private String environmentUniqueName;
  private String tomcatContextPath;
  /**
   * 实体个数
   */
  private Integer replicas;
  private String ports;
  private String remark;
  private String createUser;
  private Date createTime;
  private String updateUser;
  private Date updateTime;
}
