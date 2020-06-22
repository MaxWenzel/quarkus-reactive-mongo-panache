package org.devzone.model;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MongoEntity(collection="addressCollection")
public class Address extends ReactivePanacheMongoEntity {

    private static final Logger logger = LoggerFactory.getLogger(Address.class);

    private String postalCode;
    private String locality;
    private String state;

    public Address() {
    }

    public Address(String postalCode, String locality, String state) {
        this.postalCode = postalCode;
        this.locality = locality;
        this.state = state;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocality() {
        return locality;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

}
