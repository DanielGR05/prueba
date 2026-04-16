package edu.upc.dsa.services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.Order;
import edu.upc.dsa.ProductManager;
import edu.upc.dsa.ProductManagerImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/orders", description = "Orders operations")
@Path("/orders")
public class OrdersService {
    private ProductManager pm = ProductManagerImpl.getInstance();

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "Create an order (user, items)")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(@FormParam("user") String user, @FormParam("items") String items) {
        if (user == null || items == null) return Response.status(400).entity("{\"error\":\"missing user or items\"}").build();
        Order o = new Order(user);
        String[] pairs = items.split(",");
        for (String p : pairs) {
            String[] parts = p.split(":");
            if (parts.length == 2) {
                try {
                    int qty = Integer.parseInt(parts[1]);
                    o.addLP(qty, parts[0]);
                } catch (NumberFormatException e) {
                    // ignore invalid qty
                }
            }
        }
        pm.addOrder(o);
        return Response.status(201).entity("{\"status\":\"enqueued\"}").build();
    }
}
