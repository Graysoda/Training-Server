package training.persistence.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import training.persistence.entity.Rental;

import java.util.Date;


@Repository
public interface RentalDao extends CrudRepository<Rental, Integer> {
    Iterable<Rental> findAllByCustomerId(int customerId);

    Iterable<Rental> findAllByRentalDate(Date startDate);

    Iterable<Rental> findAllByReturnDate(Date returnDate);

    Iterable<Rental> findAllByStaffId(int staffId);
}
