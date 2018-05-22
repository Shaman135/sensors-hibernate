package services;

import DAO.SubcontractorDAO;
import database.entities.SubcontractorEntity;

import java.util.List;

public class SubcontractorService {

    private static SubcontractorDAO subcontractorDAO;

    public SubcontractorService() {
        this.subcontractorDAO = new SubcontractorDAO();
    }

    public void create(SubcontractorEntity entity){
        subcontractorDAO.openCurrentSessionWithTransaction();
        subcontractorDAO.create(entity);
        subcontractorDAO.closeCurrentSessionWithTransaction();
    }

    public void update(SubcontractorEntity entity){
        subcontractorDAO.openCurrentSessionWithTransaction();
        subcontractorDAO.update(entity);
        subcontractorDAO.closeCurrentSessionWithTransaction();
    }

    public SubcontractorEntity findById(Integer id){
        subcontractorDAO.openCurrentSession();
        SubcontractorEntity entity = subcontractorDAO.findById(id);
        subcontractorDAO.closeCurrentSession();
        return entity;
    }

    public void delete(Integer id){
        subcontractorDAO.openCurrentSessionWithTransaction();
        SubcontractorEntity entity = subcontractorDAO.findById(id);
        subcontractorDAO.delete(entity);
        subcontractorDAO.closeCurrentSessionWithTransaction();
    }

    public List<SubcontractorEntity> findAll(){
        subcontractorDAO.openCurrentSession();
        List<SubcontractorEntity> entityList = subcontractorDAO.findAll();
        subcontractorDAO.closeCurrentSession();
        return entityList;
    }

    public void deleteAll(){
        subcontractorDAO.openCurrentSessionWithTransaction();
        subcontractorDAO.deleteAll();
        subcontractorDAO.closeCurrentSessionWithTransaction();
    }

    public static SubcontractorDAO getSubcontractorDAO() {
        return subcontractorDAO;
    }

}
