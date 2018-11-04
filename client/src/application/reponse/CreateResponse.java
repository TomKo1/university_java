package application.reponse;

//CREATE_EVENT(1, "New service created!", Level.ALL)
public class CreateResponse implements Response {
        private static final long serialVersionUID = -543086459592224119L;

    private final int code;
    private final String message;
    private final Level level;

    public CreateResponse(int code, String message, Level level) {
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
