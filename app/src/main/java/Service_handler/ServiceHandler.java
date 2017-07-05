//package Service_handler;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpPut;
//import org.apache.http.client.utils.URLEncodedUtils;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static android.R.id.message;
//
//public class ServiceHandler {
//
//    static String response = null;
//    public final static int GET = 1;
//    public final static int POST = 2;
//    public final static int put = 3;
//
//    public ServiceHandler() {
//
//    }
//
//    /**
//     * Making service call
//     *
//     * @url - url to make request
//     * @method - http request method
//     */
//    public String makeServiceCall(String url, int method) {
//        return this.makeServiceCall(url, method, null);
//    }
//
//    /**
//     * Making service call
//     *
//     * @url - url to make request
//     * @method - http request method
//     * @params - http request params
//     */
//    public String makeServiceCall(String url, int method,
//                                  List<NameValuePair> params) {
//        try {
//            // http client
//            DefaultHttpClient httpClient = new DefaultHttpClient();
//            HttpEntity httpEntity = null;
//            HttpResponse httpResponse = null;
//
//            // Checking http request method type
//            if (method == POST) {
//                HttpPost httpPost = new HttpPost(url);
//                // adding post params
//                if (params != null) {
//                    httpPost.setEntity(new UrlEncodedFormEntity(params));
//                }
//
//                httpResponse = httpClient.execute(httpPost);
//
//            } else if (method == GET) {
//                // appending params to url
//                if (params != null) {
//                    String paramString = URLEncodedUtils
//                            .format(params, "utf-8");
//                    url += "?" + paramString;
//                }
//                HttpGet httpGet = new HttpGet(url);
//
//                httpResponse = httpClient.execute(httpGet);
//
//            }
//            httpEntity = httpResponse.getEntity();
//            try {
//                response = EntityUtils.toString(httpEntity);
//            } catch (OutOfMemoryError e) {
//                e.printStackTrace();
//            }
//
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return response;
//
//    }
//
//    public String makeServiceCall_withHeader(String url, int method,
//                                             List<NameValuePair> params, JSONObject object) {
//        try {
//            // http client
//            DefaultHttpClient httpClient = new DefaultHttpClient();
//            HttpEntity httpEntity = null;
//            HttpResponse httpResponse = null;
//
//            // Checking http request method type
//            if (method == POST) {
//                HttpPost httpPost = new HttpPost(url);
//                httpPost.addHeader("accept", "application/json");
//                httpPost.addHeader("content-type", "application/json");
////                JSONObject object = new JSONObject();
////                try {
////
////                    object.put("organizerKey", organizerkey);
////                    object.put("passphrase", passphrase);
////
////
////                } catch (Exception ex) {
////
////                }
//
//
//                //   message = object.toString();
//
//                String Body = object.toString();
//                //  String Body = "{  \"events\": {    \"href\": \"string\"  },  \"id\": \"b17b8026-a916-4e8b-ab40-c07582c22215\",  \"memberShipValidated\": false,  \"organizer\": {    \"href\": \"flexcheckin\"  },  \"scannerStation\": \"KH_LAPTOP\",  \"teamAccessValidated\": false,  \"ticketTypes\": [    {      \"href\": \"string\",      \"id\": \"ccea5184-5c69-4b00-b06c-2b245cc202dc\"    },    {      \"href\": \"string\",      \"id\": \"ccea5184-5c69-4b00-b06c-2b245cc202dc\"    }  ]}";
//                // adding post params
//                if (params != null) {
//                    //httpPost.setEntity(new UrlEncodedFormEntity(params));
//                    httpPost.setEntity(new StringEntity(Body, "UTF8"));
//                }
//
//                httpResponse = httpClient.execute(httpPost);
//                int code = httpResponse.getStatusLine().getStatusCode();
//                System.out.println("response code  " + code);
//
//            } else if (method == GET) {
//                // appending params to url
//                if (params != null) {
//                    String paramString = URLEncodedUtils
//                            .format(params, "utf-8");
//                    url += "?" + paramString;
//                }
//                HttpGet httpGet = new HttpGet(url);
//                httpGet.addHeader("accept", "application/json");
//                httpGet.addHeader("content-type", "application/json");
//                httpResponse = httpClient.execute(httpGet);
//
//            }
//            if (method == put) {
//                HttpPut httpPost = new HttpPut(url);
//                httpPost.addHeader("accept", "application/json");
//                httpPost.addHeader("content-type", "application/json");
////                JSONObject object = new JSONObject();
////                try {
////
////                    object.put("organizerKey", organizerkey);
////                    object.put("passphrase", passphrase);
////
////
////                } catch (Exception ex) {
////
////                }
//
//
//                //   message = object.toString();
//
//                String Body = object.toString();
//                // String id = "ccea5184-5c69-4b00-b06c-2b245cc202dc";
////                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
////                nameValuePairs.add(new BasicNameValuePair("id", "ccea5184-5c69-4b00-b06c-2b245cc202dc"));
////                String Body ="{  \"id\": \"b17b8026-a916-4e8b-ab40-c07582c22215\",  \"memberShipValidated\": false, \"scannerStation\": \"KH_LAPTOP\",  \"teamAccessValidated\": false,  \"ticketTypes\": [  {\"id\": \"ccea5184-5c69-4b00-b06c-2b245cc202dc\" } ]}";
////                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
////                nameValuePairs.add(new BasicNameValuePair("id", "ccea5184-5c69-4b00-b06c-2b245cc202dc"));
////                nameValuePairs.add(new BasicNameValuePair("id", "ccea5184-5c69-4b00-b06c-2b245cc202dc"));
//                //String Body = "{  \"events\": {    \"href\": \"string\"  },  \"id\": \"b17b8026-a916-4e8b-ab40-c07582c22215\",  \"memberShipValidated\": false,  \"organizer\": {    \"href\": \"flexcheckin\"  },  \"scannerStation\": \"KH_LAPTOP\",  \"teamAccessValidated\": false,  \"ticketTypes\": [    {      \"href\": \"string\",      \"id\": \"ccea5184-5c69-4b00-b06c-2b245cc202dc\"}  ]}";
//                // adding post params
//                if (params != null) {
//                    //httpPost.setEntity(new UrlEncodedFormEntity(params));
//                    httpPost.setEntity(new StringEntity(Body, "UTF8"));
//                }
//
//                httpResponse = httpClient.execute(httpPost);
//                int code = httpResponse.getStatusLine().getStatusCode();
//                System.out.println("response code  " + code);
//
//            }
//            httpEntity = httpResponse.getEntity();
//            response = EntityUtils.toString(httpEntity);
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return response;
//
//    }
//}