package database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "contract", schema = "public", catalog = "sbd")
public class ContractEntity implements Serializable {
    private int contractId;
    private Integer administrationId;
    private Integer subcontractorId;
    private Date startDate;
    private Date endDate;
    private Integer numberOfHours;
    private AdministrationEntity administrationEntity;
    private SubcontractorEntity subcontractorEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    @Column(name = "administration_id")
    public Integer getAdministrationId() {
        return administrationId;
    }

    public void setAdministrationId(Integer administrationId) {
        this.administrationId = administrationId;
    }

    @Column(name = "subcontractor_id")
    public Integer getSubcontractorId() {
        return subcontractorId;
    }

    public void setSubcontractorId(Integer subcontractorId) {
        this.subcontractorId = subcontractorId;
    }

    @Column(name = "start_date")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name = "end_date")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(name = "number_of_hours")
    public Integer getNumberOfHours() {
        return numberOfHours;
    }

    public void setNumberOfHours(Integer numberOfHours) {
        this.numberOfHours = numberOfHours;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "administration_id", nullable = false, insertable = false, updatable = false)
    public AdministrationEntity getAdministrationEntity() {
        return administrationEntity;
    }

    public void setAdministrationEntity(AdministrationEntity administrationEntity) {
        this.administrationEntity = administrationEntity;
    }


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subcontractor_id", nullable = false, insertable = false, updatable = false)
    public SubcontractorEntity getSubcontractorEntity() {
        return subcontractorEntity;
    }

    public void setSubcontractorEntity(SubcontractorEntity subcontractorEntity) {
        this.subcontractorEntity = subcontractorEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContractEntity that = (ContractEntity) o;

        if (contractId != that.contractId) return false;
        if (administrationId != null ? !administrationId.equals(that.administrationId) : that.administrationId != null)
            return false;
        if (subcontractorId != null ? !subcontractorId.equals(that.subcontractorId) : that.subcontractorId != null)
            return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (numberOfHours != null ? !numberOfHours.equals(that.numberOfHours) : that.numberOfHours != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = contractId;
        result = 31 * result + (administrationId != null ? administrationId.hashCode() : 0);
        result = 31 * result + (subcontractorId != null ? subcontractorId.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (numberOfHours != null ? numberOfHours.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContractEntity{" +
                "contractId=" + contractId +
                ", administrationId=" + administrationId +
                ", subcontractorId=" + subcontractorId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", numberOfHours=" + numberOfHours +
                '}';
    }
}
