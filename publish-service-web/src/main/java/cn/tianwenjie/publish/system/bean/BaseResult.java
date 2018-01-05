package cn.tianwenjie.publish.system.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by tianwj on 2017/12/21.
 * 返回前端实体
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResult {
  private String message;
  private String type;
}
