package co.pamobile.pacore.Utilities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by KhoaVin on 1/19/2017.
 */

public class JsonConvert {
    public static Gson gson = new Gson();
    public static String convertToJson(Object obj)
    {
        String json = gson.toJson(obj);
        return json;
    }
    public static Object convertFromJson(String jsonString)
    {
        Object obj = gson.fromJson(jsonString, Object.class);
        return obj;
    }
    public static <T> T[] getArray(String jsonString, Class<T[]> tClass)
    {
        Type stringStringMap = new TypeToken<T[]>(){}.getType();
            T[] ts = gson.fromJson(jsonString, tClass);
        return ts;
    }
    public static String HashMapToJson(Object obj){
        return gson.toJson(obj);
    }
   public static HashMap<String,String> JsonToHashMap(String jsonString){
       Gson gson = new Gson();
       Type stringStringMap = new TypeToken<Map<String, String>>(){}.getType();
       HashMap<String,String> map = gson.fromJson(jsonString, stringStringMap);
       return map;
   }
}
