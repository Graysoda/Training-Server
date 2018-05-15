package training.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import training.generated.*;
import training.service.InventoryServiceImpl;

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
	public ResponseEntity<?> insertInventory(@RequestPayload CreateInventoryRequest request){
		return inventoryService.insert(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteInventoryRequest")
    @ResponsePayload
	public ResponseEntity<?> deleteInventory(@RequestPayload DeleteInventoryRequest request){
		return inventoryService.deleteInventory(request.getInventoryId());
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateInventoryRequest")
    @ResponsePayload
	public ResponseEntity<?> updateInventory(@RequestPayload UpdateInventoryRequest request){
		return inventoryService.updateInventory(request);
	}
}
