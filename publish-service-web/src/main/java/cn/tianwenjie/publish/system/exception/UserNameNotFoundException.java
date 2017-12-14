package cn.tianwenjie.publish.system.exception;

/**
 * Created by tianwj on 2017/12/11.
 */
public class UserNameNotFoundException extends LogInException {

  public UserNameNotFoundException() {
    super();
  }

  public UserNameNotFoundException(String message) {
    super(message);
  }

  public UserNameNotFoundException(Throwable cause) {
    super(cause);
  }

  public UserNameNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public UserNameNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
