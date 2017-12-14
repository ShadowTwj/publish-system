package cn.tianwenjie.publish.system.exception;

/**
 * Created by tianwj on 2017/12/11.
 */
public class LogInException extends Exception {

  public LogInException() {
    super();
  }

  public LogInException(String message) {
    super(message);
  }

  public LogInException(Throwable cause) {
    super(cause);
  }

  public LogInException(String message, Throwable cause) {
    super(message, cause);
  }

  public LogInException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
