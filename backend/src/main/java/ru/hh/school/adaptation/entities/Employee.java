package ru.hh.school.adaptation.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;
import ru.hh.nab.hibernate.datasource.Default;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employee")
public class Employee {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_generator")
    @SequenceGenerator(name="employee_generator", sequenceName = "employee_id_seq", allocationSize = 1)
    @Column(name = "id", updatable=false)
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "employment_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Employee.DATE_TIME_FORMAT)
    private Date employmentTimestamp = new Date();

    @Column(name = "update_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Employee.DATE_TIME_FORMAT)
    private Date updateTimestamp;

    public Employee() {

    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getEmploymentTimestamp() {
        return employmentTimestamp;
    }

    public void setEmploymentTimestamp(Date employmentTimestamp) {
        this.employmentTimestamp = employmentTimestamp;
    }

    public Date getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Date updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }
}
