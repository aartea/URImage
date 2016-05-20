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

    public void doRequest(){
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(
            "http://",null, new JsonHttpResponseHandler(){
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
