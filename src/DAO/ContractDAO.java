package DAO;

import database.entities.ContractEntity;
import database.HibernateSingleton;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ContractDAO implements InterfaceDao <ContractEntity, Integer>{

    private Session currentSession;
    private Transaction currentTransaction;

    public ContractDAO() {
    }

    public Session openCurrentSession() {
        currentSession = HibernateSingleton.getInstance().openSession();
        return currentSession;
    }

    public Session openCurrentSessionWithTransaction(){
        currentSession = HibernateSingleton.getInstance().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession(){
        currentSession.close();
    }

    public void closeCurrentSessionWithTransaction(){
        currentTransaction.commit();
        currentSession.close();
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction){
        this.currentTransaction = currentTransaction;
    }

    @Override
    public void create(ContractEntity entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(ContractEntity entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public void delete(ContractEntity entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public void deleteAll() {
        List<ContractEntity> entityList = findAll();
        for(ContractEntity entity : entityList){
            delete(entity);
        }
    }

    @Override
    public ContractEntity findById(Integer id) {
        ContractEntity contractEntity = (ContractEntity) getCurrentSession().get(ContractEntity.class, id);
        return contractEntity;
    }

    @Override
    public List<ContractEntity> findAll() {
        List<ContractEntity> entityList = (List<ContractEntity>) getCurrentSession().createQuery("from ContractEntity").list();
        return entityList;
    }
}
