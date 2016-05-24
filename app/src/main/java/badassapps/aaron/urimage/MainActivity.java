package badassapps.aaron.urimage;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements ImageGrab.ApiResponseHandler{
    private String query;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //Will inflate our menu's search functionality
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        lv = (ListView) findViewById(R.id.lv);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        handleIntent(getIntent());

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
      //Implicit Intent needs to handle our query!
     String query = intent.getStringExtra(SearchManager.QUERY);
    }

    //Method to use singleton, pass in string parameter to be handled by response
    @Override
    public void handleResponse(final String query) {
        //Handle with picaso imageviewer
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageGrab.getInstance(MainActivity.this).doRequest(query);

            }
        });
    }
}
