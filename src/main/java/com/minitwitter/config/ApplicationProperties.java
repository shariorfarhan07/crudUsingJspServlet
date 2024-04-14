package com.minitwitter.config;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ApplicationProperties {
    private final Map<String,String> properties;
    private static ApplicationProperties instance;
//    private static String filePath = "./src/main/resources/application.properties";
    private static String filePath = "/home/shariorh/eclipse-workspace/twitter/target/classes/application.properties";

    public ApplicationProperties(Map<String, String> properties) {
        this.properties = properties;
    }
    public static synchronized ApplicationProperties getProperties() throws FileNotFoundException {
//        test();


       System.out.println(" farhan "+filePath);
       System.out.println(filePath);

        if (ApplicationProperties.instance == null){
            Map<String,String> map=new HashMap<>();
//            /home/shariorh/eclipse-workspace/twitter/src/main/resources
            File file = new File(filePath);
            System.out.println(file.listFiles());
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            try (BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+"src/main/resources/"+filePath))) {
                String line;
                String[] keyValue;
                while ((line = reader.readLine()) != null) {
//                        System.out.println(line);
                    if (!line.startsWith("#") && line.contains("=")){
                        keyValue=line.split("=");
                        map.put(keyValue[0],keyValue[1]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle file I/O errors
            }
            instance=new ApplicationProperties(map);

        }

        return ApplicationProperties.instance;
    }
    public String getProp(String key){ return properties.get(key);}

}