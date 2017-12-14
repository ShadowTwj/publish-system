package cn.tianwenjie.publish.system.exception;

/**
 * Created by tianwj on 2017/12/11.
 */
public class PasswordNotFoundException extends LogInException {

  public PasswordNotFoundException() {
    super();
  }

  public PasswordNotFoundException(String message) {
    super(message);
  }

  public PasswordNotFoundException(Throwable cause) {
    super(cause);
  }

  public PasswordNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public PasswordNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
