package gamefully.service.repositories.interfaces;

import gamefully.service.models.User;
import gamefully.service.repositories.HibernateException;

import java.util.List;

public interface IUser
{
    List<User> getUsers() throws HibernateException;
    User getUser(int id) throws HibernateException;
    User getUser(String mail) throws HibernateException;
    List<User> getUsers(String searchTerm, String column) throws HibernateException;
    void createUser(User user) throws HibernateException;
    void deleteUser(int id) throws HibernateException;
    boolean updateUser(User user) throws HibernateException;
    String getRole(String mail) throws HibernateException;
    int getLatestUserId() throws HibernateException;

}
