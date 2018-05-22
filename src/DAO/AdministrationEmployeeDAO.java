package DAO;

import database.entities.AdministrationEmployeeEntity;
import database.HibernateSingleton;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AdministrationEmployeeDAO implements InterfaceDao<AdministrationEmployeeEntity, Integer> {

    private Session currentSession;
    private Transaction currentTransaction;

    public AdministrationEmployeeDAO() {
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
    public void create(AdministrationEmployeeEntity entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(AdministrationEmployeeEntity entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public void delete(AdministrationEmployeeEntity entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public void deleteAll() {
        List <AdministrationEmployeeEntity> entityList = findAll();
        for(AdministrationEmployeeEntity entity : entityList){
            delete(entity);
        }
    }

    @Override
    public AdministrationEmployeeEntity findById(Integer id) {
        AdministrationEmployeeEntity administrationEmployeeEntity = (AdministrationEmployeeEntity) getCurrentSession().get(AdministrationEmployeeEntity.class, id);
        return administrationEmployeeEntity;
    }

    @Override
    public List<AdministrationEmployeeEntity> findAll() {
        List <AdministrationEmployeeEntity> enitityList = (List<AdministrationEmployeeEntity>) getCurrentSession().createQuery("from AdministrationEmployeeEntity").list();
        return enitityList;
    }
}
