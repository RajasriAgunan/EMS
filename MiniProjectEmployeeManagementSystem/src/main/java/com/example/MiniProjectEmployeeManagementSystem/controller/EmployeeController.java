package com.example.MiniProjectEmployeeManagementSystem.controller;

import com.example.MiniProjectEmployeeManagementSystem.dto.EmployeeDto;
import com.example.MiniProjectEmployeeManagementSystem.dto.LoginRequestDto;
import com.example.MiniProjectEmployeeManagementSystem.entity.Employee;
import com.example.MiniProjectEmployeeManagementSystem.security.JwtUtil;
import com.example.MiniProjectEmployeeManagementSystem.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("api/employees")
public class EmployeeController {
    private EmployeeService employeeService;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

   // @PostMapping("login")
  //  public ResponseEntity<String> login(@RequestBody LoginRequestDto requestDto) {
    //    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
      //          requestDto.getUsername(),
        //        requestDto.getPassword()
       // );
       // authenticationManager.authenticate(token);
       // String jwt = jwtUtil.generate(requestDto.getUsername());
       // return ResponseEntity.ok(jwt);
   // }


    @PreAuthorize("hasRole(\"ADMIN\")")
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        EmployeeDto saveEmp = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(saveEmp, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyRole(\"ADMIN\",\"USER\")")
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.CREATED);
    }
    @GetMapping("{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable("id") Long id) {
        EmployeeDto employeeDto = employeeService.getEmployee(id);
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") Long id
            ,@RequestBody @Valid EmployeeDto employeeDto) {
        employeeDto.setId(id);
        EmployeeDto updateEmployee = employeeService.updateEmployee(employeeDto);
        return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
    }

}