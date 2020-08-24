package br.com.devmedia.webservice.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.devmedia.webservice.model.domain.ErrorMessage;

@Provider
public class DAOExceptionMapper implements ExceptionMapper<DAOException> {

	@Override
	public Response toResponse(DAOException exception) {
		ErrorMessage error = new ErrorMessage(exception.getMessage(), exception.getCode());
		if (exception.getCode() == ErrorCode.BAD_REQUEST.getCode()) {
			return Response.status(Status.BAD_REQUEST)				
					.entity(error)
					.type(MediaType.APPLICATION_JSON)
					.build();
		} else if (exception.getCode() == ErrorCode.NOT_FOUND.getCode()) {
			return Response.status(Status.NOT_FOUND)				
					.entity(error)
					.type(MediaType.APPLICATION_JSON)
					.build();
		} else {
			return Response.status(Status.INTERNAL_SERVER_ERROR)				
					.entity(error)
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
	}
}