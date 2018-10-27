package application.request;


import application.dao.EventDao;
import application.model.Client;
import application.model.Event;
import application.reponse.BookResponse;
import application.reponse.KnownResponse;
import application.reponse.Response;

public class BookEventRequest extends RemoveEventRequest {
	private static final long serialVersionUID = 1372078400449284640L;

	public BookEventRequest(Integer eventID) {
		super(eventID);
	}
	
	@Override
	public Response execute(Client executor, EventDao eventDao) {
		try {
			Event event = eventDao.findById(eventID);
			if(event.getOwner() != executor) {
				eventDao.book(event);
				event.setBooker(executor);
				return new BookResponse(3, "Existing service "+ event  +" was booked", Response.Level.ALL);
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
