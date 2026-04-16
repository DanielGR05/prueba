package edu.upc.dsa.services;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import models.Order;
import edu.upc.dsa.ProductManager;
import edu.upc.dsa.ProductManagerImpl;

@Api(value = "/users", description = "Users operations")
@Path("/users")
public class UsersService {
    private ProductManager pm = ProductManagerImpl.getInstance();

    @GET
    @Path("/{id}/orders")
    @ApiOperation(value = "Get orders for a user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserOrders(@PathParam("id") String id) {
        List<Order> orders = pm.getOrdersByUser(id);
        return Response.ok(orders).build();
    }
}
