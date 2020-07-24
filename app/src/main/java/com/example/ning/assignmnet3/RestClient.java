package com.example.ning.assignmnet3;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class RestClient {
    private static final String BASE_URL = "http://172.20.10.2:9486/Assignment1/webresources/";

    public static String findByUsername(String username) {
        final String methodPath = "a1.credentials/findPasswordByUsername/" + username; //initialise

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";

        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
        //open the connection
            conn = (HttpURLConnection) url.openConnection();
        //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
        //set the connection method to GET
            conn.setRequestMethod("GET");
        //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Accept", "text/plain");
            // Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the input steream and store it as string

            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String findCredByUsername(String username) {
        final String methodPath = "a1.credentials/findByUserName/" + username; //initialise

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";

        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json

            //conn.setRequestProperty("Content-Type", "application/json");
            //conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            // Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the input steream and store it as string

            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String findCalorieBurnedByStepByUserId(int userid) {
        final String methodPath = "a1.users/findCaloriesBurnedPerStep/" + userid; //initialise

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";

        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json

            //conn.setRequestProperty("Content-Type", "application/json");
            //conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Accept", "text/plain");
            // Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the input steream and store it as string

            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String findCalorieBurnedAtRestByUserId(int userid) {
        final String methodPath = "a1.users/calculateTotalCaloBurnedAtRest/" + userid; //initialise

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";

        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Accept", "text/plain");
            // Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the input steream and store it as string

            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            conn.disconnect();
        }
        return textResult;
    }


    public static String findCalorieConsumedByUserIdAndDate(int userid, String Date) {
        final String methodPath = "a1.consumption/findTotalCaloConsumed/" + userid + "/" + Date; //initialise

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";

        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json

            //conn.setRequestProperty("Content-Type", "application/json");
            //conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Accept", "text/plain");
            // Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the input steream and store it as string

            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            conn.disconnect();
        }
        return textResult;
    }


    public static String findUserByUserId(Integer userId) {
        final String methodPath = "a1.users/" + userId; //initialise

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";

        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json

            //conn.setRequestProperty("Content-Type", "application/json");
            //conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            // Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the input steream and store it as string

            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            conn.disconnect();
        }
        return textResult;
    }



    public static String findAllUsers() {
        final String methodPath = "a1.users/"; //initialise

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";

        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            // Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the input steream and store it as string

            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            conn.disconnect();
        }
        return textResult;
    }


    public static String findAllCredentials() {
        final String methodPath = "a1.credentials/"; //initialise

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";

        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            // Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the input stream and store it as string

            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static void createUser(Users user){
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="a1.users/";
        try {
            Gson gson = new GsonBuilder().setDateFormat("YYYY-MM-dd'T'hh:mm:ssXXX").create();
            String stringCourseJson=gson.toJson(user);
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST"); //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            System.out.println(stringCourseJson);

            PrintWriter out= new PrintWriter(conn.getOutputStream()); out.print(stringCourseJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static void createCredential(Credentials credential){
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="a1.credentials/";
        try {
            Gson gson = new GsonBuilder().setDateFormat("YYYY-MM-dd'T'hh:mm:ssXXX").create();
            String stringCourseJson=gson.toJson(credential);
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST"); //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            System.out.println(stringCourseJson);
            PrintWriter out= new PrintWriter(conn.getOutputStream()); out.print(stringCourseJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static String getPiechartDate(int userid, String date) {
        final String methodPath = "a1.report/findCaloriesConsumedBurnedandRemained/" + userid + "/" + date; //initialise

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";

        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json

            //conn.setRequestProperty("Content-Type", "application/json");
            //conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Accept", "text/plain");
            // Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the input steream and store it as string

            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String getBarchartData(int userid, String startDate, String endDate) {
        final String methodPath = "a1.report/findCaloriesConsumedBurnedandStepsInAPeriod/" + userid + "/" + startDate + "/" + endDate; //initialise

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";

        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json

            //conn.setRequestProperty("Content-Type", "application/json");
            //conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Accept", "text/plain");
            // Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the input steream and store it as string

            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            conn.disconnect();
        }
        return textResult;
    }


    public static String findFoodByCategory(String cate) {
        final String methodPath = "a1.foods/findByCategory/" + cate; //initialise

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";

        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            // Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the input steream and store it as string

            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static void createConsumption(Consumption consumption){
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="a1.consumption/";
        try {
            Gson gson = new GsonBuilder().setDateFormat("YYYY-MM-dd'T'hh:mm:ssXXX").create();
            String stringCourseJson=gson.toJson(consumption);
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST"); //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            System.out.println(stringCourseJson);

            PrintWriter out= new PrintWriter(conn.getOutputStream()); out.print(stringCourseJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static String getFoodById(int foodId) {
        final String methodPath = "a1.foods/" + foodId; //initialise

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";

        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json

            //conn.setRequestProperty("Content-Type", "application/json");
            //conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            // Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the input steream and store it as string

            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String countConsumption() {
        final String methodPath = "a1.consumption/count"; //initialise

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";

        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json

            //conn.setRequestProperty("Content-Type", "application/json");
            //conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Accept", "text/plain");
            // Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the input steream and store it as string

            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String googleSearch(String keyword, String[] params, String[] values) {
        String API_KEY = "AIzaSyDYdoHegPGzUadsBrX0YN0YI2_4eAHjWz0";
        String SEARCH_ID_cx = "013548152336417214388:xecq-eulvfg";
        keyword = keyword.replace(" ", "+");
            URL url = null;
            HttpURLConnection connection = null;
            String textResult = "";
            String query_parameter="";
            if (params!=null && values!=null){
                for (int i =0; i < params.length; i ++){
                    query_parameter += "&";
                    query_parameter += params[i];
                    query_parameter += "=";
                    query_parameter += values[i];
                }
            }

            try {
                url = new URL("https://www.googleapis.com/customsearch/v1?key="+
                        API_KEY+ "&cx="+ SEARCH_ID_cx + "&q="+ keyword + query_parameter);
                connection = (HttpURLConnection)url.openConnection();
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                Scanner scanner = new Scanner(connection.getInputStream());
                while (scanner.hasNextLine()) {
                    textResult += scanner.nextLine();
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                connection.disconnect();
            }
            return textResult;
    }


    public static String foodSearch(String keyword) {

        String baseUrl = "https://api.edamam.com/api/food-database/parser?";
        String APP_ID = "f324766c";
        String key = "edf43aba0caa4470ebf9c160aae05fa3";
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter="";

        try {
            url = new URL(baseUrl + "ingr=" + keyword + "&app_id=" + APP_ID + "&app_key=" + key);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return textResult;
    }



    public static String countFood() {
        final String methodPath = "a1.foods/count"; //initialise

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";

        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json

            //conn.setRequestProperty("Content-Type", "application/json");
            //conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Accept", "text/plain");
            // Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the input steream and store it as string

            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static void createFood(Foods food){
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="a1.foods/";
        try {
            Gson gson = new GsonBuilder().setDateFormat("YYYY-MM-dd'T'hh:mm:ssXXX").create();
            String stringCourseJson=gson.toJson(food);
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST"); //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            System.out.println(stringCourseJson);

            PrintWriter out= new PrintWriter(conn.getOutputStream()); out.print(stringCourseJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static void createReport(NewReport report){
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="a1.report/";
        try {
            Gson gson = new GsonBuilder().setDateFormat("YYYY-MM-dd'T'hh:mm:ssXXX").create();
            String stringCourseJson=gson.toJson(report);
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST"); //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            System.out.println(stringCourseJson);

            PrintWriter out= new PrintWriter(conn.getOutputStream()); out.print(stringCourseJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static String countReport() {
        final String methodPath = "a1.report/count"; //initialise

        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";

        //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json

            //conn.setRequestProperty("Content-Type", "application/json");
            //conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Accept", "text/plain");
            // Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the input steream and store it as string

            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String parkSearch(String keyword) {

        String baseUrl = "https://www.mapquestapi.com/search/v2/radius?origin=";
        String key = "PoO8uSagaxnSM53Q5HWjj8tb7syflUaG";
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";

        try {
            url = new URL(baseUrl + keyword +"&radius=5&maxMatches=1&ambiguities=ignore&hostedData=mqap.ntpois|group_sic_code=?|799951&outFormat=json&key=" + key);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return textResult;
    }
}

