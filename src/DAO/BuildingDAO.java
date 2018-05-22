package DAO;

import database.entities.BuildingEntity;
import database.HibernateSingleton;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class BuildingDAO implements InterfaceDao<BuildingEntity, Integer>{

    private Session currentSession;
    private Transaction currentTransaction;

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

    public BuildingDAO() {
    }

    @Override
    public void create(BuildingEntity entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(BuildingEntity entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public void delete(BuildingEntity entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public void deleteAll() {
        List<BuildingEntity> entityList = findAll();
        for(BuildingEntity entity : entityList){
            delete(entity);
        }
    }

    @Override
    public BuildingEntity findById(Integer id) {
        BuildingEntity buildingEntity = (BuildingEntity) getCurrentSession().get(BuildingEntity.class, id);
        return buildingEntity;
    }

    @Override
    public List<BuildingEntity> findAll() {
        List<BuildingEntity> entityList = (List<BuildingEntity>) getCurrentSession().createQuery("from BuildingEntity").list();
        return entityList;
    }
}
