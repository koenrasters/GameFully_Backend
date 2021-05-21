package gamefully.service.repositories;

import gamefully.service.models.Console;
import gamefully.service.repositories.interfaces.IConsole;

import java.util.ArrayList;
import java.util.List;

public class ConsoleRepositoryMem implements IConsole
{
    private final FakeDataStore fakeDataStore = new FakeDataStore();

    public List<Console> getConsoles()
    {
        return fakeDataStore.getConsoles();
    }

    public Console getConsole(int id)
    {
        return (Console) fakeDataStore.getProduct(id);
    }

    public List<Console> getConsoles(String searchTerm)
    {
        if(searchTerm.equals(" order by UpdatedAt asc"))
        {
            return fakeDataStore.getConsoles();
        }
        else
        {
            List<Console> consoles = new ArrayList<>();
            Console playstation4 = (Console) fakeDataStore.getProduct(1);
            consoles.add(playstation4);
            return consoles;
        }

    }

    public void createConsole(Console console)
    {
        fakeDataStore.addProduct(console);
    }

    public boolean updateConsole(Console console)
    {
        fakeDataStore.updateProduct(console);
        return true;
    }

    public List<String> getColumns(String columm) throws HibernateException
    {
        return new ArrayList<>();
    }
}
