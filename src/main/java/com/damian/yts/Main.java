package com.damian.yts;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("|----------------------------------------------------|");
        System.out.println("YTS CLI V.1.0.0 - Crafted  with 🤍 by Damian Peiris !");
        System.out.println("|----------------------------------------------------|");
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the movie name : ");
        String name = input.nextLine();
        name = name.replaceAll(" ", "+");

        System.out.println();

        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder().uri(new URI("https://yts.mx/api/v2/list_movies.json?query_term=" + name)).build();
        } catch (URISyntaxException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            HttpResponse<String> res = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            try {

                JsonObject jsonFile = new Gson().fromJson(response.body(), JsonObject.class);
                JsonObject data = jsonFile.getAsJsonObject("data");
                JsonArray movies = data.getAsJsonArray("movies");
                System.out.println(movies.size() + " Results were found! ");
                System.out.println("----------------------------------------------------");

                movies.forEach((m) -> {
                    JsonObject movie = m.getAsJsonObject();
                    System.out.println("Title : " + movie.get("title"));
                    System.out.println("Click here to download : " + movie.get("url"));
                    System.out.println("Year : " + movie.get("year"));
                    System.out.println("Rating : " + movie.get("rating"));
                    System.out.println("Runtime : " + movie.get("runtime"));
                    System.out.println("Language : " + movie.get("language"));
                    System.out.println("Summary : " + movie.get("summary"));
                    System.out.println("----------------------------------------------------");


                });
                System.out.println("Do you want to search again ? (Y/N)");
                String choice = input.nextLine();
                if (choice.equals("Y") || choice.equals("y")) {
                    clearConsole();
                    main(args);
                } else {
                    System.out.println("Thank you for using YTS CLI !");
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }


    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();


    }


}
