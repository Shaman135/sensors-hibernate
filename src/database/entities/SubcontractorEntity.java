package database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "subcontractor", schema = "public", catalog = "sbd")
public class SubcontractorEntity implements Serializable {
    private int subcontractorId;
    private String companyName;
    private BigDecimal pricePerHour;
    private String subcontractorAddress;
    private BigInteger subcontractorNip;
    private List<ContractEntity> contractEntityList;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subcontractor_id")
    public int getSubcontractorId() {
        return subcontractorId;
    }

    public void setSubcontractorId(int subcontractorId) {
        this.subcontractorId = subcontractorId;
    }

    @Column(name = "company_name")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Column(name = "price_per_hour")
    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    @Column(name = "subcontractor_address")
    public String getSubcontractorAddress() {
        return subcontractorAddress;
    }

    public void setSubcontractorAddress(String subcontractorAddress) {
        this.subcontractorAddress = subcontractorAddress;
    }

    @Column(name = "subcontractor_nip")
    public BigInteger getSubcontractorNip() {
        return subcontractorNip;
    }

    public void setSubcontractorNip(BigInteger subcontractorNip) {
        this.subcontractorNip = subcontractorNip;
    }

    @OneToMany(mappedBy = "subcontractorEntity")
    public List<ContractEntity> getContractEntityList() {
        return contractEntityList;
    }

    public void setContractEntityList(List<ContractEntity> contractEntityList) {
        this.contractEntityList = contractEntityList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubcontractorEntity that = (SubcontractorEntity) o;

        if (subcontractorId != that.subcontractorId) return false;
        if (companyName != null ? !companyName.equals(that.companyName) : that.companyName != null) return false;
        if (pricePerHour != null ? !pricePerHour.equals(that.pricePerHour) : that.pricePerHour != null) return false;
        if (subcontractorAddress != null ? !subcontractorAddress.equals(that.subcontractorAddress) : that.subcontractorAddress != null)
            return false;
        if (subcontractorNip != null ? !subcontractorNip.equals(that.subcontractorNip) : that.subcontractorNip != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = subcontractorId;
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (pricePerHour != null ? pricePerHour.hashCode() : 0);
        result = 31 * result + (subcontractorAddress != null ? subcontractorAddress.hashCode() : 0);
        result = 31 * result + (subcontractorNip != null ? subcontractorNip.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SubcontractorEntity{" +
                "subcontractorId=" + subcontractorId +
                ", companyName='" + companyName + '\'' +
                ", pricePerHour=" + pricePerHour +
                ", subcontractorAddress='" + subcontractorAddress + '\'' +
                ", subcontractorNip=" + subcontractorNip +
                '}';
    }
}
