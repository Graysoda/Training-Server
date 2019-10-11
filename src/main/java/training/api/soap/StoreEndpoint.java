package training.api.soap;

import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import training.Constants;
import training.api.ValidationHelper;
import training.generated.*;
import training.persistence.dto.StoreDto;
import training.service.StoreService;

@Endpoint
public class StoreEndpoint {
    @Autowired
    private StoreService storeService;
    @Autowired
    private ValidationHelper validationHelper;

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getAllStoresRequest")
    @ResponsePayload
    public GetAllStoresResponse getAllStores(@RequestPayload GetAllStoresRequest request){
        GetAllStoresResponse response = new GetAllStoresResponse();
        response.setStores(storeService.getAll());
        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getStoreByIdRequest")
    @ResponsePayload
    public GetStoreByIdResponse getStoreById(@RequestPayload GetStoreByIdRequest request){
        GetStoreByIdResponse response = new GetStoreByIdResponse();

        if (storeService.exists(request.getStoreId())){
            response.setStores(storeService.getStoreById(request.getStoreId()));
        } else {
            response.setError("store " + Constants.DNE);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getStoreAddressRequest")
    @ResponsePayload
    public GetStoreAddressResponse getStoreAddress(@RequestPayload GetStoreAddressRequest request){
        GetStoreAddressResponse response = new GetStoreAddressResponse();

        if (storeService.exists(request.getStoreId())){
            response.setStoreAddress(storeService.getStoreById(request.getStoreId()).getAddress());
        } else {
            response.setError("store "+ Constants.DNE);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getStoreManagerRequest")
    @ResponsePayload
    public GetStoreManagerResponse getStoreManager(@RequestPayload GetStoreManagerRequest request){
        GetStoreManagerResponse response = new GetStoreManagerResponse();

        if (storeService.exists(request.getStoreId())){
            response.setStoreManager(storeService.getStoreById(request.getStoreId()).getManager());
        } else {
            response.setError("store " + Constants.DNE);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "createStoreRequest")
    @ResponsePayload
    public CreateStoreResponse createStore(@RequestPayload CreateStoreRequest request){
        CreateStoreResponse response = new CreateStoreResponse();
        StoreDto store = new StoreDto(request);
        String error = validationHelper.validateStoreCreation(store);

        if (StringHelper.isNullOrEmptyString(error)){
            response.setStore(storeService.save(store));
        } else {
            response.setError(error);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "updateStoreRequest")
    @ResponsePayload
    public UpdateStoreResponse updateStore(@RequestPayload UpdateStoreRequest request){
        UpdateStoreResponse response = new UpdateStoreResponse();
        StoreDto store = new StoreDto(request);
        String error = validationHelper.validateStoreUpdate(store);

        if (StringHelper.isNullOrEmptyString(error)){
            response.setStore(storeService.save(store));
        } else {
            response.setError(error);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "deleteStoreRequest")
    @ResponsePayload
    public DeleteStoreResponse deleteStore(@RequestPayload DeleteStoreRequest request){
        DeleteStoreResponse response = new DeleteStoreResponse();

        if (request.getStoreId() <= 0 || !storeService.exists(request.getStoreId())){
            response.setResponse("store id is invalid.");
        } else {
            storeService.delete(request.getStoreId());
            response.setResponse("store with id [" + request.getStoreId() + "] was deleted.");
        }

        return response;
    }
}
