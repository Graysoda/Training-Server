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
import training.persistence.dto.InventoryDto;
import training.service.InventoryService;
import training.service.StoreService;

@Endpoint
public class InventoryEndpoint {
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ValidationHelper validationHelper;

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getAllInventoryRequest")
    @ResponsePayload
    public GetAllInventoryResponse getAllInventory(@RequestPayload GetAllInventoryRequest request){
        GetAllInventoryResponse response = new GetAllInventoryResponse();
        response.setInventory(inventoryService.getAllInventory());
        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getInventoryByIdRequest")
    @ResponsePayload
    public GetInventoryByIdResponse getInventoryById(@RequestPayload GetInventoryByIdRequest request){
        GetInventoryByIdResponse response = new GetInventoryByIdResponse();

        if (inventoryService.exists(request.getInventoryId())){
            response.setInventory(inventoryService.getInevntoryById(request.getInventoryId()));
        } else {
            response.setError("inventory " + Constants.DNE);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getStoreInventoryRequest")
    @ResponsePayload
    public GetStoreInventoryResponse getStoreInventory(@RequestPayload GetStoreInventoryRequest request){
        GetStoreInventoryResponse response = new GetStoreInventoryResponse();

        if (storeService.exists(request.getStoreId())){
            response.setInventory(inventoryService.getStoreInventory(request.getStoreId()));
        } else {
            response.setError("store " + Constants.DNE);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "createInventoryRequest")
    @ResponsePayload
    public CreateInventoryResponse createInventory(@RequestPayload CreateInventoryRequest request){
        CreateInventoryResponse response = new CreateInventoryResponse();
        InventoryDto inventory = new InventoryDto(request);
        String error = validationHelper.validateInventoryCreation(inventory);

        if (StringHelper.isNullOrEmptyString(error)){
            response.setInventory(inventoryService.save(inventory));
        } else {
            response.setError(error);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "updateInventoryRequest")
    @ResponsePayload
    public UpdateInventoryResponse updateInventory(@RequestPayload UpdateInventoryRequest request){
        UpdateInventoryResponse response = new UpdateInventoryResponse();
        InventoryDto inventory = new InventoryDto(request);
        String error = validationHelper.validateInventoryCreation(inventory);

        if (StringHelper.isNullOrEmptyString(error)){
            response.setInventory(inventoryService.save(inventory));
        } else {
            response.setError(error);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "deleteInventoryRequest")
    @ResponsePayload
    public DeleteInventoryResponse deleteInventory(@RequestPayload DeleteInventoryRequest request){
        DeleteInventoryResponse response = new DeleteInventoryResponse();

        if (request.getInventoryId() <= 0 || !inventoryService.exists(request.getInventoryId())){
            response.setResponse("film id invalid.");
        } else {
            inventoryService.delete(request.getInventoryId());
            response.setResponse("film with id [" + request.getInventoryId() + "] was deleted.");
        }

        return response;
    }
}
