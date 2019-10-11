package training.persistence.dto;

import lombok.Data;
import training.generated.CreateStoreRequest;
import training.generated.UpdateStoreRequest;
import training.persistence.entity.Address;
import training.persistence.entity.Staff;
import training.persistence.entity.Store;

@Data
public class StoreDto {
    private Integer id;
    private Integer staff;
    private Integer address;

    public StoreDto(){}

    public StoreDto(CreateStoreRequest request) {
        staff = request.getStaffId();
        address = request.getAddressId();
    }

    public StoreDto(UpdateStoreRequest request) {
        id = request.getStoreId();
        staff = request.getStaffId();
        address = request.getAddressId();
    }

    public Store makeEntity(Address address, Staff staff) {
        Store store = new Store();
        store.setManager(staff);
        store.setAddress(address);
        return store;
    }

    public Store makeEntity(Store s) {
        return makeEntity(s.getAddress(), s.getManager());
    }
}
