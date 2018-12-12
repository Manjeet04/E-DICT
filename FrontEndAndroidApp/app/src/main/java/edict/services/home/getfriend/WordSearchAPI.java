package edict.services.home.getfriend;

import edict.models.WordSearchModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Manjeet Singh on 12/11/2018.
 */

public interface WordSearchAPI {

    @GET("/{endPoint}")
    public Call<WordSearchModel> getResponse(@Path("endPoint") String endPoint, @Query("query") String query);
}
