package DAO;

import database.entities.AdministrationEntity;
import database.HibernateSingleton;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AdministrationDAO implements InterfaceDao<AdministrationEntity, Integer> {

    private Session currentSession;
    private Transaction currentTransaction;

    public AdministrationDAO() {
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
    public void create(AdministrationEntity entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(AdministrationEntity entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public void delete(AdministrationEntity entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public void deleteAll() {
        List<AdministrationEntity> entityList = findAll();
        for(AdministrationEntity entity : entityList){
            delete(entity);
        }
    }

    @Override
    public AdministrationEntity findById(Integer id) {
        AdministrationEntity administrationEntity = (AdministrationEntity) getCurrentSession().get(AdministrationEntity.class, id);
        return administrationEntity;
    }

    @Override
    public List<AdministrationEntity> findAll() {
        List<AdministrationEntity> entityList = (List<AdministrationEntity>)getCurrentSession().createQuery("from AdministrationEntity").list();
        return entityList;
    }
}
