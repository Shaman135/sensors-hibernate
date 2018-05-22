package database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "server", schema = "public", catalog = "sbd")
public class ServerEntity implements Serializable {
    private int serverId;
    private String serverName;
    private Integer clientId;
    private Integer administrationId;
    private AdministrationEntity administrationEntity;
    private ClientEntity clientEntity;
    private List<BuildingEntity> buildingEntityList;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "server_id")
    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    @Column(name = "server_name")
    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @Column(name = "client_id")
    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "server_id", nullable = false, insertable = false, updatable = false)
    public ClientEntity getClientEntity() {
        return clientEntity;
    }

    public void setClientEntity(ClientEntity clientEntity) {
        this.clientEntity = clientEntity;
    }


    @OneToMany(mappedBy = "serverEntity")
    public List<BuildingEntity> getBuildingEntityList() {
        return buildingEntityList;
    }

    public void setBuildingEntityList(List<BuildingEntity> buildingEntitiesList) {
        this.buildingEntityList = buildingEntitiesList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerEntity that = (ServerEntity) o;

        if (serverId != that.serverId) return false;
        if (serverName != null ? !serverName.equals(that.serverName) : that.serverName != null) return false;
        if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null) return false;
        if (administrationId != null ? !administrationId.equals(that.administrationId) : that.administrationId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = serverId;
        result = 31 * result + (serverName != null ? serverName.hashCode() : 0);
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        result = 31 * result + (administrationId != null ? administrationId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ServerEntity{" +
                "serverId=" + serverId +
                ", serverName='" + serverName + '\'' +
                ", clientId=" + clientId +
                ", administrationId=" + administrationId +
                '}';
    }
}
