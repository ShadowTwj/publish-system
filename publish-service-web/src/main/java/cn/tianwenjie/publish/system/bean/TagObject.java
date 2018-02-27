package cn.tianwenjie.publish.system.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tianwj
 * @date 2018/2/8
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagObject {
  private String tag;
  private String message;
  private String object;
  private String type;
}
