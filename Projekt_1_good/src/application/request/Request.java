package application.request;

import application.dao.EventDao;
import application.model.Client;
import application.reponse.Response;

import java.io.Serializable;

public interface Request extends Serializable {
	Response execute(Client executor, EventDao eventDao);
}