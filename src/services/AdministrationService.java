package services;

import DAO.AdministrationDAO;
import database.entities.AdministrationEntity;

import java.util.List;

public class AdministrationService {

    private static AdministrationDAO administrationDAO;

    public AdministrationService() {
        this.administrationDAO = new AdministrationDAO();
    }

    public void create(AdministrationEntity entity){
        administrationDAO.openCurrentSessionWithTransaction();
        administrationDAO.create(entity);
        administrationDAO.closeCurrentSessionWithTransaction();
    }

    public void update(AdministrationEntity entity){
        administrationDAO.openCurrentSessionWithTransaction();
        administrationDAO.update(entity);
        administrationDAO.closeCurrentSessionWithTransaction();
    }

    public AdministrationEntity findById(Integer id){
        administrationDAO.openCurrentSession();
        AdministrationEntity entity = administrationDAO.findById(id);
        administrationDAO.closeCurrentSession();
        return entity;
    }

    public void delete(Integer id){
        administrationDAO.openCurrentSessionWithTransaction();
        AdministrationEntity entity = administrationDAO.findById(id);
        administrationDAO.delete(entity);
        administrationDAO.closeCurrentSessionWithTransaction();
    }

    public List<AdministrationEntity> findAll(){
        administrationDAO.openCurrentSession();
        List<AdministrationEntity> entityList = administrationDAO.findAll();
        administrationDAO.closeCurrentSession();
        return entityList;
    }

    public void deleteAll(){
        administrationDAO.openCurrentSessionWithTransaction();
        administrationDAO.deleteAll();
        administrationDAO.closeCurrentSessionWithTransaction();
    }

    public static AdministrationDAO getAdministrationDAO() {
        return administrationDAO;
    }

}
