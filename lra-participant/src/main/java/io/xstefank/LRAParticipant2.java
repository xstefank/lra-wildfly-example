package io.xstefank;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.jboss.logging.Logger;

@Path(LRAParticipant2.ROOT_PATH)
public class LRAParticipant2 {

    public static final String ROOT_PATH = "lra-participant-2";

    private static final Logger LOG = Logger.getLogger(LRAParticipant2.class);


    @LRA(end = false)
    @GET
    @Path("/doInLRA")
    public Response doInLRA(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId,
                            @QueryParam("fail") @DefaultValue("false") boolean fail) {
        LOG.info("Work LRA ID = " + lraId);
        return fail ? Response.status(500).build() : Response.ok(lraId).build();
    }

    @Complete
    @PUT
    @Path("/complete")
    public Response complete(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        LOG.info("Complete ID = " + lraId);
        return Response.ok().build();
    }

    @Compensate
    @PUT
    @Path("/compensate")
    public Response compensate(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId) {
        LOG.info("Compensate ID = " + lraId);
        return Response.ok().build();
    }
}
