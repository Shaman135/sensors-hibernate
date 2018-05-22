package database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "client", schema = "public", catalog = "sbd")
public class ClientEntity implements Serializable {
    private int clientId;
    private String clientFirstname;
    private String clientSecondname;
    private String subscriptionModel;
    private Integer administrationId;
    private AdministrationEntity administrationEntity;
    private List<ServerEntity> serverEntityList;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Column(name = "client_firstname")
    public String getClientFirstname() {
        return clientFirstname;
    }

    public void setClientFirstname(String clientFirstname) {
        this.clientFirstname = clientFirstname;
    }

    @Column(name = "client_secondname")
    public String getClientSecondname() {
        return clientSecondname;
    }

    public void setClientSecondname(String clientSecondname) {
        this.clientSecondname = clientSecondname;
    }

    @Column(name = "subscription_model")
    public String getSubscriptionModel() {
        return subscriptionModel;
    }

    public void setSubscriptionModel(String subscriptionModel) {
        this.subscriptionModel = subscriptionModel;
    }

    @Column(name = "administration_id")
    public Integer getAdministrationId() {
        return administrationId;
    }

    public void setAdministrationId(Integer administrationId) {
        this.administrationId = administrationId;
    }


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "administration_id", nullable = false, insertable = false, updatable = false)
    public AdministrationEntity getAdministrationEntity() {
        return administrationEntity;
    }

    public void setAdministrationEntity(AdministrationEntity administrationEntity) {
        this.administrationEntity = administrationEntity;
    }


    @OneToMany(mappedBy = "clientEntity")
    public List<ServerEntity> getServerEntityList() {
        return serverEntityList;
    }

    public void setServerEntityList(List<ServerEntity> serverEntityList) {
        this.serverEntityList = serverEntityList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientEntity that = (ClientEntity) o;

        if (clientId != that.clientId) return false;
        if (clientFirstname != null ? !clientFirstname.equals(that.clientFirstname) : that.clientFirstname != null)
            return false;
        if (clientSecondname != null ? !clientSecondname.equals(that.clientSecondname) : that.clientSecondname != null)
            return false;
        if (subscriptionModel != null ? !subscriptionModel.equals(that.subscriptionModel) : that.subscriptionModel != null)
            return false;
        if (administrationId != null ? !administrationId.equals(that.administrationId) : that.administrationId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = clientId;
        result = 31 * result + (clientFirstname != null ? clientFirstname.hashCode() : 0);
        result = 31 * result + (clientSecondname != null ? clientSecondname.hashCode() : 0);
        result = 31 * result + (subscriptionModel != null ? subscriptionModel.hashCode() : 0);
        result = 31 * result + (administrationId != null ? administrationId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ClientEntity{" +
                "clientId=" + clientId +
                ", clientFirstname='" + clientFirstname + '\'' +
                ", clientSecondname='" + clientSecondname + '\'' +
                ", subscriptionModel='" + subscriptionModel + '\'' +
                ", administrationId=" + administrationId +
                '}';
    }
}
