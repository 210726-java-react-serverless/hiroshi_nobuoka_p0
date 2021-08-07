package com.revature.p0.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFIleMaker {
    public static void main(String[] args) {
        Properties properties = new Properties();

        properties.setProperty("ip address", "18.224.215.51");
        properties.setProperty("port", "27017");
        properties.setProperty("dbName", "Project Zero");
        properties.setProperty("username", "hiroshin");
        properties.setProperty("password", "revature");

        try(FileWriter output = new FileWriter("src/main/resources/application.properties")){
            properties.store(output, "these are properties");
        } catch(IOException e){
            e.printStackTrace();
        }

}
