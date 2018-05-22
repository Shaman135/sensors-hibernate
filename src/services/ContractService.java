package services;

import DAO.ContractDAO;
import database.entities.ContractEntity;

import java.util.List;

public class ContractService {

    private static ContractDAO contractDAO;

    public ContractService() {
        this.contractDAO = new ContractDAO();
    }

    public void create(ContractEntity entity){
        contractDAO.openCurrentSessionWithTransaction();
        contractDAO.create(entity);
        contractDAO.closeCurrentSessionWithTransaction();
    }

    public void update(ContractEntity entity){
        contractDAO.openCurrentSessionWithTransaction();
        contractDAO.update(entity);
        contractDAO.closeCurrentSessionWithTransaction();
    }

    public ContractEntity findById(Integer id){
        contractDAO.openCurrentSession();
        ContractEntity entity = contractDAO.findById(id);
        contractDAO.closeCurrentSession();
        return entity;
    }

    public void delete(Integer id){
        contractDAO.openCurrentSessionWithTransaction();
        ContractEntity entity = contractDAO.findById(id);
        contractDAO.delete(entity);
        contractDAO.closeCurrentSessionWithTransaction();
    }

    public List<ContractEntity> findAll(){
        contractDAO.openCurrentSession();
        List<ContractEntity> entityList = contractDAO.findAll();
        contractDAO.closeCurrentSession();
        return entityList;
    }

    public void deleteAll(){
        contractDAO.openCurrentSessionWithTransaction();
        contractDAO.deleteAll();
        contractDAO.closeCurrentSessionWithTransaction();
    }

    public static ContractDAO getContractDAO() {
        return contractDAO;
    }

}
