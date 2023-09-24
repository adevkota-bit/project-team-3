package controller;

import model.VacationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.VacationRequestService;

import java.util.List;

@RestController
@RequestMapping("/vacation-requests")
public class VacationRequestController {

    @Autowired
    private VacationRequestService vacationRequestService;

    // CREATE
    @PostMapping
    public VacationRequest createRequest(@RequestBody VacationRequest vacationRequest) {
        return vacationRequestService.createRequest(vacationRequest);
    }

    // READ
    @GetMapping("/{id}")
    public VacationRequest getRequest(@PathVariable Integer id) {
        return vacationRequestService.getRequest(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public VacationRequest updateRequest(@PathVariable Integer id, @RequestBody VacationRequest vacationRequestDetails) {
        return vacationRequestService.updateRequest(id, vacationRequestDetails);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRequest(@PathVariable Integer id) {
        vacationRequestService.deleteRequest(id);
        return ResponseEntity.ok().build();
    }

    // READ ALL
    @GetMapping
    public List<VacationRequest> getAllRequests() {
        return vacationRequestService.getAllRequests();
    }
}

