package fr.univamu.iut.univjakartaeeapi.resource;

import fr.univamu.iut.univjakartaeeapi.annotation.NoJWTFilter;
import fr.univamu.iut.univjakartaeeapi.repository.UserRepositoryInterface;
import fr.univamu.iut.univjakartaeeapi.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;


@Path("/users")
@ApplicationScoped
public class UserResource {
    private UserService service;

    public UserResource() {}

    public @Inject UserResource(UserRepositoryInterface userRepo) {
        this.service = new UserService(userRepo);
    }

    public UserResource(UserService service) {
        this.service = service;
    }

    @GET
    @NoJWTFilter
    @Produces("application/json")
    public String getAllUsers() {
        return service.getAllUsersJSON();
    }

    @GET
    @NoJWTFilter
    @Path("{id}")
    @Produces("application/json")
    public String getUser(@PathParam("id") String id){
        String result = service.getUserJSON(id);
        if (result == null) throw new NotFoundException();
        return result;
    }

    @GET
    @NoJWTFilter
    @Path("{id}/username")
    @Produces("application/json")
    public String getUsernameById(@PathParam("id") String id){
        String result = service.getUsernameByIdJSON(id);
        if (result == null) throw new NotFoundException();
        return result;
    }

    @POST
    @NoJWTFilter
    @Consumes("application/x-www-form-urlencoded")
    public Response addUser(@FormParam("username") String name, @FormParam("password") String password) {
        if (service.addUser(name, password))
            return Response.ok("added").build();
        else
            return Response.status(Response.Status.CONFLICT).build();
    }
}
