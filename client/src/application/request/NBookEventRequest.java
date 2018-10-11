package application.request;

import application.dao.EventDao;
import application.reponse.Response;
import application.model.Client;
import application.model.Event;
import application.reponse.KnownResponse;

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
				return KnownResponse.NBOOK_EVENT;
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
