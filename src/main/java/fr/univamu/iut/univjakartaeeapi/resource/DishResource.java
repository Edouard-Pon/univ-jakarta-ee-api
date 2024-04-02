package fr.univamu.iut.univjakartaeeapi.resource;

import fr.univamu.iut.univjakartaeeapi.repository.DishRepositoryInterface;
import fr.univamu.iut.univjakartaeeapi.service.DishService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;


@Path("/dishes")
@ApplicationScoped
public class DishResource {
    private DishService service;

    public DishResource() {}

    public @Inject DishResource(DishRepositoryInterface dishRepo) {
        this.service = new DishService(dishRepo);
    }

    public DishResource(DishService service) {
        this.service = service;
    }

    @GET
    @Produces("application/json")
    public String getAllDishes() {
        return service.getAllDishesJSON();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public String getDish(@PathParam("id") String id) {
        String result = service.getDishJSON(id);
        if (result == null) throw new NotFoundException();
        return result;
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response addDish(@FormParam("name") String name, @FormParam("description") String description, @FormParam("price") double price) {
        if (service.addDish(name, description, price))
            return Response.ok("added").build();
        else
            return Response.status(Response.Status.CONFLICT).build();
    }

    @PUT
    @Path("{id}")
    @Consumes("application/x-www-form-urlencoded")
    public Response updateDish(@PathParam("id") String id, @FormParam("name") String name, @FormParam("description") String description, @FormParam("price") double price) {
        if (service.updateDish(id, name, description, price))
            return Response.ok("updated").build();
        else
            return Response.status(Response.Status.CONFLICT).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteDish(@PathParam("id") String id) {
        if (service.deleteDish(id))
            return Response.ok("deleted").build();
        else
            return Response.status(Response.Status.CONFLICT).build();
    }
}
