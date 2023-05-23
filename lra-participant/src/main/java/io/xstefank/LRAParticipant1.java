package io.xstefank;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.jboss.logging.Logger;

@ApplicationScoped
@Path(LRAParticipant1.ROOT_PATH)
public class LRAParticipant1 {

    public static final String ROOT_PATH = "lra-participant-1";

    private static final Logger LOG = Logger.getLogger(LRAParticipant1.class);

    @Inject
    UriInfo uriInfo;

    @LRA
    @GET
    @Path("/doInLRA")
    public Response doInLRA(@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) String lraId,
                            @QueryParam("fail") @DefaultValue("false") boolean fail) {
        LOG.info("Work LRA ID = " + lraId);

        Client client = ClientBuilder.newClient();
        client.target(uriInfo.getBaseUri() + LRAParticipant2.ROOT_PATH + "/doInLRA").request().get();
        client.close();

        return fail ? Response.status(500).build() : Response.ok().build();
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
