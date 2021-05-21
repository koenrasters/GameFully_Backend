package gamefully.service.repositories.interfaces;

import gamefully.service.models.Accessory;
import gamefully.service.repositories.HibernateException;

import java.util.List;

public interface IAccessory
{
    Accessory getAccessory(int id) throws HibernateException;
    List<Accessory> getAccessories(String searchTerm) throws HibernateException;
    void createAccessory(Accessory accessory) throws HibernateException;
    boolean updateAccessory(Accessory accessory) throws HibernateException;
    List<String> getColumns(String columm) throws HibernateException;
}
