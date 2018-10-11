package application.reponse;

import java.io.Serializable;

public interface Response extends Serializable {
	public enum Level {
		ALL,
		PRIVATE
	}
	
	int getCode();
	String getMessage();
	Level getLevel();
}
