package edu.upc.dsa.services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.Order;
import edu.upc.dsa.ProductManager;
import edu.upc.dsa.ProductManagerImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/serve", description = "Serve operations")
@Path("/serve")
public class ServeService {
    private ProductManager pm = ProductManagerImpl.getInstance();

    @POST
    @ApiOperation(value = "Serve the next order (FIFO)")
    @Produces(MediaType.APPLICATION_JSON)
    public Response serve() {
        Order o = pm.deliverOrder();
        if (o == null) return Response.ok("{\"status\":\"no_orders\"}").build();
        return Response.ok(o).build();
    }
}
