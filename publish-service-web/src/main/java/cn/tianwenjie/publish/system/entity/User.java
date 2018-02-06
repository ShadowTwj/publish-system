package cn.tianwenjie.publish.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by tianwj on 2017/12/1.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  private Integer id;
  private String account;
  private String password;
  @Transient
  private String checkPass;
  private String nickname;
  private String token;
  private String creater;
  private Date createTime;
  private Date updateTime;
}
