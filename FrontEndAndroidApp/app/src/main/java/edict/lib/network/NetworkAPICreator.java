package edict.lib.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Necra on 10-03-2018.
 */

public class NetworkAPICreator {

    public static NetworkAPI create(String ROOT_URL) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        NetworkAPI api = retrofit.create(NetworkAPI.class);

        return api;

    }

    public static NetworkAPI create(String ROOT_URL, OkHttpClient diskCacheClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(diskCacheClient)
                .build();

        NetworkAPI api = retrofit.create(NetworkAPI.class);

        return api;

    }
}
