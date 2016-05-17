package mju.cs.IndoorNavi.APSearch;

import android.os.StrictMode;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joguk on 2016-05-10.
 */
public class APJSONParser {
    public List<APData> object;

    public APJSONParser() {
    }

    public void parseJSONFromURL(String url) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                JsonReader reader = new JsonReader(new InputStreamReader(new URL(url).openStream()));
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<APData>>() {
                }.getType();
                object = gson.fromJson(reader, listType);
                Log.d("오브젝트가 있다더라4", object.toString());
            } catch (Exception e) {
                Log.d("오류가 찍힌다더라", "그렇다더라");
                e.printStackTrace();
            }
        }
    }
}
