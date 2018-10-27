package application.request;

import application.dao.EventDao;
import application.model.Client;
import application.model.Event;
import application.reponse.DeleteResponse;
import application.reponse.KnownResponse;
import application.reponse.Response;

public class RemoveEventRequest implements Request {
	private static final long serialVersionUID = -904389556583009732L;
	
	protected Integer eventID;
	
	public RemoveEventRequest(Integer eventID) {
		this.eventID = eventID;
	}

	@Override
	public Response execute(Client executor, EventDao eventDao) {
		try {
			Event event = eventDao.findById(eventID);
			if(event.getOwner() == executor) {
				eventDao.delete(event);
				return new DeleteResponse(2, "Service "+ event +" was cancelled.", Response.Level.ALL);
			} else {
				return KnownResponse.PERMISSION_DENIED;
			}
		} catch(IndexOutOfBoundsException e) {
			return KnownResponse.INVALID_ID;
		}
		
	}
}
