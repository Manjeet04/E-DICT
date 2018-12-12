package edict.lib.network;

import android.util.Log;

import edict.lib.image_upload.UploadImageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;

import static edict.lib.network.NetworkService.DATA_ENTRY_API;
import static edict.lib.network.NetworkService.DEMOGRAPHY_API;
import static edict.lib.network.NetworkService.EDUCATION_API;
import static edict.lib.network.NetworkService.EMERGENCY_API;
import static edict.lib.network.NetworkService.SEARCH_API;
import static edict.lib.network.NetworkService.TRANSPORT_API;
import static edict.lib.network.NetworkService.USER_API;
import static edict.lib.network.OnNetworkResponseListener.DEFAULT;


public class NetworkLayer {

    private NetworkService service;
    private List<Subscriber<Response<ResponseBody>>> subscribers;

    private static String TAG = "NetworkLayer";

    public NetworkLayer() {

        this.service = NetworkService.getInstance();

        subscribers = new ArrayList<>();

        TAG = getClass().getName();

    }


    private NetworkAPI getAPI(int serviceID) {
        switch(serviceID) {
            case EMERGENCY_API:
                return service.getEmergencyAPI();
            case USER_API:
                return service.getUserAPI();
            case DATA_ENTRY_API:
                return service.getDataEntryAPI();
            case SEARCH_API:
                return service.getSearchAPI();
            case EDUCATION_API:
                return service.getEducationAPI();
            case TRANSPORT_API:
                return service.getTransportAPI();
            case DEMOGRAPHY_API:
                return service.getDemographyAPI();
        }
        return null;
    }


    private String getRequestID(String endpoint, Map<String, String> query) {
        StringBuilder id = new StringBuilder(endpoint);
        id.append("/");

        String ID = id.substring(0, id.length() - 1);
        return ID;

    }


    private Subscriber<Response<ResponseBody>> getNewSubscriber(final String apiEndPoint, final boolean cacheResponse, final String ID,
                                                               final OnNetworkResponseListener onNetworkResponseListener) {
        Subscriber<Response<ResponseBody>> subscriber = new Subscriber<Response<ResponseBody>>() {

            @Override
            public void onCompleted() {

                this.unsubscribe();

            }

            @Override
            public void onError(Throwable e) {

                StringBuffer error = new StringBuffer(apiEndPoint);
                error.append(" : ");
                error.append(e.toString());
                e.printStackTrace();
                Log.e(TAG, error.toString());

                onNetworkResponseListener.onError("Something went wrong", DEFAULT);

            }

            @Override
            public void onNext(Response<ResponseBody> response) {

                 String result = null;

                try {

                    result = response.body().string();

                    if(cacheResponse) {

                        NetworkService.addCacheEntry(ID, result);

                    }

                    Object obj;

                    if(response.isSuccessful()) {

                        try {

                            ObjectMapper om = new ObjectMapper();

                            obj = om.readValue(result, onNetworkResponseListener.getTypeParameterClass());

                            /* for time being as "response": "success" not coming */
                            if(obj instanceof UploadImageResponse) {
                                onNetworkResponseListener.onResponse(obj);
                                return;
                            }

                            BaseResponse baseResponse = (BaseResponse) obj;

                            if(baseResponse.isSuccessful()) {

                                onNetworkResponseListener.onResponse(obj);

                            }
                            else {

                                onNetworkResponseListener.onError(baseResponse.getError(), DEFAULT);

                            }


                        }
                        catch(IOException e) {

                            StringBuffer error = new StringBuffer(apiEndPoint);
                            error.append(" : Mapping error in response");
                            Log.e(TAG, error.toString());
                            e.printStackTrace();

                            onNetworkResponseListener.onError("Error while parsing response", DEFAULT);

                        }

                    }
                    else {

                        String error = NetworkErrorHandler.handleErrorResponse(response);
                        onNetworkResponseListener.onError(error, response.code());

                    }



                }
                catch (Exception e) {

                    StringBuffer error = new StringBuffer(apiEndPoint);
                    error.append(" : No response from server");
                    Log.e(TAG, error.toString());
                    e.printStackTrace();
                    onNetworkResponseListener.onError("No response from server", DEFAULT);

                }


            }

        };

        return subscriber;
    }


