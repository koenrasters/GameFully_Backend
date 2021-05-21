package gamefully.service.resources;

import gamefully.service.TokenSecurity;
import gamefully.service.managers.DataAccess;
import gamefully.service.managers.UserManager;
import gamefully.service.models.Credentials;
import gamefully.service.models.User;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/user")
public class UserResources
{
    @Context
    private UriInfo uriInfo;
    private static final UserManager userManager = new UserManager(new DataAccess("DB"));
    private static final TokenSecurity tokenSecurity = new TokenSecurity();

    @POST //POST at http://localhost:XXXX/gamefully/accessories
    @Path("/auth")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void asyncGetJWT(@Suspended final AsyncResponse asyncResponse, Credentials credentials)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = getJWT(credentials);
                asyncResponse.resume(response);
            }
            private Response getJWT(Credentials credentials)
            {
                if (!userManager.isValidUser(credentials)) {
                    return Response.status(Response.Status.UNAUTHORIZED).
                            entity("Invalid username and/or password.").build();
                }
                User user = userManager.getUser(credentials.getEmail());
                String jwt = tokenSecurity.getJWT(user);
                return Response.ok(jwt).build();

            }
        }).start();
    }

    @GET //POST at http://localhost:XXXX/gamefully/accessories
    @Path("/check")
    @Consumes(MediaType.APPLICATION_JSON)
    public void asyncCheckJWT(@Suspended final AsyncResponse asyncResponse)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = checkJWT();
                asyncResponse.resume(response);
            }
            private Response checkJWT()
            {
                return Response.ok("OK").build();
            }
        }).start();
    }

    @POST
    @Path("/register")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public void asyncRegister(@Suspended final AsyncResponse asyncResponse, User user)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Response response = registerUser(user);
                asyncResponse.resume(response);
            }
            private Response registerUser(User user)
            {
                if(userManager.addUser(user))
                {
                    String url = uriInfo.getAbsolutePath() + "/" + user.getId();
                    URI uri = URI.create(url);
                    return Response.created(uri).build();
                }
                else
                {
                    return Response.status(Response.Status.CONFLICT).entity("Can't create user").build();
                }
            }
        }).start();
    }


}
