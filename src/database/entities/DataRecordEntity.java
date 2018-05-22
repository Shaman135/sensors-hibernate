package database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "data_record", schema = "public", catalog = "sbd")
public class DataRecordEntity implements Serializable {
    private int recordId;
    private String record;
    private String dataType;
    private Timestamp timestap;
    private Integer sensorId;
    private SensorEntity sensorEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    @Column(name = "record")
    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    @Column(name = "data_type")
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Column(name = "timestap")
    public Timestamp getTimestap() {
        return timestap;
    }

    public void setTimestap(Timestamp timestap) {
        this.timestap = timestap;
    }

    @Column(name = "sensor_id")
    public Integer getSensorId() {
        return sensorId;
    }

    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
    }


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sensor_id", nullable = false, insertable = false, updatable = false)
    public SensorEntity getSensorEntity() {
        return sensorEntity;
    }

    public void setSensorEntity(SensorEntity sensorEntity) {
        this.sensorEntity = sensorEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataRecordEntity that = (DataRecordEntity) o;

        if (recordId != that.recordId) return false;
        if (record != null ? !record.equals(that.record) : that.record != null) return false;
        if (dataType != null ? !dataType.equals(that.dataType) : that.dataType != null) return false;
        if (timestap != null ? !timestap.equals(that.timestap) : that.timestap != null) return false;
        if (sensorId != null ? !sensorId.equals(that.sensorId) : that.sensorId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = recordId;
        result = 31 * result + (record != null ? record.hashCode() : 0);
        result = 31 * result + (dataType != null ? dataType.hashCode() : 0);
        result = 31 * result + (timestap != null ? timestap.hashCode() : 0);
        result = 31 * result + (sensorId != null ? sensorId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DataRecordEntity{" +
                "recordId=" + recordId +
                ", record='" + record + '\'' +
                ", dataType='" + dataType + '\'' +
                ", timestap=" + timestap +
                ", sensorId=" + sensorId +
                '}';
    }
}
