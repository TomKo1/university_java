package application.request;

import application.dao.EventDao;
import application.model.Client;
import application.reponse.Response;
import application.reponse.SuccessResponse;
import application.reponse.UnknownResponse;

public class ClientNameRequest implements Request {
  private static final long serialVersionUID = -493859309253749249L;
  private String name;


  public ClientNameRequest(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  @Override
  public Response execute(Client executor, EventDao eventDao) {
    if(eventDao != null ) {
      return new UnknownResponse(400, "Wrong format of request", Response.Level.PRIVATE);
    } else {
      executor.setName(name);
      return new SuccessResponse(200, "Name successfully set!", Response.Level.PRIVATE);
    }
  }
}
