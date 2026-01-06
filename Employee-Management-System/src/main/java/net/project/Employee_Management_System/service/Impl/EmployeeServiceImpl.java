package net.project.Employee_Management_System.service.Impl;

import lombok.AllArgsConstructor;
import net.project.Employee_Management_System.dto.EmployeeDto;
import net.project.Employee_Management_System.entity.Employee;
import net.project.Employee_Management_System.exception.ResourceNotFoundException;
import net.project.Employee_Management_System.mapper.EmployeeMapper;
import net.project.Employee_Management_System.repository.EmployeeRepository;
import net.project.Employee_Management_System.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;


    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new ResourceNotFoundException("Employee is not Exist with given id : " + employeeId));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map((employee) ->EmployeeMapper.mapToEmployeeDto(employee))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {

       Employee employee =  employeeRepository.findById(employeeId).orElseThrow(
                ()-> new ResourceNotFoundException("Employee is not exists with given id: " + employeeId)
        );

       employee.setFirstName(updatedEmployee.getFirstName());
       employee.setLastName(updatedEmployee.getLastName());
       employee.setEmail(updatedEmployee.getEmail());

       Employee updateEmployeeObj = employeeRepository.save(employee);

       return EmployeeMapper.mapToEmployeeDto(updateEmployeeObj);
    }

    @Override
    public void deleteEmployee(Long employeeId) {

        Employee employee =  employeeRepository.findById(employeeId).orElseThrow(
                ()-> new ResourceNotFoundException("Employee is not exists with given id: " + employeeId)
        );

        employeeRepository.deleteById(employeeId);




    }
}
