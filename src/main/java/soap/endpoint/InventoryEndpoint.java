package soap.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soap.generated.*;
import soap.service.InventoryServiceImpl;

import java.sql.SQLException;

@Endpoint
public class InventoryEndpoint {
	private static final String NAMESPACE_URI = Constants.NAMESPACE_URI;
	private InventoryServiceImpl inventoryService;

	@Autowired
	public void setInventoryService(InventoryServiceImpl inventoryService){
		this.inventoryService = inventoryService;
	}

	@PayloadRoot(namespace = NAMESPACE_URI,localPart = "getAllInventoryRequest")
	@ResponsePayload
	public GetAllInventoryResponse getAllInventory(@RequestPayload GetAllInventoryRequest request) throws SQLException {
		GetAllInventoryResponse response = new GetAllInventoryResponse();
		response.setInventory(inventoryService.getAllInventory());
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInventoryByIdRequest")
	@ResponsePayload
	public GetInventoryByIdResponse getInventoryById(@RequestPayload GetInventoryByIdRequest request) throws SQLException {
		GetInventoryByIdResponse response = new GetInventoryByIdResponse();
		response.setInventory(inventoryService.getInventoryById(request.getInventoryId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getStoreInventoryRequest")
	@ResponsePayload
	public GetStoreInventoryResponse getStoreInventory(@RequestPayload GetStoreInventoryRequest request) throws SQLException {
		GetStoreInventoryResponse response = new GetStoreInventoryResponse();
		response.setInventory(inventoryService.getStoreInventory(request.getStoreId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createInventoryRequest")
	public void insertInventory(@RequestPayload CreateInventoryRequest request){
		inventoryService.insert(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteInventoryRequest")
	public void deleteInventory(@RequestPayload DeleteInventoryRequest request){
		inventoryService.deleteInventory(request.getInventoryId());
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateInventoryRequest")
	public void updateInventory(@RequestPayload UpdateInventoryRequest request){
		inventoryService.updateInventory(request);
	}
}
