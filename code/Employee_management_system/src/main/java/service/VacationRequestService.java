package service;

import model.VacationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.VacationRequestRepository;

import java.util.List;
import Exception.ResourceNotFoundException;

@Service
public class VacationRequestService {

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    // CREATE
    public VacationRequest createRequest(VacationRequest vacationRequest) {
        return vacationRequestRepository.save(vacationRequest);
    }

    // READ
    public VacationRequest getRequest(Integer id) {
        return vacationRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VacationRequest", "id", id));
    }

    // UPDATE
    public VacationRequest updateRequest(Integer id, VacationRequest vacationRequestDetails) {
        VacationRequest vacationRequest = getRequest(id); // or use findById(id).orElseThrow(...)
        // Set fields that need to be updated
        vacationRequest.setStartDate(vacationRequestDetails.getStartDate());
        vacationRequest.setEndDate(vacationRequestDetails.getEndDate());
        return vacationRequestRepository.save(vacationRequest);
    }

    // DELETE
    public void deleteRequest(Integer id) {
        VacationRequest vacationRequest = getRequest(id); // or use findById(id).orElseThrow(...)
        vacationRequestRepository.delete(vacationRequest);
    }

    // READ ALL
    public List<VacationRequest> getAllRequests() {
        return vacationRequestRepository.findAll();
    }
}
