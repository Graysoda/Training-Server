package training.database.dao;

import training.generated.CreateRentalRequest;
import training.generated.Rental;
import training.generated.UpdateRentalRequest;

import java.util.List;

public interface RentalDao {
    List<Rental> getAll();
    Rental getById(long id);
    List<Rental> getByCustomerId(long id);
    List<Rental> getByStaffId(long id);
    List<Rental> getByStartDate(String date);
    List<Rental> getByReturnDate(String date);
    void insert(CreateRentalRequest request);
    void update(UpdateRentalRequest request);
    void delete(long id);
}
