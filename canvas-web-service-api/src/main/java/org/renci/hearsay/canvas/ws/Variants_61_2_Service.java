package org.renci.hearsay.canvas.ws;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.renci.hearsay.canvas.refseq.dao.model.Variants_61_2;

@Path("/Variants_61_2_Service/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({ MediaType.APPLICATION_JSON })
public interface Variants_61_2_Service {

    @GET
    @Path("/findByGeneNameAndMaxAlleleFrequency/{geneName}/{threshold}")
    public List<Variants_61_2> findByGeneNameAndMaxAlleleFrequency(@PathParam("geneName") String geneName,
            @PathParam("threshold") Double threshold);

}
