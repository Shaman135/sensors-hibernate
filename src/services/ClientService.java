package services;

import DAO.ClientDAO;
import database.entities.ClientEntity;

import java.util.List;

public class ClientService {

    private static ClientDAO clientDAO;

    public ClientService() {
        this.clientDAO = new ClientDAO();
    }

    public void create(ClientEntity entity){
        clientDAO.openCurrentSessionWithTransaction();
        clientDAO.create(entity);
        clientDAO.closeCurrentSessionWithTransaction();
    }

    public void update(ClientEntity entity){
        clientDAO.openCurrentSessionWithTransaction();
        clientDAO.update(entity);
        clientDAO.closeCurrentSessionWithTransaction();
    }

    public ClientEntity findById(Integer id){
        clientDAO.openCurrentSession();
        ClientEntity entity = clientDAO.findById(id);
        clientDAO.closeCurrentSession();
        return entity;
    }

    public void delete(Integer id){
        clientDAO.openCurrentSessionWithTransaction();
        ClientEntity entity = clientDAO.findById(id);
        clientDAO.delete(entity);
        clientDAO.closeCurrentSessionWithTransaction();
    }

    public List<ClientEntity> findAll(){
        clientDAO.openCurrentSession();
        List<ClientEntity> entityList = clientDAO.findAll();
        clientDAO.closeCurrentSession();
        return entityList;
    }

    public void deleteAll(){
        clientDAO.openCurrentSessionWithTransaction();
        clientDAO.deleteAll();
        clientDAO.closeCurrentSessionWithTransaction();
    }

    public static ClientDAO getClientDAO() {
        return clientDAO;
    }
}
