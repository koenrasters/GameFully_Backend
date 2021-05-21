package gamefully.service.repositories;

import gamefully.service.models.Accessory;
import gamefully.service.repositories.interfaces.IAccessory;

import java.util.ArrayList;
import java.util.List;

public class AccessoryRepositoryMem implements IAccessory
{
    private final FakeDataStore fakeDataStore = new FakeDataStore();

    public List<Accessory> getAccessories()
    {
        return fakeDataStore.getAccessories();
    }

    public Accessory getAccessory(int id)
    {
        return (Accessory) fakeDataStore.getProduct(id);
    }

    public List<Accessory> getAccessories(String searchTerm)
    {
        if(searchTerm.equals(" order by UpdatedAt asc"))
        {
            return fakeDataStore.getAccessories();
        }
        else
        {
            List<Accessory> accessories = new ArrayList<>();
            Accessory ps4controller = (Accessory) fakeDataStore.getProduct(8);
            accessories.add(ps4controller);
            return accessories;
        }
    }

    public void createAccessory(Accessory accessory)
    {
        fakeDataStore.addProduct(accessory);
    }

    public boolean updateAccessory(Accessory accessory)
    {
        fakeDataStore.updateProduct(accessory);
        return true;
    }

    public List<String> getColumns(String columm) throws HibernateException
    {
        return new ArrayList<>();
    }


}
