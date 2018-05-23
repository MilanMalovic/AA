package rest;

import java.util.List;

import model.Beer;
import retrofit2.Call;
import retrofit2.http.GET;

public interface BeerEndPoint {

    @GET("beers?page=2&per_page=80")
    Call<List<Beer>> getBeers();
}
