package training.api.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import training.Constants;
import training.generated.*;
import training.service.LanguageService;

@Endpoint
public class LanguageEndpoint {
    @Autowired
    private LanguageService languageService;

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getAllLanguagesRequest")
    @ResponsePayload
    public GetAllLanguagesResponse getAllLanguages(@RequestPayload GetAllLanguagesRequest request){
        GetAllLanguagesResponse response = new GetAllLanguagesResponse();
        response.setLanguage(languageService.getAllLanguages());
        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getLanguageByIdRequest")
    @ResponsePayload
    public GetLanguageByIdResponse getLanguageById(@RequestPayload GetLanguageByIdRequest request){
        GetLanguageByIdResponse response = new GetLanguageByIdResponse();

        if (languageService.exists(request.getLanguageId())){
            response.setLanguage(languageService.getLanguageById(request.getLanguageId()));
        } else {
            response.setError("language " + Constants.DNE);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getLanguageByNameRequest")
    @ResponsePayload
    public GetLanguageByNameResponse getLanguageByName(@RequestPayload GetLanguageByNameRequest request){
        GetLanguageByNameResponse response = new GetLanguageByNameResponse();

        if (languageService.exists(request.getLanguageName())){
            response.setLanguage(languageService.getLanguageByName(request.getLanguageName()));
        } else {
            response.setError("language " + Constants.DNE);
        }

        return response;
    }
}
