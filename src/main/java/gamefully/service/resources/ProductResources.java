package gamefully.service.resources;

import gamefully.service.managers.DataAccess;
import gamefully.service.managers.ProductsManager;
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
import java.util.List;

@Path("/")
public class ProductResources
{
    @Context
    private UriInfo uriInfo;
    private static final ProductsManager productsManager = new ProductsManager(new DataAccess("DB"));

    @GET //Get product with id
    @PermitAll
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void asyncGetProduct(@Suspended final AsyncResponse asyncResponse, @PathParam("id") int id)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = getProduct(id);
                asyncResponse.resume(response);
            }
            private Response getProduct(@PathParam("id") int id)
            {
                Product product = productsManager.getProduct(id);
                if(product == null)
                {
                    return Response.status(Response.Status.BAD_REQUEST).entity("This is not a valid number").build();
                }
                else
                {
                    return Response.ok(product).build();
                }
            }
        }).start();
    }

    @GET //Get accessory with id
    @PermitAll
    @Path("/distinct")
    @Produces(MediaType.APPLICATION_JSON)
    public void asyncGetDistinctProduct(@Suspended final AsyncResponse asyncResponse)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = getDistinctProduct();
                asyncResponse.resume(response);
            }
            private Response getDistinctProduct()
            {
                List<DistinctColumn> distinctColumns = productsManager.getColumns("product");
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
    public void asyncGetAllProducts(@Suspended final AsyncResponse asyncResponse, @Context UriInfo info)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = getAllProducts(info);
                asyncResponse.resume(response);
            }
            private Response getAllProducts(@Context UriInfo info) {
                List<Product> products;
                String query = info.getRequestUri().getQuery();
                products = productsManager.getProducts(query);
                if (products == null){
                    return Response.status(Response.Status.BAD_REQUEST).entity("Please provide a valid query").build();
                } else
                {
                    return Response.ok(products).build();
                }

            }
        }).start();
    }


    @DELETE //DELETE at http://localhost:XXXX/gamefully/3
    @RolesAllowed("ADMIN")
    @Path("{id}")
    public void asyncDelProduct(@Suspended final AsyncResponse asyncResponse, @PathParam("id") int nr)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = deleteProduct(nr);
                asyncResponse.resume(response);
            }
            private Response deleteProduct(@PathParam("id") int nr) {
                productsManager.deleteProduct(nr);
                return Response.noContent().build();
            }
        }).start();
    }



}
