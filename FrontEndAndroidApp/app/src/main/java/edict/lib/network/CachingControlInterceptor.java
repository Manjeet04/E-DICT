package edict.lib.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CachingControlInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException
    {

        Request request = chain.request();

        // add cache control only for GET methods

        if (request.method().equals("GET") && request.headers().get("Cache-Control") == null) {

           request = request.newBuilder()
                    .header("Cache-Control", "public, max-stale=60")
                    .build();

        // max-stale denotes that we won't send a new request until a request is 60 seconds old

        }


        Response originalResponse = chain.proceed(request);
        return originalResponse.newBuilder()
            .header("Cache-Control", "max-age=60")
            .build();

        // max-age denotes that after 60 seconds the cached response will be destroyed
    }
}