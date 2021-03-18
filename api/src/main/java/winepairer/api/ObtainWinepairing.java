package winepairer.api;

import jakarta.servlet.http.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import winepairer.api.dto.*;
import winepairer.domain.WinePairer;

@Path("/pairwine")
public class ObtainWinepairing {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response initialize(
            @Context HttpServletRequest request,
            MealInput mealInput) {
        String meal = mealInput.getMealToPair();
        var winepairing = new WinePairer(meal);

        var output = new WinePairing(winepairing);
        return Response.status(200).entity(output).build();
    }
}