package badassapps.aaron.urimage;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by aaron on 5/20/2016.
 */
public class ImageGrab {
    private static ImageGrab instance;
    private static ApiResponseHandler responseHandler;

    private static final String FLICKR_BASE_URL = "http://api.flickr.com/services/rest/?method=";
    private static final String FLICKR_PHOTOS_SEARCH_STRING = "flickr.photos.search";

    private static final String APIKEY_SEARCH_STRING = "&api_key=f5762a90a3a7e08e4aab2ff5b8904107";

    private static final String TAGS_STRING = "&tags=";
    private static final String FORMAT_STRING = "&nojsoncallback=1&format=json";

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

    //Does AsyncHttpClient resolve if wifi is open/closed? Check this!
    public void doRequest(String parameter){
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(
            "FLICKR_BASE_URL + FLICKR_PHOTOS_SEARCH_STRING + APIKEY_SEARCH_STRING + TAGS_STRING" + parameter + "FORMAT_STRING",null, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    String address = null;

                    try {
                        JSONArray results = response.getJSONArray("results");
                        JSONObject location = (JSONObject) results.get(0);
                        address = location.getString("formatted_address");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    responseHandler.handleResponse(address);
                }

            });
        }

    public interface ApiResponseHandler{
        void handleResponse(String response);
    }
}
