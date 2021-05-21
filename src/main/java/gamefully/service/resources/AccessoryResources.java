package gamefully.service.resources;

import gamefully.service.managers.DataAccess;
import gamefully.service.managers.ProductsManager;
import gamefully.service.models.Accessory;
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

@Path("/accessories")
public class AccessoryResources
{
    @Context
    private UriInfo uriInfo;
    private static final ProductsManager productsManager = new ProductsManager(new DataAccess("DB"));

    @GET //Get accessory with id
    @PermitAll
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void asyncGetAccessory(@Suspended final AsyncResponse asyncResponse, @PathParam("id") int id)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = getAccessory(id);
                asyncResponse.resume(response);
            }
            private Response getAccessory(@PathParam("id") int id)
            {
                Product accessory = productsManager.getAccessory(id);
                if(accessory == null)
                {
                    return Response.status(Response.Status.BAD_REQUEST).entity("There is no console with this id").build();
                }
                else
                {
                    return Response.ok(accessory).build();
                }
            }
        }).start();
    }

    @GET //Get accessory with id
    @PermitAll
    @Path("/distinct")
    @Produces(MediaType.APPLICATION_JSON)
    public void asyncGetDistinctAccessory(@Suspended final AsyncResponse asyncResponse)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = getDistinctAccessory();
                asyncResponse.resume(response);
            }
            private Response getDistinctAccessory()
            {
                List<DistinctColumn> distinctColumns = productsManager.getColumns("accessory");
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
    public void asyncGetAllAccessories(@Suspended final AsyncResponse asyncResponse, @Context UriInfo info)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = getAllAccessories(info);
                asyncResponse.resume(response);
            }
            private Response getAllAccessories(@Context UriInfo info) {
                List<Accessory> accessories;
                String query = info.getRequestUri().getQuery();
                accessories = productsManager.getAccessories(query);
                if (accessories == null){
                    return Response.status(Response.Status.BAD_REQUEST).entity("Please provide a valid query").build();
                } else {
                    return Response.ok(accessories).build();
                }
            }
        }).start();
    }

    @DELETE //DELETE at http://localhost:XXXX/gamefully/accessories/3
    @RolesAllowed("ADMIN")
    @Path("{id}")
    public void asyncDelAccessory(@Suspended final AsyncResponse asyncResponse, @PathParam("id") int nr)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = deleteAccessory(nr);
                asyncResponse.resume(response);
            }
            private Response deleteAccessory(@PathParam("id") int nr) {
                productsManager.deleteProduct(nr);
                return Response.noContent().build();
            }
        }).start();
    }

    @POST //POST at http://localhost:XXXX/gamefully/accessories
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    public void asyncCreateAccessory(@Suspended final AsyncResponse asyncResponse, Accessory accessory)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = createAccessory(accessory);
                asyncResponse.resume(response);
            }
            private Response createAccessory(Accessory accessory)
            {
                if (!productsManager.addAccessory(accessory)){
                    String entity =  "Accessory with number " + accessory.getId() + " already exists.";
                    return Response.status(Response.Status.CONFLICT).entity(entity).build();
                } else {
                    String url = uriInfo.getAbsolutePath() + "/" + accessory.getId();
                    URI uri = URI.create(url);
                    return Response.created(uri).build();
                }
            }
        }).start();
    }


    @PUT //PUT at http://localhost:XXXX/gamefully/accesssories
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public void asyncUpdateAccessory(@Suspended final AsyncResponse asyncResponse, Accessory accessory, @PathParam("id") int id)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                accessory.setId(id);
                Response response = updateAccessory(accessory);
                asyncResponse.resume(response);
            }
            private Response updateAccessory(Accessory accessory) {
                if (productsManager.updateAccessory(accessory)) {
                    return Response.noContent().build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Please provide a valid id.").build();
                }
            }
        }).start();
    }


}
