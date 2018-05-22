package database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "administration", schema = "public", catalog = "sbd")
public class AdministrationEntity implements Serializable {
    private int administrationId;
    private String administrationAddress;
    private BigInteger nip;
    private List<AdministrationEmployeeEntity> administrationEmployeeEntityList = new ArrayList<>();
    private List<ClientEntity> clientEntityList = new ArrayList<>();
    private List<ContractEntity> contractEntityList = new ArrayList<>();
    private List<ServerEntity> serverEntityList = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "administration_id")
    public int getAdministrationId() {
        return administrationId;
    }

    public void setAdministrationId(int administrationId) {
        this.administrationId = administrationId;
    }

    @Column(name = "administration_address")
    public String getAdministrationAddress() {
        return administrationAddress;
    }

    public void setAdministrationAddress(String administrationAddress) {
        this.administrationAddress = administrationAddress;
    }

    @Column(name = "nip")
    public BigInteger getNip() {
        return nip;
    }

    public void setNip(BigInteger nip) {
        this.nip = nip;
    }

    @OneToMany(mappedBy = "administrationEntity")
    public List<AdministrationEmployeeEntity> getAdministrationEmployeeEntityList() {
        return this.administrationEmployeeEntityList;
    }

    public void setAdministrationEmployeeEntityList(List<AdministrationEmployeeEntity> administrationEmployees) {
        this.administrationEmployeeEntityList = administrationEmployees;
    }

    @OneToMany(mappedBy = "administrationEntity")
    public List<ClientEntity> getClientEntityList() {
        return clientEntityList;
    }

    public void setClientEntityList(List<ClientEntity> clientEntities) {
        this.clientEntityList = clientEntities;
    }

    @OneToMany(mappedBy = "administrationEntity")
    public List<ContractEntity> getContractEntityList() {
        return contractEntityList;
    }

    public void setContractEntityList(List<ContractEntity> contractEntities) {
        this.contractEntityList = contractEntities;
    }

    @OneToMany(mappedBy = "administrationEntity")
    public List<ServerEntity> getServerEntityList() {
        return serverEntityList;
    }

    public void setServerEntityList(List<ServerEntity> serverEntities) {
        this.serverEntityList = serverEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdministrationEntity that = (AdministrationEntity) o;

        if (administrationId != that.administrationId) return false;
        if (administrationAddress != null ? !administrationAddress.equals(that.administrationAddress) : that.administrationAddress != null)
            return false;
        if (nip != null ? !nip.equals(that.nip) : that.nip != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = administrationId;
        result = 31 * result + (administrationAddress != null ? administrationAddress.hashCode() : 0);
        result = 31 * result + (nip != null ? nip.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AdministrationEntity{" +
                "administrationId=" + administrationId +
                ", administrationAddress='" + administrationAddress + '\'' +
                ", nip=" + nip +
                '}';
    }
}
