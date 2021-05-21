package gamefully.service.repositories;

import gamefully.service.models.User;
import gamefully.service.repositories.interfaces.IUser;

import java.util.List;

public class UserRepositoryMem implements IUser
{
    private final FakeDataStore fakeDataStore = new FakeDataStore();

    public List<User> getUsers()
    {
        return fakeDataStore.getUserList();
    }

    public User getUser(int id)
    {
        return fakeDataStore.getUser(id);
    }

    public User getUser(String mail)
    {
        return fakeDataStore.getUser(mail);
    }

    public List<User> getUsers(String searchTerm, String column)
    {
        return fakeDataStore.getUserList();
    }

    public void createUser(User user)
    {
        fakeDataStore.addUser(user);
    }

    public void deleteUser(int id)
    {
        fakeDataStore.deleteUser(id);
    }

    public boolean updateUser(User user)
    {
        return fakeDataStore.updateUser(user);
    }

    public String getRole(String mail)
    {
        User user = this.getUser(mail);
        if(user.getAdmin()==1)
        {
            return "ADMIN";
        }
        else
        {
            return "USER";
        }
    }

    public int getLatestUserId() throws HibernateException {
        return 0;
    }
}
