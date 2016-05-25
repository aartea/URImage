package badassapps.aaron.urimage;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by aaron on 5/20/2016.
 */
public class ImageGrab {
    private static ImageGrab instance;
    private static ApiResponseHandler responseHandler;

    private static final String FLICKR_BASE_URL = "https://api.flickr.com/services/rest/?method=";
    private static final String FLICKR_PHOTOS_SEARCH_STRING = "flickr.photos.search";

    private static final String APIKEY_SEARCH_STRING = "&api_key=f5762a90a3a7e08e4aab2ff5b8904107";

    private static final String TAGS_STRING = "&tags=";
    private static final String FORMAT_STRING = "&nojsoncallback=1&format=json";

    private JSONObject photo;

    //Empty constructor
    private ImageGrab(){
    }


    //Creates our singleton
    public static ImageGrab getInstance(ApiResponseHandler handler){
        responseHandler = handler;
        if(instance == null){
            instance = new ImageGrab();
        }
        return instance;
    }

    public void doRequest(String parameter){
        AsyncHttpClient client = new AsyncHttpClient();

        //Ensure somewhere our wifi is on/off

        client.get(
            "FLICKR_BASE_URL + FLICKR_PHOTOS_SEARCH_STRING + APIKEY_SEARCH_STRING + TAGS_STRING" + parameter + "FORMAT_STRING",null,
                new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    {
                        //Need to reconstruct and grab JSON object for image! Return image when done.
                        try {
                            responseHandler.handleResponse(response.getString("photo"));
                            }
                         catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

    public interface ApiResponseHandler{
        void handleResponse(String response);
    }
}
