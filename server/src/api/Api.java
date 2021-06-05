package api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import database.Database;

@SpringBootApplication
public class Api {

    static final String databaseConnUrl = "mongodb://localhost:27017";

    public static void main(String[] args) {
        Database.connect(databaseConnUrl);
        SpringApplication.run(Api.class, args);
    }

}
