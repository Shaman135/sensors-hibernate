package DAO;

import database.HibernateSingleton;
import database.entities.ServerEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ServerDAO implements InterfaceDao <ServerEntity, Integer>{

    private Session currentSession;
    private Transaction currentTransaction;

    public ServerDAO() {
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
    public void create(ServerEntity entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(ServerEntity entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public void delete(ServerEntity entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public void deleteAll() {
        List<ServerEntity> entityList = findAll();
        for(ServerEntity entity : entityList){
            delete(entity);
        }
    }

    @Override
    public ServerEntity findById(Integer id) {
        ServerEntity serverEntity = getCurrentSession().get(ServerEntity.class, id);
        return serverEntity;
    }

    @Override
    public List<ServerEntity> findAll() {
        List<ServerEntity> entityList = (List<ServerEntity>) getCurrentSession().createQuery("from ServerEntity").list();
        return entityList;
    }
}
