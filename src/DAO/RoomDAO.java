package DAO;

import database.HibernateSingleton;
import database.entities.RoomEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RoomDAO implements InterfaceDao <RoomEntity, Integer>{

    private Session currentSession;
    private Transaction currentTransaction;

    public RoomDAO() {
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
    public void create(RoomEntity entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(RoomEntity entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public void delete(RoomEntity entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public void deleteAll() {
        List<RoomEntity> entityList = findAll();
        for(RoomEntity entity : entityList){
            delete(entity);
        }
    }

    @Override
    public RoomEntity findById(Integer id) {
        RoomEntity roomEntity = getCurrentSession().get(RoomEntity.class, id);
        return roomEntity;
    }

    @Override
    public List<RoomEntity> findAll() {
        List<RoomEntity> entityList = (List<RoomEntity>) getCurrentSession().createQuery("from RoomEntity").list();
        return entityList;
    }
}
