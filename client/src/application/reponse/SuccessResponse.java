package application.reponse;

public class SuccessResponse implements Response {
  private static final long serialVersionUID = -892346459595674119L;

  private final int code;
  private final String message;
  private final Level level;

  public SuccessResponse(int code, String message, Level level) {
    this.code = code;
    this.message = message;
    this.level = level;
  }

  @Override
  public int getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public Level getLevel() {
    return level;
  }

  @Override
  public String toString() {
    return "(" + level + ") " + message;
  }


}
