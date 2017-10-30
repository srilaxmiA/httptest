
package com.self.httptest;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        try (Scanner in = new Scanner(System.in);) {

            App app = new App();
            String url = null;

            String[] responseHeaders = new String[]{"status", "content-length", "date"};

            while (in.hasNext()) {
                url = in.next();
                Map<String, Object> responseMap = new HashMap<String, Object>();
                responseMap.put("url", url);
                String errorMessage = null;
                try {
                    for (String header : responseHeaders) {

                        Object headerValue = app.getResponseHeader(url, header);

                        responseMap.put(header, headerValue);

                        if ("date".equals(header)) {

                            Long dateInMillis = Long.valueOf((String) headerValue);
                            responseMap.put(header, new Date(dateInMillis));
                        }
                    }
                } catch (HttpUrlError httpUrlError) {
                    errorMessage = "invalid url";
                    responseMap.put("error", errorMessage);
                }


                //System.out.println("response map :: " + responseMap);
                Gson gson = new Gson();
                String json = gson.toJson(responseMap);
                System.out.println(" json string :: " + json);
            }
        }
    }

    public HttpURLConnection getConnection(String httpUrl) throws HttpUrlError{

        URL url = null;
        HttpURLConnection urlConnection =null;
        try {
            url = new URL(httpUrl);
            urlConnection =(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
        }catch (MalformedURLException mue) {
            throw new HttpUrlError(mue.getMessage(),mue);
        } catch (UnknownHostException uhe) {
            throw new HttpUrlError(uhe.getMessage(),uhe);
        } catch (IOException ioe) {
            throw new HttpUrlError(ioe.getMessage(),ioe);
        }
        return urlConnection;
    }


    public String getResponseHeader(String httpUrl,String headerName) throws HttpUrlError{

        String headerValue=null;
        HttpURLConnection urlConnection=null;
        try {

            urlConnection=getConnection(httpUrl);
//            urlConnection.setRequestMethod("HEAD");
            urlConnection.connect();
            if("status".equals(headerName)){
                headerValue=String.valueOf(urlConnection.getResponseCode());
            }
            if("content-length".equals(headerName)){
                headerValue=String.valueOf(urlConnection.getContentLength());
            }
            if("date".equals(headerName)){
                headerValue=String.valueOf(urlConnection.getDate());
            }
        } catch (UnknownHostException uhe) {
            throw new HttpUrlError(uhe.getMessage(),uhe);
        }catch (IOException ioe) {
            throw new HttpUrlError(ioe.getMessage(),ioe);
        }
        urlConnection.disconnect();
       return headerValue;
    }

//    public int getStausCode(HttpURLConnection urlConnection,String headerName) throws IOException{
//
//        return urlConnection.getResponseCode();
//    }
//
//    public int getContentLength(HttpURLConnection urlConnection,String headerName) throws IOException{
//
//        return urlConnection.getContentLength();
//    }
//
//    public Date getDateTime(HttpURLConnection urlConnection,String headerName) throws IOException{
//
//        return new Date(urlConnection.getDate());
//    }


}

class HttpUrlError extends RuntimeException{
    public HttpUrlError(String message) {
        super(message);
    }

    public HttpUrlError(String message, Throwable cause) {
        super(message, cause);
    }
}

