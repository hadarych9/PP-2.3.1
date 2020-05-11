package CRUD.service;

import CRUD.dao.UserDAO;
import CRUD.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional(rollbackFor = SQLException.class)
public class UserService {

    private UserDAO dao;

    @Autowired
    public void setDao(UserDAO dao) {
        this.dao = dao;
    }

    public void addUser(User user){
        dao.addUser(user);
    }

    public boolean doesUserNotExist(String name){
        return dao.doesUserNotExist(name);
    }

    public List<User> getAllUsers() {
        return (List) dao.getAllUsers();
    }

    public int updateUser(User user){
        return dao.updateUser(user);
    }

    public User getById(Long id){
        return dao.getById(id);
    }

    public User getByName(String name){
        return dao.getByName(name);
    }

    public int deleteUser(Long id){
        return dao.deleteUser(id);
    }

    public void dropTable(){
        dao.dropTable();
    }

    /*public int countRoles(String role){
        return dao.countRoles(role);
    }*/
}
