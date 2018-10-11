package application.request;


import application.dao.EventDao;
import application.reponse.Response;
import application.model.Client;

import java.io.Serializable;

public interface Request extends Serializable {
	Response execute(Client executor, EventDao eventDao);
}