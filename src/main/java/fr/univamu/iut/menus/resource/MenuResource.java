package fr.univamu.iut.menus.resource;

import fr.univamu.iut.menus.model.MenuInput;
import fr.univamu.iut.menus.repository.MenuRepositoryInterface;
import fr.univamu.iut.menus.service.MenuService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/menus")
@ApplicationScoped
public class MenuResource {
    private MenuService service;

    public MenuResource() {}

    public @Inject MenuResource(MenuRepositoryInterface menuRepo) {
        this.service = new MenuService(menuRepo);
    }

    public MenuResource(MenuService service) {
        this.service = service;
    }

    @GET
    @Produces("application/json")
    public String getAllMenus() {
        return service.getAllMenusJSON();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public String getMenu(@PathParam("id") String id) {
        String result = service.getMenuJSON(id);
        if (result == null) throw new NotFoundException();
        return result;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMenu(MenuInput menuInput) {
        if (service.addMenu(menuInput.getUserId(), menuInput.getDishes()))
            return Response.ok("added").build();
        else
            return Response.status(Response.Status.CONFLICT).build();
    }

    @PUT
    @Path("{id}")
    @Consumes("application/x-www-form-urlencoded") // TODO - call users api to check access rights
    public Response updateMenu(@PathParam("id") String id, @FormParam("name") String name, @FormParam("description") String description, @FormParam("price") double price) {
        if (service.updateMenu(id, name, description, price))
            return Response.ok("updated").build();
        else
            return Response.status(Response.Status.CONFLICT).build();
    }

    @DELETE
    @Path("{id}") // TODO - call users api to check access rights
    public Response deleteMenu(@PathParam("id") String id) {
        if (service.deleteMenu(id))
            return Response.ok("deleted").build();
        else
            return Response.status(Response.Status.CONFLICT).build();
    }
}
