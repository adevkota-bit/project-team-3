package repository;

import model.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacationRequestRepository extends JpaRepository<VacationRequest, Integer> {

    List<VacationRequest> findByEmployeeId(Integer employeeId);
}
