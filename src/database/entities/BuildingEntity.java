package database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "building", schema = "public", catalog = "sbd")
public class BuildingEntity implements Serializable {
    private int buildingId;
    private String buildingAddress;
    private Integer numberOfRooms;
    private Integer serverId;
    private ServerEntity serverEntity;
    private List<RoomEntity> roomEntityList;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_id")
    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    @Column(name = "building_address")
    public String getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) {
        this.buildingAddress = buildingAddress;
    }

    @Column(name = "number_of_rooms")
    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    @Column(name = "server_id")
    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "server_id", nullable = false, insertable = false, updatable = false)
    public ServerEntity getServerEntity() {
        return serverEntity;
    }

    public void setServerEntity(ServerEntity serverEntity) {
        this.serverEntity = serverEntity;
    }

    @OneToMany(mappedBy = "buildingEntity")
    public List<RoomEntity> getRoomEntityList() {
        return roomEntityList;
    }

    public void setRoomEntityList(List<RoomEntity> roomEntityList) {
        this.roomEntityList = roomEntityList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuildingEntity that = (BuildingEntity) o;

        if (buildingId != that.buildingId) return false;
        if (buildingAddress != null ? !buildingAddress.equals(that.buildingAddress) : that.buildingAddress != null)
            return false;
        if (numberOfRooms != null ? !numberOfRooms.equals(that.numberOfRooms) : that.numberOfRooms != null)
            return false;
        if (serverId != null ? !serverId.equals(that.serverId) : that.serverId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = buildingId;
        result = 31 * result + (buildingAddress != null ? buildingAddress.hashCode() : 0);
        result = 31 * result + (numberOfRooms != null ? numberOfRooms.hashCode() : 0);
        result = 31 * result + (serverId != null ? serverId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BuildingEntity{" +
                "buildingId=" + buildingId +
                ", buildingAddress='" + buildingAddress + '\'' +
                ", numberOfRooms=" + numberOfRooms +
                ", serverId=" + serverId +
                '}';
    }
}
