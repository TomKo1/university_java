package application.reponse;

// DELETE_EVENT(2, "Service was cancelled.", Level.ALL),
public class DeleteResponse implements Response {
    private static final long serialVersionUID = -432186459592224119L;

    private final int code;
    private final String message;
    private final Level level;

    public DeleteResponse(int code, String message, Level level) {
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
