package com.corpmatrix;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "employee")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "employee_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;
    
    private String firstName;
    private String lastName;
    private String department;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;
    
    @OneToMany(mappedBy = "manager")
    private List<Employee> directReports;
    
    public Employee() {}
    public Employee(String firstName, String lastName, String department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
    }
    
    public Long getEmployeeId() { return employeeId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getDepartment() { return department; }
    public Employee getManager() { return manager; }
    public void setManager(Employee manager) { this.manager = manager; }
    public List<Employee> getDirectReports() { return directReports; }
}
