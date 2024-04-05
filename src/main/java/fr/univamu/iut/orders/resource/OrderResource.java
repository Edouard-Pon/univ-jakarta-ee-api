package fr.univamu.iut.orders.resource;

import fr.univamu.iut.orders.repository.OrderRepositoryInterface;
import fr.univamu.iut.orders.service.OrderService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Path;


@Path("/orders")
@ApplicationScoped
public class OrderResource {
    private OrderService service;

    public OrderResource() {}

    public @Inject OrderResource(OrderRepositoryInterface orderRepo) {
        this.service = new OrderService(orderRepo);
    }

    public OrderResource(OrderService service) {
        this.service = service;
    }

    @GET
    @Produces("application/json")
    public String getAllOrders() {
        return service.getAllOrdersJSON();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public String getOrder(@PathParam("id") String id) {
        String result = service.getOrderJSON(id);
        if (result == null) throw new NotFoundException();
        return result;
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response addOrder(@FormParam("menuId") String menuId, @FormParam("address") String address, @FormParam("deliveryDate") String deliveryDate) {
        if (service.addOrder(menuId, address, deliveryDate))
            return Response.ok("added").build();
        else
            return Response.status(Response.Status.CONFLICT).build();
    }
}
