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

    LinkedList<String> items;
    ArrayAdapter<String> mAdapter;


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
        String id, secret, server, farm, address = null;

        client.get(
            "FLICKR_BASE_URL + FLICKR_PHOTOS_SEARCH_STRING + APIKEY_SEARCH_STRING + TAGS_STRING" + parameter + "FORMAT_STRING",null,
                new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    {
                        //Need to reconstruct and grab JSON object for image! Return image when done.
                        try {
                            JSONObject jsonObject = response.getJSONObject("photos");
                            JSONArray jArray = jsonObject.getJSONArray("photo");

                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject photo = jArray.getJSONObject(i);
                                if (!photo.has("url_l")) continue;
                                items.add(photo.getString("url_l"));

                            }
                            mAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        responseHandler.handleResponse(items.get(0));
                    }
                }

            });
        }

    public interface ApiResponseHandler{
        void handleResponse(String response);
    }
}
