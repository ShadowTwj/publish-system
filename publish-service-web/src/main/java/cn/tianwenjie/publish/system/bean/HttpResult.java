package cn.tianwenjie.publish.system.bean;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Http返回结果封装
 *
 * @author tianwj
 * @date 2018/2/6
 */
@Data
@Accessors(chain = true)
public class HttpResult {
  private static final String SUCCESS = "success";
  private static final String WARNING = "warning";
  private static final String ERROR = "error";

  private String message = "";
  private String type = WARNING;
  private Object data;

  public HttpResult success() {
    return this.setType(SUCCESS);
  }

  public HttpResult warning() {
    return this.setType(WARNING);
  }

  public HttpResult error() {
    return this.setType(ERROR);
  }
}
