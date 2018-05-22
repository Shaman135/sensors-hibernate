package DAO;

import database.entities.DataRecordEntity;
import database.HibernateSingleton;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DataRecordDAO implements InterfaceDao<DataRecordEntity, Integer>{

    private Session currentSession;
    private Transaction currentTransaction;

    public DataRecordDAO() {
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
    public void create(DataRecordEntity entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(DataRecordEntity entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public void delete(DataRecordEntity entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public void deleteAll() {
        List<DataRecordEntity> entityList = findAll();
        for(DataRecordEntity entity : entityList){
            delete(entity);
        }
    }

    @Override
    public DataRecordEntity findById(Integer id) {
        DataRecordEntity dataRecordEntity = getCurrentSession().get(DataRecordEntity.class, id);
        return dataRecordEntity;
    }

    @Override
    public List<DataRecordEntity> findAll() {
        List<DataRecordEntity> entityList = (List<DataRecordEntity>) getCurrentSession().createQuery("from DataRecordEntity").list();
        return entityList;
    }
}
