package CRUD.dao;

import CRUD.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class UserDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public boolean doesUserNotExist(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("FROM User WHERE name = :name");
        query.setParameter("name", name);
        return query.list().isEmpty();
    }

    public void addUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    public User getById(Long id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    public User getByName(String name) {
        User user = null;
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("FROM User WHERE name = :name");
        query.setParameter("name", name);
        if(!query.list().isEmpty()) user = query.list().get(0);
        return user;
    }

    public int deleteUser(Long id) {
        User user = getById(id);
        int x;
        if(user != null){
            sessionFactory.getCurrentSession().delete(user);
            x = 1;
        } else x = 0;
        return x;
    }

    public int updateUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("UPDATE User SET name = :name , password = :password , age = :age WHERE id = :id");
        query.setParameter("id", user.getId());
        query.setParameter("name", user.getName());
        query.setParameter("password", user.getPassword());
        query.setParameter("age", user.getAge());
        query.executeUpdate();
        return 0;
    }

    public Collection getAllUsers() {
        return sessionFactory.getCurrentSession().createQuery("FROM User").list();
    }

    public void dropTable() {
        sessionFactory.getCurrentSession().createQuery("DELETE FROM User").executeUpdate();
    }

    /*public int countRoles(String role) {
        int result = 0;
        Session session = sessionFactory.openSession();
        Query<User> query = session.createQuery("FROM User WHERE role = :role");
        query.setParameter("role", role);
        result = query.list().size();
        session.close();
        return result;
    }*/
}
