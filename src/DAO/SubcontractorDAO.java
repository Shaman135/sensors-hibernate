package DAO;

import database.HibernateSingleton;
import database.entities.SubcontractorEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class SubcontractorDAO implements InterfaceDao<SubcontractorEntity, Integer>{

    private Session currentSession;
    private Transaction currentTransaction;

    public SubcontractorDAO(){}

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
    public void create(SubcontractorEntity entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(SubcontractorEntity entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public void delete(SubcontractorEntity entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public void deleteAll() {
        List<SubcontractorEntity> entityList = findAll();
        for(SubcontractorEntity entity : entityList){
            delete(entity);
        }
    }

    @Override
    public SubcontractorEntity findById(Integer id) {
        SubcontractorEntity subcontractorEntity = getCurrentSession().get(SubcontractorEntity.class, id);
        return subcontractorEntity;
    }

    @Override
    public List<SubcontractorEntity> findAll() {
        List<SubcontractorEntity> entityList = (List<SubcontractorEntity>) getCurrentSession().createQuery("from SubcontractorEntity ").list();
        return entityList;
    }
}
