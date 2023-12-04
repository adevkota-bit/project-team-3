package Employee_Management_System.user;

import Employee_Management_System.credential.Credential;
import Employee_Management_System.credential.CredentialRepository;
import Employee_Management_System.util.EncryptDecrypt;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    SecretKey key;

    {
        try {
            key = EncryptDecrypt.generateKey(128);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    IvParameterSpec ivParameterSpec = EncryptDecrypt.generateIv();

    String algorithm = "AES/CBC/PKCS5Padding";


    public List<Employee> getAllEmployee() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        var employees = employeeRepository.findAll();
        List<Employee> transformed = new ArrayList<>();
        for (var employee : employees){
            var temp = employee.copy();
            temp.setId(EncryptDecrypt.encrypt(algorithm,employee.getId(),key,ivParameterSpec));
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

    public void deleteEmployeeById(String employeeId) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        var id = EncryptDecrypt.decrypt(algorithm,employeeId,key,ivParameterSpec);
        boolean employeeExist = employeeRepository.existsById(id);
        if (!employeeExist){
            throw new IllegalStateException("Employee does not exist");
        }
        employeeRepository.deleteById(id);
    }

    //non-query based method
    @Transactional
    public void updateEmployeeName(String employeeId, String name) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        var id = EncryptDecrypt.decrypt(algorithm,employeeId,key,ivParameterSpec);
        Employee employee = employeeRepository.findById(id).get();

        employee.setName(name);

    }

    public Optional<Employee> getEmployeeById(String id) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return employeeRepository.findEmployeeById(EncryptDecrypt.decrypt(algorithm,id,key,ivParameterSpec));
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

}
