package cn.tianwenjie.publish.system.bean;

import cn.tianwenjie.publish.system.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Created by tianwj on 2017/12/11.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogInResult {
  private String msg;
  private int code;
  private User user;
}
