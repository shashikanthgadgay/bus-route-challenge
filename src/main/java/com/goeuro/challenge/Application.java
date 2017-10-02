package com.goeuro.challenge;

import com.goeuro.challenge.data.BusRouteDataStoreManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner{

    private static final Logger log = Logger.getLogger(Application.class);

    @Autowired
    private BusRouteDataStoreManager dataStoreManager;

    @Value("${filePath}")
    private String busRouteDataFilePath;

    public static void main(String[] args) {
        if (args.length != 1) {
            log.error("Invalid number of arguments");
            throw new IllegalArgumentException("This application requires bus route data file path as an argument.");
        }

        args[0] = String.format("--filePath=%s", args[0]);
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        dataStoreManager.validateDataFileAndInitializeStore(busRouteDataFilePath);
    }
}