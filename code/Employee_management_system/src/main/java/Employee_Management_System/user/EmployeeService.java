package Employee_Management_System.user;

import Employee_Management_System.credential.Credential;
import Employee_Management_System.credential.CredentialRepository;
import at.favre.lib.bytes.Bytes;
import at.favre.lib.idmask.Config;
import at.favre.lib.idmask.IdMask;
import at.favre.lib.idmask.IdMasks;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class EmployeeService {

    byte[] key = Bytes.random(16).array();
    IdMask<UUID> idMask = IdMasks.forUuids(Config.builder(key).build());

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CredentialRepository credentialRepository;


    public List<Employee> getAllEmployee() {

        var employees = employeeRepository.findAll();
        List<Employee> transformed = new ArrayList<>();
        for (var employee : employees){
            var temp = employee.copy();
            temp.setId(mask(employee.getId()));
            transformed.add(temp);
        }
        return transformed;
    }

    public void addNewEmployee(Employee employee) {
        Optional<Employee> employeeByName = employeeRepository.findEmployeeByName(employee.getName());
        if(employeeByName.isPresent()) {
            throw new IllegalStateException("name taken");
        }
        employeeRepository.save(employee);
    }

    public void deleteEmployeeById(String employeeId) {
        var id = unmask(employeeId);
        boolean employeeExist = employeeRepository.existsById(id);
        if (!employeeExist){
            throw new IllegalStateException("Employee does not exist");
        }
        employeeRepository.deleteById(id);
    }

    //non-query based method
    @Transactional
    public void updateEmployeeName(String employeeId, String name) {

        Employee employee = employeeRepository.findById(unmask(employeeId)).get();

        employee.setName(name);

    }

    public Optional<Employee> getEmployeeById(String id) {
        return employeeRepository.findEmployeeById(unmask(id));
    }

    public Employee save(Employee employee) {
        employeeRepository.save(employee);
        return employee;
    }

    public Optional<Employee> getEmployeeByUsername(String username){
        Optional<Credential> cred = credentialRepository.findByUsername(username);
        String name = cred.get().getFirstname();
        return employeeRepository.findEmployeeByName(name);
    }

    public String mask(String id){

        return idMask.mask(UUID.fromString(id));

    }

    public String unmask(String maskedId){
        return  idMask.unmask(maskedId).toString();
    }
}
