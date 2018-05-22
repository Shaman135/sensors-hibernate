package database.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "administration_employee", schema = "public", catalog = "sbd")
public class AdministrationEmployeeEntity implements Serializable{
    private int employeeId;
    private Integer administrationId;
    private String firstname;
    private String secondname;
    private String position;
    private AdministrationEntity administrationEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Column(name = "administration_id")
    public Integer getAdministrationId() {
        return administrationId;
    }

    public void setAdministrationId(Integer administrationId) {
        this.administrationId = administrationId;
    }

    @Column(name = "firstname")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Column(name = "secondname")
    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    @Column(name = "position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "administration_id", nullable = false, insertable = false, updatable = false)
    public AdministrationEntity getAdministrationEntity() {
        return administrationEntity;
    }

    public void setAdministrationEntity(AdministrationEntity administrationEntity) {
        this.administrationEntity = administrationEntity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdministrationEmployeeEntity that = (AdministrationEmployeeEntity) o;

        if (employeeId != that.employeeId) return false;
        if (administrationId != null ? !administrationId.equals(that.administrationId) : that.administrationId != null)
            return false;
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        if (secondname != null ? !secondname.equals(that.secondname) : that.secondname != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = employeeId;
        result = 31 * result + (administrationId != null ? administrationId.hashCode() : 0);
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (secondname != null ? secondname.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AdministrationEmployeeEntity{" +
                "employeeId=" + employeeId +
                ", administrationId=" + administrationId +
                ", firstname='" + firstname + '\'' +
                ", secondname='" + secondname + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
