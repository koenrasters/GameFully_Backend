package gamefully.service.repositories;

import gamefully.service.models.Game;
import gamefully.service.models.GenreModel;
import gamefully.service.repositories.interfaces.IGame;

import java.util.ArrayList;
import java.util.List;

public class GameRepositoryMem implements IGame
{
    private final FakeDataStore fakeDataStore = new FakeDataStore();

    public List<Game> getGames()
    {
        return fakeDataStore.getGames();
    }

    public Game getGame(int id)
    {
        return (Game) fakeDataStore.getProduct(id);
    }

    public List<Game> getGames(String searchTerm)
    {
        if(searchTerm.equals(" order by UpdatedAt asc"))
        {
            return fakeDataStore.getGames();
        }
        else
        {
            List<Game> games = new ArrayList<>();
            Game cod = (Game) fakeDataStore.getProduct(4);
            games.add(cod);
            return games;
        }

    }

    public List<GenreModel> getGenres() throws HibernateException
    {
        return new ArrayList<>();
    }

    public void createGame(Game game)
    {
        fakeDataStore.addProduct(game);
    }

    public boolean updateGame(Game game)
    {
        fakeDataStore.updateProduct(game);
        return true;
    }

    public List<String> getColumns(String columm) throws HibernateException
    {
        return new ArrayList<>();
    }
}
