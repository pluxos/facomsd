package client.business.request.strategy;

import client.commons.domain.Method;
import client.commons.exceptions.ErrorMap;
import client.commons.exceptions.InvalidCommandException;
import client.commons.utils.DataCodificator;
import client.connector.GenericRequest;

public class GetUser implements RequestStrategy {

	@Override
	public GenericRequest buildRequest(String[] inputParams) throws InvalidCommandException {
		GenericRequest request = new GenericRequest();
		try {
			request.setCode(DataCodificator.stringToBigInteger(inputParams[1]));
			request.setMethod(Method.GET);
		} catch (NullPointerException e) {
			throw new InvalidCommandException(ErrorMap.INVALID_COMMAND);
		}
		return request;
	}
}