package com.damian.yts;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the movie name : ");
        String name = input.nextLine();
        System.out.println("Select the quality : ");
        System.out.println("1. 720p");
        System.out.println("2. 1080p");
        System.out.println("3. 2160p");
        String quality = input.nextLine();
        switch (quality){
            case "1":
                quality = "720p";
                break;
            case "2":
                quality = "1080p";
                break;
            case "3":
                quality =" 2160p";
                break;
            default:
                quality = "0";
                break;
        }
        if(quality.equals("0")){
            System.out.println("Invalid input");
            clearConsole();
            main(args);

        }
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder().
                    uri(new URI("https://yts.mx/api/v2/list_movies.json?query_term="+name+"&quality="+quality)).
                    build();
        } catch (URISyntaxException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            HttpResponse<String>res = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(res.body());
        } catch (IOException | InterruptedException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }



    }
    public static void clearConsole() {
          System.out.print("\033[H\033[2J");
            System.out.flush();


    }

}
