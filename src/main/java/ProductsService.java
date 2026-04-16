package edu.upc.dsa.services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.Product;
import java.util.List;
import edu.upc.dsa.ProductManager;
import edu.upc.dsa.ProductManagerImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/products", description = "Products operations")
@Path("/products")
public class ProductsService {
    private ProductManager pm = ProductManagerImpl.getInstance();

    @GET
    @Path("/price")
    @ApiOperation(value = "List products by price ascending")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByPrice() {
        List<Product> list = pm.getProductsByPrice();
        return Response.ok(list).build();
    }

    @GET
    @Path("/sales")
    @ApiOperation(value = "List products by sales descending")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBySales() {
        List<Product> list = pm.getProductsBySales();
        return Response.ok(list).build();
    }
}
