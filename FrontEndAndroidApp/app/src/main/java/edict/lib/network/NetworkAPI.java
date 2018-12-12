package edict.lib.network;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Necra on 14-09-2017.
 */


public interface NetworkAPI {


    @GET
    Observable<Response<ResponseBody>> get(@Url String url,
                                           @HeaderMap Map<String, String> headerMap,
                                           @QueryMap Map<String, String> queryMap);

    @POST
    Observable<Response<ResponseBody>> post(@Url String url,
                                            @HeaderMap Map<String, String> headerMap,
                                            @QueryMap Map<String, String> queryMap,
                                            @Body Object body);

    @Multipart
    @POST
    Observable<Response<ResponseBody>> postMultipart(@Url String url,
                                                     @PartMap() Map<String, RequestBody> partMap,
                                                     @Part MultipartBody.Part file);

}
