package DAO;

import database.HibernateSingleton;
import database.entities.SensorEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class SensorDAO implements InterfaceDao<SensorEntity, Integer>{

    private Session currentSession;
    private Transaction currentTransaction;

    public SensorDAO() {
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
    public void create(SensorEntity entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(SensorEntity entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public void delete(SensorEntity entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public void deleteAll() {
        List<SensorEntity> entityList = findAll();
        for(SensorEntity entity : entityList){
            delete(entity);
        }
    }

    @Override
    public SensorEntity findById(Integer id) {
        SensorEntity sensorEntity = getCurrentSession().get(SensorEntity.class, id);
        return sensorEntity;
    }

    @Override
    public List<SensorEntity> findAll() {
        List<SensorEntity> entityList = (List<SensorEntity>) getCurrentSession().createQuery("from SensorEntity ").list();
        return entityList;
    }
}
