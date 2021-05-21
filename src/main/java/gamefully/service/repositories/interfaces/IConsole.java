package gamefully.service.repositories.interfaces;

import gamefully.service.models.Console;
import gamefully.service.repositories.HibernateException;

import java.util.List;

public interface IConsole
{
    Console getConsole(int id) throws HibernateException;
    List<Console> getConsoles(String searchTerm) throws HibernateException;
    void createConsole(Console console) throws HibernateException;
    boolean updateConsole(Console console) throws HibernateException;
    List<String> getColumns(String columm) throws HibernateException;
}
