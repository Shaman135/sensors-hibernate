package services;

import DAO.RoomDAO;
import database.entities.RoomEntity;

import java.util.List;

public class RoomService {

    private static RoomDAO roomDAO;

    public RoomService() {
        this.roomDAO = new RoomDAO();
    }

    public void create(RoomEntity entity){
        roomDAO.openCurrentSessionWithTransaction();
        roomDAO.create(entity);
        roomDAO.closeCurrentSessionWithTransaction();
    }

    public void update(RoomEntity entity){
        roomDAO.openCurrentSessionWithTransaction();
        roomDAO.update(entity);
        roomDAO.closeCurrentSessionWithTransaction();
    }

    public RoomEntity findById(Integer id){
        roomDAO.openCurrentSession();
        RoomEntity entity = roomDAO.findById(id);
        roomDAO.closeCurrentSession();
        return entity;
    }

    public void delete(Integer id){
        roomDAO.openCurrentSessionWithTransaction();
        RoomEntity entity = roomDAO.findById(id);
        roomDAO.delete(entity);
        roomDAO.closeCurrentSessionWithTransaction();
    }

    public List<RoomEntity> findAll(){
        roomDAO.openCurrentSession();
        List<RoomEntity> entityList = roomDAO.findAll();
        roomDAO.closeCurrentSession();
        return entityList;
    }

    public void deleteAll(){
        roomDAO.openCurrentSessionWithTransaction();
        roomDAO.deleteAll();
        roomDAO.closeCurrentSessionWithTransaction();
    }

    public static RoomDAO getRoomDAO() {
        return roomDAO;
    }

}
