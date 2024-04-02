package fr.univamu.iut.univjakartaeeapi.resource;

import fr.univamu.iut.univjakartaeeapi.annotation.NoJWTFilter;
import fr.univamu.iut.univjakartaeeapi.service.UserAuthService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class UserAuthResource {
    @Inject
    private UserAuthService userAuthService;

    @POST
    @NoJWTFilter
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response authenticate(@FormParam("username") String username, @FormParam("password") String password) {
        String token = userAuthService.authenticate(username, password);
        if (token != null) {
            return Response.ok(token).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}