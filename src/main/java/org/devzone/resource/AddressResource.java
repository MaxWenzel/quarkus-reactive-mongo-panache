package org.devzone.resource;

import io.smallrye.mutiny.Multi;
import org.devzone.model.Address;
import org.devzone.repository.AddressRepository;
import org.jboss.resteasy.annotations.SseElementType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/postalcodes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddressResource {

    private static final Logger logger = LoggerFactory.getLogger(AddressResource.class);

    @Inject
    private AddressRepository addressRepository;

    @GET
    @Path("/complete")
    public Multi<Address> get() {
        logger.info("Try to find postalcodes");
        return addressRepository.getAll();
    }

    @GET
    @Path("/{postalcode}")
    public Multi<Address> getSingle(@PathParam String postalCode) {
        logger.info("Try to find address for postalcode {}", postalCode);
        return addressRepository.findByPostalCode(postalCode);
    }

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    public Multi<Address> getAllAtOnce(@PathParam int count, @PathParam String name) {
        return addressRepository.getAll();
    }
}