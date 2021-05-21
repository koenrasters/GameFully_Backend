package gamefully.service;

import gamefully.service.managers.DataAccess;
import gamefully.service.managers.UserManager;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuthenticationFilter implements ContainerRequestFilter
{
    @Context
    private ResourceInfo resourceInfo;

    private static final UserManager userManager = new UserManager(new DataAccess("DB"));
    private static final TokenSecurity tokenSecurity = new TokenSecurity();

    @Override
    public void filter(ContainerRequestContext requestContext) {
        Method method = resourceInfo.getResourceMethod();

        if (method.isAnnotationPresent(PermitAll.class)) {
            return;
        }

        if (method.isAnnotationPresent(DenyAll.class)) {
            Response response = Response.status(Response.Status.FORBIDDEN).build();
            requestContext.abortWith(response);
            return;
        }

        final String AUTHORIZATION_PROPERTY = "Authorization";
        final String AUTHENTICATION_SCHEME = "Bearer";

        final MultivaluedMap<String, String> headers = requestContext.getHeaders();

        final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

        if (authorization == null || authorization.isEmpty()) {
            Response response = Response.status(Response.Status.UNAUTHORIZED).
                    entity("Missing Token.").build();
            requestContext.abortWith(response);
            return;
        }

        final String token = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

        if(tokenSecurity.checkJWT(token).equals("Expired"))
        {
            Response response = Response.status(Response.Status.UNAUTHORIZED).
                    entity("Token Expired.").build();
            requestContext.abortWith(response);
            return;
        }

        if (tokenSecurity.checkJWT(token).equals("Wrong"))
        {
            Response response = Response.status(Response.Status.UNAUTHORIZED).
                    entity("Wrong token.").build();
            requestContext.abortWith(response);
            return;
        }

        if (method.isAnnotationPresent(RolesAllowed.class)) {
            RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
            Set<String> rolesSet = new HashSet<>(Arrays.asList(rolesAnnotation.value()));

            if (!userManager.isUserAllowed(token, rolesSet)) {
                Response response = Response.status(Response.Status.FORBIDDEN).build();
                requestContext.abortWith(response);
            }
        }

    }


}

