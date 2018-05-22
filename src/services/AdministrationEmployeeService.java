package services;

import DAO.AdministrationEmployeeDAO;
import database.entities.AdministrationEmployeeEntity;

import java.util.List;

public class AdministrationEmployeeService {
    private static AdministrationEmployeeDAO administrationEmployeeDAO;

    public AdministrationEmployeeService() {
        this.administrationEmployeeDAO = new AdministrationEmployeeDAO();
    }

    public void create(AdministrationEmployeeEntity entity){
        administrationEmployeeDAO.openCurrentSessionWithTransaction();
        administrationEmployeeDAO.create(entity);
        administrationEmployeeDAO.closeCurrentSessionWithTransaction();
    }

    public void update(AdministrationEmployeeEntity entity){
        administrationEmployeeDAO.openCurrentSessionWithTransaction();
        administrationEmployeeDAO.update(entity);
        administrationEmployeeDAO.closeCurrentSessionWithTransaction();
    }

    public AdministrationEmployeeEntity findById(Integer id){
        administrationEmployeeDAO.openCurrentSession();
        AdministrationEmployeeEntity entity = administrationEmployeeDAO.findById(id);
        administrationEmployeeDAO.closeCurrentSession();
        return entity;
    }

    public void delete(Integer id){
        administrationEmployeeDAO.openCurrentSessionWithTransaction();
        AdministrationEmployeeEntity administrationEmployeeEntity = administrationEmployeeDAO.findById(id);
        administrationEmployeeDAO.delete(administrationEmployeeEntity);
        administrationEmployeeDAO.closeCurrentSessionWithTransaction();
    }

    public List<AdministrationEmployeeEntity> findAll(){
        administrationEmployeeDAO.openCurrentSession();
        List<AdministrationEmployeeEntity> entityList = administrationEmployeeDAO.findAll();
        administrationEmployeeDAO.closeCurrentSession();
        return entityList;
    }

    public void deleteAll(){
        administrationEmployeeDAO.openCurrentSessionWithTransaction();
        administrationEmployeeDAO.deleteAll();
        administrationEmployeeDAO.closeCurrentSessionWithTransaction();
    }

    public static AdministrationEmployeeDAO getAdministrationEmployeeDAO() {
        return administrationEmployeeDAO;
    }
}
