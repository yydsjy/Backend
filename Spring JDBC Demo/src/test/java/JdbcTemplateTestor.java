import com.example.spring.jdbc.dao.EmployeeDao;
import com.example.spring.jdbc.entity.Employee;
import com.example.spring.jdbc.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class JdbcTemplateTestor {
    @Resource
    private EmployeeDao employeeDao;
    @Resource
    private EmployeeService employeeService;

    @Test
    public void testFindById(){
        Employee employee = employeeDao.findById(3308);
        System.out.println(employee);
    }

    @Test
    public void testFindByDname(){
        List<Employee> list = employeeDao.findByDname("");
        System.out.println(list);
    }

    @Test
    public void testFindMapByDname(){
        System.out.println(employeeDao.findMapByDname("研发部"));
    }

    @Test
    public void testInsert(){
        Employee employee= new Employee();
        employee.setEno(8888);
        employee.setEname("Mike");
        employee.setSalary(1234f);
        employee.setDname("R&D");
        employee.setHiredate(new Date());
        employeeDao.insert(employee);
    }

    @Test
    public void testUpdate(){
        Employee employee = employeeDao.findById(8888);
        employee.setDname("Boss");
        int count = employeeDao.update(employee);
        System.out.println(count);
    }

    @Test
    public void testDelete(){
        System.out.println(employeeDao.delete(8888));
    }

    @Test
    public void testBatchImport(){
        employeeService.batchImport();
    }
}
