package ressourcesrest;

import entities.Etudiant;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("etudiants")
public class restEtudiant {

    public static EtudiantBusiness stB = new EtudiantBusiness();
    public static OptionBusiness opB = new OptionBusiness();

    // GET /etudiants              → tous les étudiants
    // GET /etudiants?codeOption=1 → étudiants filtrés par option
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiants(@QueryParam("codeOption") int codeOption) {
        List<Etudiant> l = new ArrayList<>();
        if (codeOption == 0) {
            l = stB.getAllEtudiants();
        } else {
            Option option = opB.getOptionByCode(codeOption);
            if (option != null) {
                l = stB.getEtudiantsByOption(option);
            }
        }

        if (l.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(200).entity(l).build();
    }

    @GET
    @Path("{identifiant}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantById(@PathParam("identifiant") String identifiant) {
        Etudiant e = stB.getEtudiantByIdentifiant(identifiant);
        if (e == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(200).entity(e).build();
    }

    // POST /etudiants → ajouter un étudiant
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewEtudiant(Etudiant etu) {
    // creation objet etudiant
        Etudiant newEtudiant = new Etudiant(
                etu.getIdentifiant(),
                etu.getNom(),
                etu.getPrenom(),
                etu.getOption(),
                etu.getAnneeEtude(),
                etu.getEmail()
        );


        if (!stB.addEtudiant(etu)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    // PUT /etudiants/ID → modifier un étudiant
    @PUT
    @Path("{identifiant}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("identifiant") String identifiant, Etudiant updatedEtudiant) {
        if (stB.updateEtudiant(identifiant, updatedEtudiant)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // DELETE /etudiants/ID → supprimer un étudiant
    @DELETE
    @Path("{identifiant}")
    public Response deleteEtudiant(@PathParam("identifiant") String identifiant) {
        if (stB.deleteEtudiant(identifiant)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}