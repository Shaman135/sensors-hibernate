package services;

import DAO.DataRecordDAO;
import database.entities.DataRecordEntity;

import java.util.List;

public class DataRecordService {

    private static DataRecordDAO dataRecordDAO;

    public DataRecordService() {
        this.dataRecordDAO = new DataRecordDAO();
    }

    public void create(DataRecordEntity entity){
        dataRecordDAO.openCurrentSessionWithTransaction();
        dataRecordDAO.create(entity);
        dataRecordDAO.closeCurrentSessionWithTransaction();
    }

    public void update(DataRecordEntity entity){
        dataRecordDAO.openCurrentSessionWithTransaction();
        dataRecordDAO.update(entity);
        dataRecordDAO.closeCurrentSessionWithTransaction();
    }

    public DataRecordEntity findById(Integer id){
        dataRecordDAO.openCurrentSession();
        DataRecordEntity entity = dataRecordDAO.findById(id);
        dataRecordDAO.closeCurrentSession();
        return entity;
    }

    public void delete(Integer id){
        dataRecordDAO.openCurrentSessionWithTransaction();
        DataRecordEntity entity = dataRecordDAO.findById(id);
        dataRecordDAO.delete(entity);
        dataRecordDAO.closeCurrentSessionWithTransaction();
    }

    public List<DataRecordEntity> findAll(){
        dataRecordDAO.openCurrentSession();
        List<DataRecordEntity> entityList = dataRecordDAO.findAll();
        dataRecordDAO.closeCurrentSession();
        return entityList;
    }

    public void deleteAll(){
        dataRecordDAO.openCurrentSessionWithTransaction();
        dataRecordDAO.deleteAll();
        dataRecordDAO.closeCurrentSessionWithTransaction();
    }

    public static DataRecordDAO getDataRecordDAO() {
        return dataRecordDAO;
    }

}
