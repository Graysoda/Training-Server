package training.controller.jsonObjects;

public class RentalJson {
    private Long customerId;
    private Long staffId;
    private Long inventoryId;
    private String rentalDate;
    private String returnDate;

    public RentalJson() {
    }

    public RentalJson(Long customerId, Long staffId, Long inventoryId, String rentalDate, String returnDate) {
        this.customerId = customerId;
        this.staffId = staffId;
        this.inventoryId = inventoryId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public String getReturnDate() {
        return returnDate;
    }
}
