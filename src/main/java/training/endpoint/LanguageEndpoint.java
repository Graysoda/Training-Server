package training.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import training.generated.*;
import training.service.LanguageServiceImpl;

@Endpoint
public class LanguageEndpoint {
	private static final String NAMESPACE_URI = SoapConstants.NAMESPACE_URI;
	@Autowired private LanguageServiceImpl languageService;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllLanguagesRequest")
	@ResponsePayload
	public GetAllLanguagesResponse getAllLanguages(@RequestPayload GetAllLanguagesRequest request){
		GetAllLanguagesResponse response = new GetAllLanguagesResponse();
		response.setLanguage(languageService.getAllLanguages());
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getLanguageByIdRequest")
	@ResponsePayload
	public GetLanguageByIdResponse getLanguageById(@RequestPayload GetLanguageByIdRequest request){
		GetLanguageByIdResponse response = new GetLanguageByIdResponse();
		response.setLanguage(languageService.getLanguageById(request.getLanguageId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getLanguageByNameRequest")
	@ResponsePayload
	public GetLanguageByNameResponse getLanguageByName(@RequestPayload GetLanguageByNameRequest request){
		GetLanguageByNameResponse response = new GetLanguageByNameResponse();
		response.setLanguage(languageService.getLanguageByName(request.getLanguageName()));
		return response;
	}
}
