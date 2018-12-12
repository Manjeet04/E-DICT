package edict.services.home.getfriend;

/**
 * Created by Manjeet Singh on 1/8/2018.
 */
import edict.models.Response_alpha;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface API_alpha {
    @GET("{endpoint}")
    // public Call<Response_alpha> getResponse(@Path("endpoint") String endPoint, @Query("id")int id); // for ID API
    public Call<Response_alpha> getRes(@Path("endpoint") String endPoint); // "endpoint" has no significance.
}
