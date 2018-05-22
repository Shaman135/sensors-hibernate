package services;

import DAO.BuildingDAO;
import database.entities.BuildingEntity;

import java.util.List;

public class BuildingService {

    private static BuildingDAO buildingDAO;

    public BuildingService() {
        this.buildingDAO = new BuildingDAO();
    }

    public void create(BuildingEntity entity){
        buildingDAO.openCurrentSessionWithTransaction();
        buildingDAO.create(entity);
        buildingDAO.closeCurrentSessionWithTransaction();
    }

    public void update(BuildingEntity entity){
        buildingDAO.openCurrentSessionWithTransaction();
        buildingDAO.update(entity);
        buildingDAO.closeCurrentSessionWithTransaction();
    }

    public BuildingEntity findById(Integer id){
        buildingDAO.openCurrentSession();
        BuildingEntity entity = buildingDAO.findById(id);
        buildingDAO.closeCurrentSession();
        return entity;
    }

    public void delete(Integer id){
        buildingDAO.openCurrentSessionWithTransaction();
        BuildingEntity entity = buildingDAO.findById(id);
        buildingDAO.delete(entity);
        buildingDAO.closeCurrentSessionWithTransaction();
    }

    public List<BuildingEntity > findAll(){
        buildingDAO.openCurrentSession();
        List<BuildingEntity> entityList = buildingDAO.findAll();
        buildingDAO.closeCurrentSession();
        return entityList;
    }

    public void deleteAll(){
        buildingDAO.openCurrentSessionWithTransaction();
        buildingDAO.deleteAll();
        buildingDAO.closeCurrentSessionWithTransaction();
    }

    public static BuildingDAO getBuildingDAO() {
        return buildingDAO;
    }

}
