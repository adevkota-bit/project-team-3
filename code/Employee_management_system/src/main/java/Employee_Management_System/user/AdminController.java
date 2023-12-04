package Employee_Management_System.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import Exception.ResourceNotFoundException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@CrossOrigin("http://localhost:3000")
public class AdminController {
    @Autowired
    private EmployeeService employeeService;

//    @Autowired
//    public AdminController(EmployeeService employeeService) {
//        this.employeeService = employeeService;
//    }


    @GetMapping(path = "/allemployee")
    //@GetMapping
    public List<Employee> getAllEmployee() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return employeeService.getAllEmployee();
    }

    @PostMapping
    public void addNewEmployee(@RequestBody Employee employee){
        employeeService.addNewEmployee(employee);
    }

    @DeleteMapping(path = "{employeeID}")
    public void deleteEmployee(@PathVariable String employeeID) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        employeeService.deleteEmployeeById(employeeID);
    }

    @PutMapping("{employeeId}")
    public void updateEmployeeName(
            @PathVariable String employeeId,
            @RequestParam(required = false) String name
    ) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        employeeService.updateEmployeeName(employeeId, name);
    }

    @GetMapping("/employee/{id}")
    public Optional<Employee> getEmployeeById(@PathVariable String id ) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
       var employee = employeeService.getEmployeeById(id);

       if (employee.isPresent()){
           return employee;
       }else{
           throw new ResourceNotFoundException("Cannot find employee with id: "+ id);
       }
    }


    @PutMapping("/employee/{id}")
    public Optional<Employee> updateEmployeeJobTitle(@RequestBody Employee newEmployee, @PathVariable String id) throws Exception {

        return Optional.ofNullable(employeeService.getEmployeeById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setJobTitle(newEmployee.getJobTitle());
                    employee.setAnnualSalary(newEmployee.getAnnualSalary());
                    return employeeService.save(employee);
                }).orElseThrow(() -> new Exception("Cannot find employee with id: "+ id)));

    }


}
