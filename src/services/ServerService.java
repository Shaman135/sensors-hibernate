package services;

import DAO.ServerDAO;
import database.entities.ServerEntity;

import java.util.List;

public class ServerService {

    private static ServerDAO serverDAO;

    public ServerService() {
        this.serverDAO = new ServerDAO();
    }

    public void create(ServerEntity entity){
        serverDAO.openCurrentSessionWithTransaction();
        serverDAO.create(entity);
        serverDAO.closeCurrentSessionWithTransaction();
    }

    public void update(ServerEntity entity){
        serverDAO.openCurrentSessionWithTransaction();
        serverDAO.update(entity);
        serverDAO.closeCurrentSessionWithTransaction();
    }

    public ServerEntity findById(Integer id){
        serverDAO.openCurrentSession();
        ServerEntity entity = serverDAO.findById(id);
        serverDAO.closeCurrentSession();
        return entity;
    }

    public void delete(Integer id){
        serverDAO.openCurrentSessionWithTransaction();
        ServerEntity entity = serverDAO.findById(id);
        serverDAO.delete(entity);
        serverDAO.closeCurrentSessionWithTransaction();
    }

    public List<ServerEntity> findAll(){
        serverDAO.openCurrentSession();
        List<ServerEntity> entityList = serverDAO.findAll();
        serverDAO.closeCurrentSession();
        return entityList;
    }

    public void deleteAll(){
        serverDAO.openCurrentSessionWithTransaction();
        serverDAO.deleteAll();
        serverDAO.closeCurrentSessionWithTransaction();
    }

    public static ServerDAO getServerDAO() {
        return serverDAO;
    }

}