    private Subscriber<Response<ResponseBody>> getNewSubscriber(final String apiEndPoint,
                                                                final OnNetworkResponseListener onNetworkResponseListener) {
        Subscriber<Response<ResponseBody>> subscriber = new Subscriber<Response<ResponseBody>>() {

            @Override
            public void onCompleted() {

                this.unsubscribe();

            }

            @Override
            public void onError(Throwable e) {

                StringBuffer error = new StringBuffer(apiEndPoint);
                error.append(" : ");
                error.append(e.toString());
                e.printStackTrace();
                Log.e(TAG, error.toString());

                onNetworkResponseListener.onError("Something went wrong", DEFAULT);

            }

            @Override
            public void onNext(Response<ResponseBody> response) {

                String result = null;

                try {

                    result = response.body().string();

                    /* only for image upload api */
                    if(onNetworkResponseListener.getTypeParameterClass() == String.class) {
                        if(response.isSuccessful()) {
                            onNetworkResponseListener.onResponse("success");
                        }
                        else {
                            onNetworkResponseListener.onError("failure", DEFAULT);
                        }
                        return;
                    }

                    Object obj;

                    if(response.isSuccessful()) {

                        try {

                            ObjectMapper om = new ObjectMapper();

                            obj = om.readValue(result, onNetworkResponseListener.getTypeParameterClass());

                            BaseResponse baseResponse = (BaseResponse) obj;

                            if(baseResponse.isSuccessful()) {

                                onNetworkResponseListener.onResponse(obj);

                            }
                            else {

                                onNetworkResponseListener.onError(baseResponse.getError(), DEFAULT);

                            }


                        }
                        catch(IOException e) {

                            StringBuffer error = new StringBuffer(apiEndPoint);
                            error.append(" : Mapping error in response");
                            Log.e(TAG, error.toString());

                            onNetworkResponseListener.onError("Error while parsing response", DEFAULT);

                        }

                    }
                    else {

                        String error = NetworkErrorHandler.handleErrorResponse(response);
                        onNetworkResponseListener.onError(error, response.code());

                    }



                }
                catch (Exception e) {

                    StringBuffer error = new StringBuffer(apiEndPoint);
                    error.append(" : No response from server");
                    e.printStackTrace();
                    Log.e(TAG, error.toString());
                    onNetworkResponseListener.onError("No response from server", DEFAULT);

                }


            }

        };

        return subscriber;
    }


    public void get(final int serviceID, final String apiEndPoint, Map<String, String> headers, Map<String, String> query, boolean useCache, final boolean cacheResponse, final OnNetworkResponseListener onNetworkResponseListener) {

        NetworkAPI api = getAPI(serviceID);

        final String ID = getRequestID(apiEndPoint, query);

        Observable<Response<ResponseBody>> friendResponseObservable = service.getPreparedObservable(api.get(apiEndPoint,
                        headers, query), ID, useCache);

        Subscriber<Response<ResponseBody>> subscriber = getNewSubscriber(apiEndPoint, cacheResponse, ID, onNetworkResponseListener);

        friendResponseObservable.subscribe(subscriber);

        addToSubscribers(subscriber);

    }


    public void post(int serviceID, String apiEndPoint, Map<String, String> headers, Map<String, String> query, Object object, final OnNetworkResponseListener onNetworkResponseListener) {

        NetworkAPI api = getAPI(serviceID);

        Observable<Response<ResponseBody>> friendResponseObservable = service.getPreparedObservable(api.post(apiEndPoint, headers, query, object));

        Subscriber<Response<ResponseBody>> subscriber = getNewSubscriber(apiEndPoint, onNetworkResponseListener);

        friendResponseObservable.subscribe(subscriber);

        addToSubscribers(subscriber);

    }


    public void postMultipart(NetworkAPI api, String apiEndPoint, HashMap<String, RequestBody> map, MultipartBody.Part body, final OnNetworkResponseListener onNetworkResponseListener){

        Observable<Response<ResponseBody>> friendResponseObservable =
                service.getPreparedObservable(api.postMultipart(apiEndPoint, map, body));

        Subscriber<Response<ResponseBody>> subscriber = getNewSubscriber(apiEndPoint, onNetworkResponseListener);

        friendResponseObservable.subscribe(subscriber);

        addToSubscribers(subscriber);

    }


    private synchronized void addToSubscribers(Subscriber<Response<ResponseBody>> subscriber) {

        subscribers.add(subscriber);

    }


    public void unsubscribeAll() {

        for(Subscriber<?> subscriber : subscribers) {
            if(!subscriber.isUnsubscribed()) {
                subscriber.unsubscribe();
            }
        }

        subscribers.clear();
        subscribers = null;

    }

}