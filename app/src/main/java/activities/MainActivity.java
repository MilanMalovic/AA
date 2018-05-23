package activities;

import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import adapters.RecyclerListAdapter;
import db.DbManager;
import model.Beer;
import rest.APIClient;
import rest.BeerEndPoint;
import retrofit2.Call;
import retrofit2.Response;
import velimir.beersapp.R;

public class MainActivity extends AppCompatActivity {

    public static final String SAVED_LIST = "SAVED_LIST";
    private RecyclerView recyclerView;
    private RecyclerListAdapter adapter;
    private List<Beer> beerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        if(savedInstanceState == null){
            setContent();
        } else {
            beerList = savedInstanceState.getParcelableArrayList(SAVED_LIST);
        }

        adapter = new RecyclerListAdapter(this, beerList);
        recyclerView.setAdapter(adapter);



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVED_LIST, (ArrayList) beerList);
    }



    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void setContent(){

        if(!hasConnected()) {
            // No Internet connection
            // Get data from Database
            Toast.makeText(this,"No Internet Connection. Reading data from Database.",Toast.LENGTH_LONG).show();
            new GetDataFromDb().execute();



        } else {
            // 1. Send Request to the Server
            // 2. Insert or Update Database
            new GetBeers().execute();
            new InserOrUpdateDb().execute(beerList);
        }
    }

    private boolean hasConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


       public class GetBeers extends AsyncTask<Void, Void, List<Beer>> {
        final List<Beer> lista = new ArrayList<>();


            @Override
            protected List<Beer> doInBackground(Void... voids) {

                BeerEndPoint apiService = APIClient.getClient().create(BeerEndPoint.class);
                Call<List<Beer>> call = apiService.getBeers();

                try {
                    Response<List<Beer>> response = call.execute();
                    if(response.isSuccessful()){
                        lista.addAll(response.body());
                    } else {
                        Log.i("RESPONS ---- > ", response.message());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return lista;
            }

            @Override
            protected void onPostExecute(List<Beer> beers) {
                beerList.addAll(beers);
                adapter.notifyDataSetChanged();
            }


    }

    public class GetDataFromDb extends AsyncTask<Void,Void,List<Beer>> {

      private List<Beer> lista = new ArrayList<>();

        @Override
        protected List<Beer> doInBackground(Void... voids) {
            lista.addAll(DbManager.readAll(MainActivity.this));
            return lista;
        }

        @Override
        protected void onPostExecute(List<Beer> beers) {
            beerList.clear();
            beerList.addAll(beers);
            adapter.notifyDataSetChanged();
        }
    }

    public class InserOrUpdateDb extends AsyncTask <List<Beer>, Void, Void> {


        @Override
        protected Void doInBackground(List<Beer>... lists) {
            DbManager.insertOrUpdate(MainActivity.this, beerList);
            return null;
        }
    }




}
