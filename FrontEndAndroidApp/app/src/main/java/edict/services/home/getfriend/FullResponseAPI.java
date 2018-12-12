package edict.services.home.getfriend;

import edict.models.MainResponseModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Manjeet Singh on 1/29/2018.
 */

public interface FullResponseAPI {
    @GET("{endPoint}")
    public Call<MainResponseModel> getData(@Path("endPoint") String apiEndPoint);
}
