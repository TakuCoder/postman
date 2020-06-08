package thiyagu.postman.com.postmanandroid.Utils;

import android.arch.persistence.room.Room;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import thiyagu.postman.com.postmanandroid.Database.CollectionsDAO.InfoDAO;
import thiyagu.postman.com.postmanandroid.Database.CollectionsDAO.InfoTable;
import thiyagu.postman.com.postmanandroid.Database.CollectionsDAO.ItemDAO;
import thiyagu.postman.com.postmanandroid.Database.CollectionsDAO.ItemTable;
import thiyagu.postman.com.postmanandroid.Database.Databases.CollectionDatabase;

public class PublicShareCollectionParser {
    CollectionDatabase collectionDatabase;
    InfoDAO infoDAO;
    ItemDAO itemDAO;
    String _postman_id;

    InfoTable infoTable;
    ItemTable itemTable;

    String request_method;

    public PublicShareCollectionParser(Context context)
    {

        collectionDatabase = Room.databaseBuilder(context, CollectionDatabase.class, "collection_db")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build();
        infoDAO = collectionDatabase.getInfoDAO();
        infoTable = new InfoTable();
    }
    public int parse(String s,String origin) throws IOException
    {

        String json;
        if(origin.equalsIgnoreCase("file"))
        {

            BufferedReader bufferedReader ;
            StringBuilder sb ;
            String line;
            bufferedReader = new BufferedReader(new FileReader(s));
            sb = new StringBuilder();

            line = bufferedReader.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }

            json = sb.toString();
        }
        else {

            json = s;
        }
        try {




            JSONObject info_Object = new JSONObject(json);
            if (info_Object.has("auth-attribute")) {
                System.out.println("auth-attribute");
            }
            if (info_Object.has("auth")) {
                System.out.println("found auth");
            }
            if (info_Object.has("certificate-list")) {
                System.out.println("certificate-list");
            }
            if (info_Object.has("certificate")) {
                System.out.println("certificate");
            }
            if (info_Object.has("cookie-list")) {
                System.out.println("cookie-list");
            }
            if (info_Object.has("cookie")) {
                System.out.println("cookie");
            }
            if (info_Object.has("description")) {
                System.out.println("description");
            }
            if (info_Object.has("event-list")) {
                System.out.println("event-list");
            }
            if (info_Object.has("event")) {
                System.out.println("event");
            }
            if (info_Object.has("header-list")) {
                System.out.println("header-list");
            }
            if (info_Object.has("header")) {
                System.out.println("header");
            }
            if (info_Object.has("info")) {
                PrintLog("found info");

                JSONObject info = info_Object.getJSONObject("info");
                //System.out.println("found info");
                _postman_id = info.getString("_postman_id");
                String name = info.getString("name");
                String schema = info.getString("schema");
                System.out.println("_postman_id " + _postman_id);
                System.out.println("name " + name);
                System.out.println("schema " + schema);


                infoTable.set_postman_id(_postman_id);
                infoTable.setName(name);
                infoTable.setSchema(schema);


                PrintLog("found info");
            }
            if (info_Object.has("item-group")) {
                System.out.println("item-group");
            }
            if (info_Object.has("item"))
            {
                PrintLog("found item");
                //System.out.println("item");
                JSONArray jsonArray = info_Object.getJSONArray("item");

                int count = jsonArray.length();
                infoTable.setNo_of_data(count);
                infoDAO.Insert(infoTable);


                for(int ii=0;ii<jsonArray.length();ii++)
                {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(ii);
                    String item_name = jsonObject1.getString("name");
                    System.out.println("name  " + item_name);

                    itemTable = new ItemTable();
                    itemTable.setName(item_name);




                    //itemname


                    if (jsonObject1.has("protocolProfileBehavior"))
                    {
                        System.out.println("has protocolProfileBehavior");

                        JSONObject protocolProfileBehavior = jsonObject1.getJSONObject("protocolProfileBehavior");
                        if ((protocolProfileBehavior.has("disableBodyPruning"))) {

                            System.out.println("disableBodyPruning" + protocolProfileBehavior.get("disableBodyPruning"));
                        }
                    }

                    JSONObject object_request = jsonObject1.getJSONObject("request");
                    itemTable.setRequest(object_request.toString());
                    itemTable.setResponse("");
                    itemTable.set_postman_id(_postman_id);
                    ItemDAO itemDAO = collectionDatabase.getItemDAO();
                    itemDAO.Insert(itemTable);
                    // System.out.println(object_request);
                    if (object_request.has("auth"))
                    {
                        PrintLog("found auth");

                        JSONObject auth_object = object_request.getJSONObject("auth");
                        switch (auth_object.getString("type")) {
                            case "basic":
                                PrintLog("found basic");
                                JSONArray basic_array = auth_object.getJSONArray("basic");
                                JSONObject basic_username_object = new JSONObject(basic_array.get(1).toString());
                                String basic_username_type = basic_username_object.getString("type");
                                String basic_username_value = basic_username_object.getString("value");
                                String basic_username_key = basic_username_object.getString("key");
                                System.out.println(basic_username_type + "    basic_username_type");
                                System.out.println(basic_username_value + "   basic_username_value");
                                System.out.println(basic_username_key + " basic_username_key");
                                JSONObject basic_password_object = new JSONObject(basic_array.get(0).toString());
                                String basic_password_type = basic_password_object.getString("type");
                                String basic_password_value = basic_password_object.getString("value");
                                String basic_password_key = basic_password_object.getString("key");
                                System.out.println(basic_password_type + "    basic_password_type");
                                System.out.println(basic_password_value + "   basic_password_value");
                                System.out.println(basic_password_key + "     basic_password_key");
                                System.out.println();
                                break;
                            default:
                                System.out.println("this execeuted");
                                break;
                        }
                    }
                    request_method = object_request.get("method").toString();
                    System.out.println(request_method + " request_method");

                    if (object_request.has("header"))
                    {
                        PrintLog("found header");
                        JSONArray request_header_array = object_request.getJSONArray("header");
                        for (int i = 0; i < request_header_array.length(); i++) {
                            JSONObject header_object = request_header_array.getJSONObject(i);

                            {
                                String header_key = header_object.getString("key");
                                String header_value = header_object.getString("value");


                                String header_type = header_object.getString("type");
                                System.out.println(header_key + " header_key");
                                System.out.println(header_value + " header_value");
                                System.out.println(header_type + " header_type");
                                if (header_object.has("description")) {
                                    String header_description = header_object.getString("description");
                                    System.out.println(header_description + " header_description");

                                }
                                else {
                                    System.out.println("header_description" + "no header_description");
                                }

                                if(header_object.has("disabled"))
                                {

                                }
                                else
                                {

                                }

                            }
                            System.out.println("");
                        }
                    } else {

                        PrintLog("no found header");
                    }


                    if (object_request.has("body"))
                    {
                        PrintLog("found body");
                        JSONObject object = object_request.getJSONObject("body");
                        String mode = object.getString("mode");
                        System.out.println(mode);

                        switch (mode)
                        {

                            case "formdata":


                                JSONArray formdata_Array = object.getJSONArray("formdata");
                                for (int i = 0; i < formdata_Array.length(); i++) {
                                    JSONObject formdata_object = formdata_Array.getJSONObject(i);
                                    String formdata_key = formdata_object.getString("key");

                                    String formdata_type = formdata_object.getString("type");
                                    if (formdata_type.equals("text")) {
                                        String formdata_value = formdata_object.getString("value");
                                        System.out.println(formdata_value + "   formdata_value");
                                    } else if (formdata_type.equals("file")) {

                                        String formdata_value = formdata_object.getString("src");
                                        System.out.println(formdata_value + "   formdata_value");
                                    }
                                    if (formdata_object.has("disabled")) {
                                        if (formdata_object.get("disabled").equals(true)) {

                                            System.out.println("Disabled");

                                        }

                                    } else {

                                        System.out.println("not Disabled");
                                    }
                                    System.out.println(formdata_key + "   formdata_key");
                                    System.out.println(formdata_type + "   formdata_type");
                                    if (formdata_object.has("description")) {

                                        String formdata_description = formdata_object.getString("description");
                                        System.out.println(formdata_description + "   formdata_description");
                                    } else {
                                        System.out.println(" no  formdata_description");

                                    }


                                }
                                PrintLog("found formdata");
                                break;

                            case "urlencoded":
                                PrintLog("found urlencoded");

                                formdata_Array = object.getJSONArray("urlencoded");
                                for (int i = 0; i < formdata_Array.length(); i++) {
                                    JSONObject urlencoded_object = formdata_Array.getJSONObject(i);
                                    String urlencoded_key = urlencoded_object.getString("key");
                                    String urlencoded_value = urlencoded_object.getString("value");
                                    String urlencoded_type = urlencoded_object.getString("type");
                                    if (urlencoded_object.has("disabled")) {
                                        System.out.println(urlencoded_object.get("disabled"));

                                    } else {

                                        System.out.println("not Disabled");
                                    }
                                    System.out.println(urlencoded_key + "   urlencoded_key");
                                    System.out.println(urlencoded_value + "   urlencoded_value");

                                    System.out.println(urlencoded_type + "   urlencoded_type");
                                    if (urlencoded_object.has("description")) {

                                        String formdata_description = urlencoded_object.getString("description");
                                        System.out.println(formdata_description + "   urlencoded_description");
                                    } else {
                                        System.out.println(" no  urlencoded_description");

                                    }
                                    System.out.println();

                                }
                                break;

                            case "raw":
                                System.out.println(object.get("raw"));
                                PrintLog("found raw");
                                break;

                            case "file":
                                PrintLog("found file");
                                break;

                            default:
                                break;


                        }

                        System.out.println(mode);
                    } else {

                        PrintLog("no body found");
                    }
                    if (object_request.has("url"))
                    {

                        if(request_method.equalsIgnoreCase("GET"))
                        {
String url = object_request.getString("url");
                        //    JSONObject Object_Url = object_request.getJSONObject("url");
                         //   String raw = Object_Url.getString("raw");
//                            if (Object_Url.has("query"))
//                            {
//                                PrintLog("has Querry");
//                                JSONArray query_array = Object_Url.getJSONArray("query");
//                                for (int i = 0; i < query_array.length(); i++) {
//                                    JSONObject jsonObject2 = query_array.getJSONObject(i);
//                                    String query_key = jsonObject2.getString("key");
//                                    String query_value = jsonObject2.getString("value");
//
//                                    System.out.println("query_key " + query_key);
//                                    System.out.println("query_value " + query_value);
//
//                                    if (jsonObject2.has("description")) {
//
//                                        String query_description = jsonObject2.getString("description");
//                                        System.out.println("query_description " + query_description);
//                                    } else {
//                                        //String query_description = jsonObject2.getString("description");
//                                        System.out.println("query_description " + "nodesc");
//
//                                    }
//
//
//                                    if (jsonObject2.has("disabled")) {
//                                        String disabled = jsonObject2.optString("disabled");
//                                        System.out.println("disabled " + disabled);
//                                    } else {
//                                        System.out.println("disabled " + "false");
//
//                                    }
//                                    System.out.println("");
//                                }
//
//                            } else {
//                                PrintLog("No Querry");
//
//                            }


                        }
                        else if(request_method.equalsIgnoreCase("POST"))
                        {
                            PrintLog("found url");

                            String url =  object_request.getString("url");
                    if(object_request.has("description"))
                    {

                        PrintLog("Found Description");
                        String description =  object_request.getString("description");


                    }

                            System.out.println(url);
                        }



                        //https://www.getpostman.com/collections/b6cc1873340f3865ff2a


                      ////  JSONObject Object_Url = object_request.getJSONObject("url");
//                        System.out.println(Object_Url.toString());
//                        // System.out.println(Object_Url);
//                        //  String raw = Object_Url.getString("raw");
//                        if(object_request.has("hostarray"))
//                        {
//
//                            JSONArray hostarray = Object_Url.getJSONArray("host");
//                            String host1 = hostarray.getString(0);
//                            String host2 = hostarray.getString(1);
//                            String host3 = hostarray.getString(2);
//                            //    System.out.println(raw);
//                            System.out.println(host1 + host2 + host3);
//                        }
//                        else {
//
//                            System.out.println("no host");
//                        }





                    }
                    PrintLog("found item");

                }



            }
            if (info_Object.has("protocol-profile-behavior")) {
                System.out.println("protocol-profile-behavior");
            }
            if (info_Object.has("auth")) {
                System.out.println("found auth");
            }
            if (info_Object.has("protocol-profile-behavior")) {
                System.out.println("protocol-profile-behavior");
            }
            if (info_Object.has("protocol-config")) {
                System.out.println("protocol-config");
            }
            if (info_Object.has("request")) {
                System.out.println("request");
            }
            if (info_Object.has("response")) {
                System.out.println("response");
            }
            if (info_Object.has("response")) {
                System.out.println("response");
            }
            if (info_Object.has("script")) {
                System.out.println("script");
            }
            if (info_Object.has("url")) {
                System.out.println("url");
            }
            if (info_Object.has("variable-list")) {
                System.out.println("variable-list");
            }
            if (info_Object.has("variable")) {
                System.out.println("variable");
            }
            if (info_Object.has("version")) {
                System.out.println("version");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
return 0;

    }

    public static void PrintLog(String s) {

        int value = s.length();
        int total = 100 - value;
        int point = total / 2;

        //System.out.println(value);
        // System.out.println(total);
        //  System.out.println(point);

        for (int i = 0; i < point; i++) {


            System.out.print("=");
        }
        System.out.print(s);

        for (int i = point + value; i < 100; i++) {
            System.out.print("=");
        }
        System.out.println("");
    }



}
