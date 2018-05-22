package database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "sensor", schema = "public", catalog = "sbd")
public class SensorEntity implements Serializable {
    private int sensorId;
    private String sensorType;
    private String serialNumber;
    private String producent;
    private Integer roomId;
    private RoomEntity roomEntity;
    private List<DataRecordEntity> dataRecordEntityList;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_id")
    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    @Column(name = "sensor_type")
    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    @Column(name = "serial_number")
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Column(name = "producent")
    public String getProducent() {
        return producent;
    }

    public void setProducent(String producent) {
        this.producent = producent;
    }

    @Column(name = "room_id")
    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false, insertable = false, updatable = false)
    public RoomEntity getRoomEntity() {
        return roomEntity;
    }

    public void setRoomEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
    }

    @OneToMany(mappedBy = "sensorEntity")
    public List<DataRecordEntity> getDataRecordEntityList() {
        return dataRecordEntityList;
    }

    public void setDataRecordEntityList(List<DataRecordEntity> dataRecordEntityList) {
        this.dataRecordEntityList = dataRecordEntityList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SensorEntity that = (SensorEntity) o;

        if (sensorId != that.sensorId) return false;
        if (sensorType != null ? !sensorType.equals(that.sensorType) : that.sensorType != null) return false;
        if (serialNumber != null ? !serialNumber.equals(that.serialNumber) : that.serialNumber != null) return false;
        if (producent != null ? !producent.equals(that.producent) : that.producent != null) return false;
        if (roomId != null ? !roomId.equals(that.roomId) : that.roomId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sensorId;
        result = 31 * result + (sensorType != null ? sensorType.hashCode() : 0);
        result = 31 * result + (serialNumber != null ? serialNumber.hashCode() : 0);
        result = 31 * result + (producent != null ? producent.hashCode() : 0);
        result = 31 * result + (roomId != null ? roomId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SensorEntity{" +
                "sensorId=" + sensorId +
                ", sensorType='" + sensorType + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", producent='" + producent + '\'' +
                ", roomId=" + roomId +
                '}';
    }
}
