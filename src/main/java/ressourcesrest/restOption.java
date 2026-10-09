package ressourcesrest;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/options")
public class restOption {

   public static OptionBusiness opB= new OptionBusiness();

   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public Response getOptions(@QueryParam("domaine") String d){
      List <Option>l= new ArrayList<>();
      if(d == null){l= opB.getListeOptions();}
      else { l= opB.getOptionsByDomaine(d);}

      if(l.isEmpty()){
         return Response.status(Response.Status.NO_CONTENT).build();
      }
      return Response.status(200).entity(l).build();
   }

   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   public Response addNewOption(Option op) {
      if (!opB.addOption(op)) {
         return Response.status(Response.Status.BAD_REQUEST).build();
      }
      return Response.status(200).build();
   }

//     @GET
//     @Produces(MediaType.APPLICATION_JSON)
//     public Response getOptionByDomaine( @QueryParam("domaine") String d) {
//         List <Option>l= opB.getOptionsByDomaine(d);
//         if(l.isEmpty()){
//             return Response.status(Response.Status.NO_CONTENT).build();
//         }
//         return Response.status(200).entity(l).build();
//     }

   @DELETE
   @Path("{code}")
   public Response deleteOption(@PathParam("code") int  id) {
      if(opB.deleteOption(id)) {
         return Response.status(Response.Status.NO_CONTENT).build();
      }
      return Response.status(Response.Status.NOT_FOUND).build();
   }
}
