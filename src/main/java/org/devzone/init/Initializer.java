package org.devzone.init;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.devzone.model.Address;
import org.devzone.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@QuarkusMain
public class Initializer {

    public static void main(String... args) {
        Quarkus.run(MyApp.class, args);
    }

    public static class MyApp implements QuarkusApplication {

        private static final Logger logger = LoggerFactory.getLogger(MyApp.class);

        @Inject
        private AddressRepository addressRepository;

        @Override
        public int run(String... args) throws Exception {
            logger.info("First delete (old) entries");
            addressRepository.deleteAll().await().atMost(Duration.ofSeconds(3));
            logger.info("Init database");
            List<Address> addresses = readAddressData();

            addressRepository.saveAddresses(addresses.stream()).await().atMost(Duration.ofSeconds(10));
            Quarkus.waitForExit();
            return 0;
        }

        public List<Address> readAddressData() throws IOException {
            try (InputStream inputStream = getClass().getResourceAsStream("/postalcodes.csv");
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                return reader.lines().map(line -> {
                    line = line.replace("\uFEFF", "");
                    String[] fields = line.split(",");
                    //logger.info("Read address with state {}", fields[3]);
                    return new Address(fields[2], fields[1], fields[3]);
                }).collect(Collectors.toList());
            }
        }
    }
}