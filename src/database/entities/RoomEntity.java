package database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "room", schema = "public", catalog = "sbd")
public class RoomEntity implements Serializable {
    private int roomId;
    private String roomType;
    private Integer roomSpace;
    private Integer buildingId;
    private BuildingEntity buildingEntity;
    private List<SensorEntity> sensorEntityList;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Column(name = "room_type")
    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    @Column(name = "room_space")
    public Integer getRoomSpace() {
        return roomSpace;
    }

    public void setRoomSpace(Integer roomSpace) {
        this.roomSpace = roomSpace;
    }

    @Column(name = "building_id")
    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "building_id", nullable = false, insertable = false, updatable = false)
    public BuildingEntity getBuildingEntity() {
        return buildingEntity;
    }

    public void setBuildingEntity(BuildingEntity buildingEntity) {
        this.buildingEntity = buildingEntity;
    }


    @OneToMany(mappedBy = "roomEntity")
    public List<SensorEntity> getSensorEntityList() {
        return sensorEntityList;
    }

    public void setSensorEntityList(List<SensorEntity> sensorEntityList) {
        this.sensorEntityList = sensorEntityList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomEntity that = (RoomEntity) o;

        if (roomId != that.roomId) return false;
        if (roomType != null ? !roomType.equals(that.roomType) : that.roomType != null) return false;
        if (roomSpace != null ? !roomSpace.equals(that.roomSpace) : that.roomSpace != null) return false;
        if (buildingId != null ? !buildingId.equals(that.buildingId) : that.buildingId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roomId;
        result = 31 * result + (roomType != null ? roomType.hashCode() : 0);
        result = 31 * result + (roomSpace != null ? roomSpace.hashCode() : 0);
        result = 31 * result + (buildingId != null ? buildingId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RoomEntity{" +
                "roomId=" + roomId +
                ", roomType='" + roomType + '\'' +
                ", roomSpace=" + roomSpace +
                ", buildingId=" + buildingId +
                '}';
    }
}
