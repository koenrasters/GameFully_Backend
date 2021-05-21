package gamefully.service.resources;

import gamefully.service.managers.DataAccess;
import gamefully.service.managers.ProductsManager;
import gamefully.service.models.DistinctColumn;
import gamefully.service.models.Game;
import gamefully.service.models.GenreModel;
import gamefully.service.models.Product;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("/games")
public class GamesResources
{
    @Context
    private UriInfo uriInfo;
    private static final ProductsManager productsManager = new ProductsManager(new DataAccess("DB"));

    @GET //Get game with id
    @PermitAll
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void asyncGetGames(@Suspended final AsyncResponse asyncResponse, @PathParam("id") int id)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = getGames(id);
                asyncResponse.resume(response);
            }
            private Response getGames(@PathParam("id") int id)
            {
                Product game = productsManager.getGame(id);
                if(game == null)
                {
                    return Response.status(Response.Status.BAD_REQUEST).entity("There is no game with this id").build();
                }
                else
                {
                    return Response.ok(game).build();
                }
            }
        }).start();
    }

    @GET //Get accessory with id
    @PermitAll
    @Path("/distinct")
    @Produces(MediaType.APPLICATION_JSON)
    public void asyncGetDistinctGame(@Suspended final AsyncResponse asyncResponse)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = getDistinctGame();
                asyncResponse.resume(response);
            }
            private Response getDistinctGame()
            {
                List<DistinctColumn> distinctColumns = productsManager.getColumns("game");
                if(distinctColumns == null)
                {
                    return Response.status(Response.Status.BAD_REQUEST).entity("There is no column").build();
                }
                else
                {
                    return Response.ok(distinctColumns).build();
                }
            }
        }).start();
    }

    @GET //Get game with id
    @PermitAll
    @Path("/genres")
    @Produces(MediaType.APPLICATION_JSON)
    public void asyncGetGenres(@Suspended final AsyncResponse asyncResponse)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = getGenres();
                asyncResponse.resume(response);
            }
            private Response getGenres()
            {
                List<GenreModel> genres = productsManager.getGenres();
                if(genres == null)
                {
                    return Response.status(Response.Status.BAD_REQUEST).entity("There is no genre").build();
                }
                else
                {
                    return Response.ok(genres).build();
                }
            }
        }).start();
    }


    @PermitAll
    @GET //GET at http://localhost:XXXX/?
    @Produces(MediaType.APPLICATION_JSON)
    public void asyncGetAllGames(@Suspended final AsyncResponse asyncResponse, @Context UriInfo info)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = getAllGames(info);
                asyncResponse.resume(response);
            }
            private Response getAllGames(@Context UriInfo info) {
                List<Game> games;
                String query = info.getRequestUri().getQuery();
                games = productsManager.getGames(query);
                if (games == null){
                    return Response.status(Response.Status.BAD_REQUEST).entity("Please provide a valid query").build();
                } else {
                    return Response.ok(games).build();
                }
            }
        }).start();
    }


    @DELETE //DELETE at http://localhost:XXXX/gamefully/games/3
    @RolesAllowed("ADMIN")
    @Path("{id}")
    public void asyncDelGame(@Suspended final AsyncResponse asyncResponse, @PathParam("id") int nr)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = deleteGame(nr);
                asyncResponse.resume(response);
            }
            private Response deleteGame(@PathParam("id") int nr) {
                productsManager.deleteProduct(nr);
                return Response.noContent().build();
            }
        }).start();
    }

    @POST //POST at http://localhost:XXXX/gamefully/games
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    public void asyncCreateGame(@Suspended final AsyncResponse asyncResponse, Game game)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = createGame(game);
                asyncResponse.resume(response);
            }
            private Response createGame(Game game)
            {
                if (!productsManager.addGame(game)){
                    String entity =  "Game with number " + game.getId() + " already exists.";
                    return Response.status(Response.Status.CONFLICT).entity(entity).build();
                } else {
                    String url = uriInfo.getAbsolutePath() + "/" + game.getId();
                    URI uri = URI.create(url);
                    return Response.created(uri).build();
                }
            }
        }).start();
    }


    @PUT //PUT at http://localhost:XXXX/gamefully/games/3
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public void asyncUpdateGame(@Suspended final AsyncResponse asyncResponse, Game game, @PathParam("id") int id)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                game.setId(id);
                Response response = updateGame(game);
                asyncResponse.resume(response);
            }
            private Response updateGame(Game game) {
                if (productsManager.updateGame(game)) {
                    return Response.noContent().build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Please provide a valid id.").build();
                }
            }
        }).start();
    }


}
