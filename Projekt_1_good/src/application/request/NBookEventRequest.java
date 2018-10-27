package application.request;

import application.dao.EventDao;
import application.model.Client;
import application.model.Event;
import application.reponse.KnownResponse;
import application.reponse.NBookResponse;
import application.reponse.Response;

public class NBookEventRequest extends RemoveEventRequest {
	private static final long serialVersionUID = -493859156253749249L;

	public NBookEventRequest(Integer eventID) {
		super(eventID);
	}
	
	@Override
	public Response execute(Client executor, EventDao eventDao) {
		try {
			Event event = eventDao.findById(eventID);
			if(event.getBooker() == executor) {
				eventDao.nbook(event);
				return new NBookResponse(4, "Booked service "+ event +" is now free.", Response.Level.PRIVATE);
			} else {
				return KnownResponse.PERMISSION_DENIED;
			}
		} catch(IndexOutOfBoundsException e) {
			return KnownResponse.INVALID_ID;
		} catch(UnsupportedOperationException e) {
			return KnownResponse.BOOK_ERROR;
		}
		
	}
}
