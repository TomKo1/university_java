package application.reponse;


// BOOK_EVENT(3, "Existing service was booked", Level.ALL),
public class BookResponse implements Response {
    private static final long serialVersionUID = -123486459592224119L;

    private final int code;
    private final String message;
    private final Level level;

    public BookResponse(int code, String message, Level level) {
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
