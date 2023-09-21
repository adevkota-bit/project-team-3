import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
import service.EmployeeService;
import repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

public class EmployeeServiceTests {

    private EmployeeRepository employeeRepository;
    EmployeeService employeeService = new EmployeeService(employeeRepository);
//    public void testAddNewEmployee() {
//
//    }
    @Test
    public void testGetAllEmployee() {
//        List<String > employeeList = new ArrayList<String>();
//        employeeList.add("A");
//        employeeRepository = (EmployeeRepository) employeeList;

        assertEquals(employeeService.getAllEmployee(), employeeRepository);
    }
}
