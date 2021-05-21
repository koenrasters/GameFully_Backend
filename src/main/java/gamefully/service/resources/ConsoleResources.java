package gamefully.service.resources;

import gamefully.service.managers.DataAccess;
import gamefully.service.managers.ProductsManager;
import gamefully.service.models.Console;
import gamefully.service.models.DistinctColumn;
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

@Path("/consoles")
public class ConsoleResources
{
    @Context
    private UriInfo uriInfo;
    private static final ProductsManager productsManager = new ProductsManager(new DataAccess("DB"));

    @GET //Get console with id
    @PermitAll
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConsole(@PathParam("id") int id)
    {
        Product console = productsManager.getConsole(id);
        if(console == null)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("There is no console with this id").build();
        }
        else
        {
            return Response.ok(console).build();
        }
    }

    @GET //Get accessory with id
    @PermitAll
    @Path("/distinct")
    @Produces(MediaType.APPLICATION_JSON)
    public void asyncGetDistinctConsole(@Suspended final AsyncResponse asyncResponse)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = getDistinctConsole();
                asyncResponse.resume(response);
            }
            private Response getDistinctConsole()
            {
                List<DistinctColumn> distinctColumns = productsManager.getColumns("console");
                if(distinctColumns == null)
                {
                    return Response.status(Response.Status.BAD_REQUEST).entity("There is no console with this id").build();
                }
                else
                {
                    return Response.ok(distinctColumns).build();
                }
            }
        }).start();
    }

    @PermitAll
    @GET //GET at http://localhost:XXXX/?
    @Produces(MediaType.APPLICATION_JSON)
    public void asyncGetAllConsoles(@Suspended final AsyncResponse asyncResponse, @Context UriInfo info)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = getAllConsoles(info);
                asyncResponse.resume(response);
            }
            private Response getAllConsoles(@Context UriInfo info) {
                List<Console> consoles;
                String query = info.getRequestUri().getQuery();
                consoles = productsManager.getConsoles(query);
                if (consoles == null){
                    return Response.status(Response.Status.BAD_REQUEST).entity("Please provide a valid query").build();
                } else {
                    return Response.ok(consoles).build();
                }
            }
        }).start();
    }

    @DELETE //DELETE at http://localhost:XXXX/gamefully/consoles/3
    @RolesAllowed("ADMIN")
    @Path("{id}")
    public void asyncDelConsole(@Suspended final AsyncResponse asyncResponse, @PathParam("id") int nr)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = deleteConsole(nr);
                asyncResponse.resume(response);
            }
            private Response deleteConsole(@PathParam("id") int nr) {
                productsManager.deleteProduct(nr);
                return Response.noContent().build();
            }
        }).start();
    }

    @POST //POST at http://localhost:XXXX/gamefully/consoles
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    public void asyncCreateConsole(@Suspended final AsyncResponse asyncResponse, Console console)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = createConsole(console);
                asyncResponse.resume(response);
            }
            private Response createConsole(Console console)
            {
                if (!productsManager.addConsole(console)){
                    String entity =  "Console with number " + console.getId() + " already exists.";
                    return Response.status(Response.Status.CONFLICT).entity(entity).build();
                } else {
                    String url = uriInfo.getAbsolutePath() + "/" + console.getId();
                    URI uri = URI.create(url);
                    return Response.created(uri).build();
                }
            }
        }).start();
    }


    @PUT //PUT at http://localhost:XXXX/gamefully/consoles
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public void asyncUpdateConsole(@Suspended final AsyncResponse asyncResponse, Console console, @PathParam("id") int id)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                console.setId(id);
                Response response = updateConsole(console);
                asyncResponse.resume(response);
            }
            private Response updateConsole(Console console) {
                if (productsManager.updateConsole(console)) {
                    return Response.noContent().build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Please provide a valid id.").build();
                }
            }
        }).start();
    }


}
