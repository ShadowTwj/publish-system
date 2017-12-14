package cn.tianwenjie.publish.system.exception;

/**
 * Created by tianwj on 2017/12/11.
 */
public class UserNameException extends LogInException {

  public UserNameException() {
    super();
  }

  public UserNameException(String message) {
    super(message);
  }

  public UserNameException(Throwable cause) {
    super(cause);
  }

  public UserNameException(String message, Throwable cause) {
    super(message, cause);
  }

  public UserNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
