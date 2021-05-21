package gamefully.service.resources;

import gamefully.service.managers.DataAccess;
import gamefully.service.managers.ProductsManager;
import gamefully.service.models.Stock;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/stock")
public class StockResources
{
    @Context
    private UriInfo uriInfo;
    private static final ProductsManager productsManager = new ProductsManager(new DataAccess("DB"));

    @GET //Get product with id
    @RolesAllowed({"USER", "ADMIN"})
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void asyncGetStock(@Suspended final AsyncResponse asyncResponse, @PathParam("id") int id)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = getStock(id);
                asyncResponse.resume(response);
            }
            private Response getStock(@PathParam("id") int id)
            {
                Stock stock = productsManager.getStock(id);
                if(stock == null)
                {
                    return Response.status(Response.Status.BAD_REQUEST).entity("This is not a valid stock number").build();
                }
                else
                {
                    return Response.ok(stock).build();
                }
            }
        }).start();
    }

    @GET //Get product with id
    @RolesAllowed({"USER", "ADMIN"})
    @Path("/quantity/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void asyncGetQuantity(@Suspended final AsyncResponse asyncResponse, @PathParam("id") int id)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = getStockQuantity(id);
                asyncResponse.resume(response);
            }
            private Response getStockQuantity(@PathParam("id") int id)
            {
                int quantity = productsManager.getStockQuantity(id);
                if(quantity == -1)
                {
                    return Response.status(Response.Status.BAD_REQUEST).entity("This is not a valid number to get quantity").build();
                }
                else
                {
                    return Response.ok(quantity).build();
                }
            }
        }).start();
    }

    @PUT //Get product with id
    @RolesAllowed({"ADMIN"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/quantity/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void asyncUpdateStockQuantity(@Suspended final AsyncResponse asyncResponse, int newQuantity, @PathParam("id") int id)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = updateStockQuantity(id, newQuantity);
                asyncResponse.resume(response);
            }
            private Response updateStockQuantity(@PathParam("id") int id, int newQuantity)
            {
                int quantity = productsManager.updateStockQuantity(id, newQuantity);
                if(quantity == -1)
                {
                    return Response.status(Response.Status.BAD_REQUEST).entity("This is not a valid number").build();
                }
                else
                {
                    return Response.ok(quantity).build();
                }
            }
        }).start();
    }

}
