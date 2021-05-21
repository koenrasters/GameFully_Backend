package gamefully.service.repositories.interfaces;

import gamefully.service.models.Game;
import gamefully.service.models.GenreModel;
import gamefully.service.repositories.HibernateException;

import java.util.List;

public interface IGame
{
    Game getGame(int id) throws HibernateException;
    List<Game> getGames(String searchTerm) throws HibernateException;
    void createGame(Game game) throws HibernateException;
    boolean updateGame(Game game) throws HibernateException;
    List<GenreModel> getGenres() throws HibernateException;
    List<String> getColumns(String columm) throws HibernateException;
}
