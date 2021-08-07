package com.revature.p0.util;


import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import com.revature.p0.exceptions.DataSourceException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class MongoClientFactory {

    private final MongoClient mongoClient;
    private static final MongoClientFactory mongoClientFactory = new MongoClientFactory();

    private MongoClientFactory(){
        Properties appProperties = new Properties();

        try{
            appProperties.load(new FileReader("src/main/resources/application.properties"));
            String ipAddress = appProperties.getProperty("ip address");
            int port = Integer.parseInt(appProperties.getProperty("port"));
            String dbName = appProperties.getProperty("dbName");
            String username = appProperties.getProperty("username");
            char[] password = appProperties.getProperty("password").toCharArray();

            List<ServerAddress> hosts = Collections.singletonList(new ServerAddress(ipAddress, port));
            MongoCredential credentials = MongoCredential.createScramSha1Credential(username, dbName, password);
            MongoClientSettings settings = MongoClientSettings.builder()
                                                                .applyToClusterSettings(builder -> builder.hosts(hosts))
                                                                .credential(credentials)
                                                                .build();

            this.mongoClient = MongoClients.create(settings);

        } catch (FileNotFoundException fnfe){
            fnfe.printStackTrace(); //TODO log this file
            throw new DataSourceException("Unable to load database properties file.", fnfe);
        } catch (Exception e){
            e.printStackTrace(); //TODO log this file
            throw new DataSourceException("An unexpect exception occured." ,e);

        }

    }

    public void cleanup(){mongoClient.close();}

    public static MongoClientFactory getInstance(){ return mongoClientFactory;}

    public MongoClient getConnection(){return mongoClient;}
}
