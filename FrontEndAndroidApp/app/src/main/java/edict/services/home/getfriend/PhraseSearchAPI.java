package edict.services.home.getfriend;

import edict.models.WordListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Manjeet Singh on 12/11/2018.
 */

public interface PhraseSearchAPI {
    @GET("/{endPoint}")
    public Call<WordListResponse> getWordList(@Path("endPoint") String endPoint, @Query("query") String query, @Query("fields") String fields);

}
