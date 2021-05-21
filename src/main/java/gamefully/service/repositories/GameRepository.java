package gamefully.service.repositories;

import gamefully.service.models.Game;
import gamefully.service.models.GenreModel;
import gamefully.service.repositories.interfaces.IGame;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class GameRepository implements IGame
{
    public List<Game> getGames() throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();

            List<Game> result = session.createQuery("from Game ", Game.class).list();
            for(Game game : result)
            {
                Hibernate.initialize(game.getGenres());
                Hibernate.initialize(game.getWishlists());
                Hibernate.initialize(game.getCartItems());
                Hibernate.initialize(game.getTransactionItems());
                Hibernate.initialize(game.getStock());
            }

            session.getTransaction().commit();

            return result;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read game from database",e);
        }
        finally
        {
            session.close();
        }
    }

    public Game getGame(int id) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            Game game = session.get(Game.class, id);
            Hibernate.initialize(game.getGenres());
            Hibernate.initialize(game.getWishlists());
            Hibernate.initialize(game.getCartItems());
            Hibernate.initialize(game.getTransactionItems());
            Hibernate.initialize(game.getStock());
            session.getTransaction().commit();
            return game;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read game with id " + id, e);
        }
        finally
        {
            session.close();
        }
    }

    public List<Game> getGames(String searchTerm) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            Query<Game> query = session.createQuery("from Game " + searchTerm, Game.class);
            List<Game> games = query.list();
            for(Game game : games)
            {
                Hibernate.initialize(game.getGenres());
                Hibernate.initialize(game.getWishlists());
                Hibernate.initialize(game.getCartItems());
                Hibernate.initialize(game.getTransactionItems());
                Hibernate.initialize(game.getStock());
            }

            session.getTransaction().commit();
            return games;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read console from database with search " + searchTerm, e);
        }
        finally
        {
            session.close();
        }
    }

    public List<GenreModel> getGenres() throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            Query<GenreModel> query = session.createQuery("from GenreModel " , GenreModel.class);
            List<GenreModel> genres = query.list();
            session.getTransaction().commit();
            return genres;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read Genre from database", e);
        }
        finally
        {
            session.close();
        }
    }

    public int getLatestGameId() throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();

            List<Integer> gameIdList = session.createQuery("select max(id) from Game ").list();
            if(gameIdList.get(0) == null)
            {
                gameIdList.set(0,0);
            }
            int gameId = gameIdList.get(0) + 1;
            session.getTransaction().commit();

            return gameId;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read game id from database",e);
        }
        finally
        {
            session.close();
        }
    }

    public void createGame(Game game) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            session.save(game);

            session.getTransaction().commit();

        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot create game " + game,e);
        }
        finally
        {
            session.close();
        }
    }

    public boolean updateGame(Game game) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            session.update(game);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot update game", e);
        }
        finally
        {
            session.close();
        }
    }

    public List<String> getColumns(String columm) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            String gameQuery = " from game Inner join product on game.id = product.id";
            session.beginTransaction();
            Query<String> query = session.createSQLQuery("select distinct " + columm + gameQuery);
            List<String> distinctList = query.list();
            session.getTransaction().commit();
            return distinctList;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cant get columns", e);
        }
        finally
        {
            session.close();
        }
    }

}
