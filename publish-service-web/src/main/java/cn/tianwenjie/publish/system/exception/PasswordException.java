package cn.tianwenjie.publish.system.exception;

/**
 * Created by tianwj on 2017/12/11.
 */
public class PasswordException extends LogInException {

  public PasswordException() {
    super();
  }

  public PasswordException(String message) {
    super(message);
  }

  public PasswordException(Throwable cause) {
    super(cause);
  }

  public PasswordException(String message, Throwable cause) {
    super(message, cause);
  }

  public PasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
