package services;

import DAO.SensorDAO;
import database.entities.SensorEntity;

import java.util.List;

public class SensorService {

    private static SensorDAO sensorDAO;

    public SensorService() {
        this.sensorDAO = new SensorDAO();
    }

    public void create(SensorEntity entity){
        sensorDAO.openCurrentSessionWithTransaction();
        sensorDAO.create(entity);
        sensorDAO.closeCurrentSessionWithTransaction();
    }

    public void update(SensorEntity entity){
        sensorDAO.openCurrentSessionWithTransaction();
        sensorDAO.update(entity);
        sensorDAO.closeCurrentSessionWithTransaction();
    }

    public SensorEntity findById(Integer id){
        sensorDAO.openCurrentSession();
        SensorEntity entity = sensorDAO.findById(id);
        sensorDAO.closeCurrentSession();
        return entity;
    }

    public void delete(Integer id){
        sensorDAO.openCurrentSessionWithTransaction();
        SensorEntity entity = sensorDAO.findById(id);
        sensorDAO.delete(entity);
        sensorDAO.closeCurrentSessionWithTransaction();
    }

    public List<SensorEntity> findAll(){
        sensorDAO.openCurrentSession();
        List<SensorEntity> entityList = sensorDAO.findAll();
        sensorDAO.closeCurrentSession();
        return entityList;
    }

    public void deleteAll(){
        sensorDAO.openCurrentSessionWithTransaction();
        sensorDAO.deleteAll();
        sensorDAO.closeCurrentSessionWithTransaction();
    }

    public static SensorDAO getSensorDAO() {
        return sensorDAO;
    }

}
