package org.devzone.repository;

import io.quarkus.mongodb.panache.reactive.*;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.devzone.model.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.stream.Stream;

@ApplicationScoped
public class AddressRepository implements ReactivePanacheMongoRepository<Address> {

    private static final Logger logger = LoggerFactory.getLogger(AddressRepository.class);

    public Uni<Void> saveAddress(Address address) {
        logger.info("Try to persist new address");
        return address.persist();
    }

    public Uni<Long> deleteAll() {
        logger.info("Try to persist new address");
        return Address.deleteAll();
    }

    public Uni<Void> saveAddresses(Stream<Address> addresses) {
        logger.info("Try to persist new addresses");
        return persist(addresses);
    }

    public Multi<Address> findByPostalCode(String postalCode){
        return stream("postalCode", Sort.ascending("locality", "state"), postalCode);
    }

    public Multi<Address> getAll() {
        return streamAll(Sort.ascending("locality", "state"));
    }
}