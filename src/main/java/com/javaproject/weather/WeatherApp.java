package com.javaproject.weather;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter city name");
        String cityname = sc.nextLine();
        sc.close();

        try{
            URL url = new URL(""+ cityname +"");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            int responseCode = httpURLConnection.getResponseCode();

            if(responseCode != 200){
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            }
            else{
                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()){
                    informationString.append(scanner.nextLine());
                }
                scanner.close();

                System.out.println(informationString);

                Object jsonParser = new JSONParser().parse(String.valueOf(informationString));

                JSONObject jsonObject = new JSONObject();
                jsonObject = (JSONObject) jsonParser;
                System.out.println("Coordinates are : " + jsonObject.get("coord"));
                System.out.println("Base : " + jsonObject.get("base"));
                System.out.println("Main : " + jsonObject.get("main"));
                System.out.println("Visibility : " + jsonObject.get("visibility"));
                System.out.println("Wind : " + jsonObject.get("wind"));
                System.out.println("Clouds : " + jsonObject.get("clouds"));
                System.out.println("SYS : " + jsonObject.get("sys"));

                Object test = jsonObject.get("main");
                JSONObject obj = new JSONObject();
                obj = (JSONObject) test;
                System.out.println(obj.get("temp"));

                JSONArray jsonArray = new JSONArray();
                jsonArray = (JSONArray) jsonObject.get("weather");
                if(jsonArray.listIterator() != null){
                    System.out.println(jsonArray.get(0));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
