package training.api.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import training.generated.*;
import training.service.impl.InventoryServiceImpl;

@Endpoint
public class InventoryEndpoint {
	private static final String NAMESPACE_URI = SoapConstants.NAMESPACE_URI;
	@Autowired private InventoryServiceImpl inventoryService;

	@PayloadRoot(namespace = NAMESPACE_URI,localPart = "getAllInventoryRequest")
	@ResponsePayload
	public GetAllInventoryResponse getAllInventory(@RequestPayload GetAllInventoryRequest request) {
		GetAllInventoryResponse response = new GetAllInventoryResponse();
		response.setInventory(inventoryService.getAllInventory());
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInventoryByIdRequest")
	@ResponsePayload
	public GetInventoryByIdResponse getInventoryById(@RequestPayload GetInventoryByIdRequest request) {
		GetInventoryByIdResponse response = new GetInventoryByIdResponse();
		response.setInventory(inventoryService.getInventoryById(request.getInventoryId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getStoreInventoryRequest")
	@ResponsePayload
	public GetStoreInventoryResponse getStoreInventory(@RequestPayload GetStoreInventoryRequest request) {
		GetStoreInventoryResponse response = new GetStoreInventoryResponse();
		response.setInventory(inventoryService.getStoreInventory(request.getStoreId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createInventoryRequest")
    @ResponsePayload
	public CreateInventoryResponse insertInventory(@RequestPayload CreateInventoryRequest request){
		CreateInventoryResponse response = new CreateInventoryResponse();

		if (request.getFilmId() < 0){
			response.setError("filmId is invalid");
			return response;
		}
		if (request.getStoreId() < 0){
			response.setError("storeId is invalid");
			return response;
		}
		ResponseEntity entity = inventoryService.insert(request);

		if (entity.getBody() instanceof Inventory){
			response.setInventory(((Inventory) entity.getBody()));
			return response;
		} else {
			response.setError(entity.getBody().toString());
			return response;
		}
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteInventoryRequest")
    @ResponsePayload
	public DeleteInventoryResponse deleteInventory(@RequestPayload DeleteInventoryRequest request){
		DeleteInventoryResponse response = new DeleteInventoryResponse();

		if (request.getInventoryId() < 0){
			response.setResponse("inventoryId is invalid");
			return response;
		}

		ResponseEntity entity = inventoryService.deleteInventory(request.getInventoryId());

		response.setResponse(entity.getBody().toString());
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateInventoryRequest")
    @ResponsePayload
	public UpdateInventoryResponse updateInventory(@RequestPayload UpdateInventoryRequest request){
		UpdateInventoryResponse response = new UpdateInventoryResponse();

		if (request.getInventoryId() < 0){
			response.setError("inventoryId is invalid");
			return response;
		}

		if (request.getFilmId() < 0){
			response.setError("filmId is invalid");
			return response;
		}
		if (request.getStoreId() < 0){
			response.setError("storeId is invalid");
			return response;
		}

		ResponseEntity entity = inventoryService.updateInventory(request);

		if (entity.getBody() instanceof Inventory){
			response.setInventory(((Inventory) entity.getBody()));
			return response;
		} else {
			response.setError(entity.getBody().toString());
			return response;
		}
	}
}
