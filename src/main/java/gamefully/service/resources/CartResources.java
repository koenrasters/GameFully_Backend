package gamefully.service.resources;

import gamefully.service.managers.DataAccess;
import gamefully.service.managers.UserManager;
import gamefully.service.models.Cart;
import gamefully.service.models.CartItem;
import gamefully.service.models.CartItemUpdate;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/cart")
public class CartResources {
    @Context
    private UriInfo uriInfo;
    private static final UserManager userManager = new UserManager(new DataAccess("DB"));

    @GET //Get product with id
    @RolesAllowed({"USER", "ADMIN"})
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void asyncGetCart(@Suspended final AsyncResponse asyncResponse, @PathParam("id") int id)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = getCart(id);
                asyncResponse.resume(response);
            }
            private Response getCart(@PathParam("id") int id)
            {
                Cart cart = userManager.getCart(id);
                if(cart == null)
                {
                    return Response.status(Response.Status.BAD_REQUEST).entity("This is not a valid number").build();
                }
                else
                {
                    return Response.ok(cart).build();
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
                Response response = getCartQuantity(id);
                asyncResponse.resume(response);
            }
            private Response getCartQuantity(@PathParam("id") int id)
            {
                int quantity = userManager.getCartQuantity(id);
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
    @RolesAllowed({"USER", "ADMIN"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/item/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void asyncUpdateItemQuantity(@Suspended final AsyncResponse asyncResponse, int newQuantity, @PathParam("id") int id)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = updateItemQuantity(id, newQuantity);
                asyncResponse.resume(response);
            }
            private Response updateItemQuantity(@PathParam("id") int id, int newQuantity)
            {
                int quantity = userManager.updateCartItemQuantity(id, newQuantity);
                if(quantity == -1)
                {
                    return Response.status(Response.Status.BAD_REQUEST).entity("This is not a valid number to update").build();
                }
                else
                {
                    return Response.ok(quantity).build();
                }
            }
        }).start();
    }
    
    @POST
    @RolesAllowed({"USER", "ADMIN"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add")
    public void asyncAddCartItem(@Suspended final AsyncResponse asyncResponse, CartItemUpdate update)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = addCartItem(update);
                asyncResponse.resume(response);
            }
            private Response addCartItem(CartItemUpdate update)
            {
                CartItem cartItem = userManager.addCartItem(update);
                if (cartItem.getCart() == null){
                    String entity =  "CartItem with number " + cartItem.getId() + " already exists.";
                    return Response.status(Response.Status.CONFLICT).entity(entity).build();
                } else {
                    String url = uriInfo.getAbsolutePath() + "/" + cartItem.getId();
                    URI uri = URI.create(url);
                    return Response.created(uri).build();
                }
            }
        }).start();
    }

    @DELETE //DELETE at http://localhost:XXXX/gamefully/accessories/3
    @RolesAllowed({"ADMIN","USER"})
    @Path("/item/{id}")
    public void asyncDelCartItem(@Suspended final AsyncResponse asyncResponse, @PathParam("id") int nr)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = deleteCartItem(nr);
                asyncResponse.resume(response);
            }
            private Response deleteCartItem(@PathParam("id") int nr) {
                userManager.deleteCartItem(nr);
                return Response.noContent().build();
            }
        }).start();
    }

}
