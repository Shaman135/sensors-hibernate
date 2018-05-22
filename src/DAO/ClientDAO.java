package DAO;

import database.entities.ClientEntity;
import database.HibernateSingleton;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ClientDAO implements InterfaceDao <ClientEntity, Integer>{

    private Session currentSession;
    private Transaction currentTransaction;

    public ClientDAO() {
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
    public void create(ClientEntity entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(ClientEntity entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public void delete(ClientEntity entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public void deleteAll() {
        List <ClientEntity> entityList = findAll();
        for(ClientEntity entity : entityList){
            delete(entity);
        }
    }

    @Override
    public ClientEntity findById(Integer id) {
        ClientEntity clientEntity =  (ClientEntity) getCurrentSession().get(ClientEntity.class, id);
        return clientEntity;
    }

    @Override
    public List<ClientEntity> findAll() {
        List <ClientEntity> entityList = (List<ClientEntity>) getCurrentSession().createQuery("from ClientEntity").list();
        return entityList;
    }
}
