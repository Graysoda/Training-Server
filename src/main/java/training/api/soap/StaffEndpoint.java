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
import training.persistence.dto.StaffDto;
import training.service.StaffService;
import training.service.StoreService;

@Endpoint
public class StaffEndpoint {
    @Autowired
    private StaffService staffService;
    @Autowired
    private ValidationHelper validationHelper;
    @Autowired
    private StoreService storeService;

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getAllStaffRequest")
    @ResponsePayload
    public GetAllStaffResponse getAllStaff(@RequestPayload GetAllStaffRequest request){
        GetAllStaffResponse response = new GetAllStaffResponse();
        response.setStaff(staffService.getAllStaff());
        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getStaffByIdRequest")
    @ResponsePayload
    public GetStaffByIdResponse getStaffById(@RequestPayload GetStaffByIdRequest request){
        GetStaffByIdResponse response = new GetStaffByIdResponse();

        if (staffService.exists(request.getStaffId())){
            response.setStaff(staffService.getStaffById(request.getStaffId()));
        } else {
            response.setError("staff " + Constants.DNE);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getStaffByStoreRequest")
    @ResponsePayload
    public GetStaffByStoreResponse getStaffByStore(@RequestPayload GetStaffByStoreRequest request){
        GetStaffByStoreResponse response = new GetStaffByStoreResponse();

        if (storeService.exists(request.getStoreId())){
            response.setStaff(staffService.getStaffByStoreId(request.getStoreId()));
        } else {
            response.setError("store " + Constants.DNE);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getActiveStaffRequest")
    @ResponsePayload
    public GetActiveStaffResponse getActiveStaff(@RequestPayload GetActiveStaffRequest request){
        GetActiveStaffResponse response = new GetActiveStaffResponse();
        response.setStaff(staffService.getActiveStaff());
        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getInactiveStaffRequest")
    @ResponsePayload
    public GetInactiveStaffResponse getInactiveStaff(@RequestPayload GetInactiveStaffRequest request){
        GetInactiveStaffResponse response = new GetInactiveStaffResponse();
        response.setStaff(staffService.getInactiveStaff());
        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "createStaffRequest")
    @ResponsePayload
    public CreateStaffResponse createStaff(@RequestPayload CreateStaffRequest request){
        CreateStaffResponse response = new CreateStaffResponse();
        StaffDto staff = new StaffDto(request);
        String error = validationHelper.validateStaffCreation(staff);

        if (StringHelper.isNullOrEmptyString(error)){
            response.setStaff(staffService.save(staff));
        } else {
            response.setError(error);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "updateStaffRequest")
    @ResponsePayload
    public UpdateStaffResponse updateStaff(@RequestPayload UpdateStaffRequest request){
        UpdateStaffResponse response = new UpdateStaffResponse();
        StaffDto staff = new StaffDto(request);
        String error = validationHelper.validateStaffUpdate(staff);

        if (StringHelper.isNullOrEmptyString(error)){
            response.setStaff(staffService.save(staff));
        } else {
            response.setError(error);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "deleteStaffRequest")
    @ResponsePayload
    public DeleteStaffResponse deleteStaff(@RequestPayload DeleteStaffRequest request){
        DeleteStaffResponse response = new DeleteStaffResponse();

        if (request.getStaffId() <= 0 || !staffService.exists(request.getStaffId())){
            response.setResponse("staff id is invalid.");
        } else {
            staffService.delete(request.getStaffId());
            response.setResponse("staff with id [" + request.getStaffId() + "] was deleted.");
        }

        return response;
    }

}
