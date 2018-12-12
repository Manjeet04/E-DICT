package edict.lib.network;

import android.content.Context;
import android.util.Log;
import android.util.LruCache;

import edict.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NetworkService {

    private static NetworkAPI emergencyAPI;
    private static NetworkAPI userAPI;
    private static NetworkAPI dataEntryAPI;
    private static NetworkAPI searchAPI;
    private static NetworkAPI educationAPI;
    private static NetworkAPI transportAPI;
    private static NetworkAPI demographyAPI;


    public static final int EMERGENCY_API = 1;
    public static final int USER_API = 2;
    public static final int DATA_ENTRY_API = 3;
    public static final int SEARCH_API = 4;
    public static final int EDUCATION_API=6;
    public static final int TRANSPORT_API=7;
    public static final int DEMOGRAPHY_API=8;

    private static LruCache<String, String> apiObservables;    // in memory cache

    private static NetworkService networkService;   // singleton object of our service

    private static OkHttpClient diskCacheClient;


    private NetworkService(Context context){

        apiObservables = new LruCache<>(100);

        try {
            setDiskCacheClient(context);
        }
        catch(Exception e) {
            Log.e("ApplicationLevelError", "Could not initialise disk cache client");
        }

        initialiseEmergencyAPI(context);
        initialiseUserAPI(context);
        initialiseDataEntryAPI(context);
        initialiseSearchAPI(context);
        initialiseEducationAPI(context);
        initialiseTransportAPI(context);
        initialiseDemographyAPI(context);

    }

    public static void createInstance(Context context) {
        if(networkService == null) {
            networkService = new NetworkService(context);
        }
    }


    public static NetworkService getInstance() {
        return networkService;
    }



    private void initialiseEmergencyAPI(Context context) {

        String ROOT_URL_EMERGENCY_SERVICE = context.getResources().getString(R.string.ROOT_URL_DICT);

        emergencyAPI = NetworkAPICreator.create(ROOT_URL_EMERGENCY_SERVICE, diskCacheClient);

    }


    private void initialiseUserAPI(Context context) {

        String ROOT_URL_USER_SERVICE = context.getResources().getString(R.string.ROOT_URL_SIGN_IN);

        userAPI = NetworkAPICreator.create(ROOT_URL_USER_SERVICE, diskCacheClient);

    }


    private void initialiseDataEntryAPI(Context context) {

        String ROOT_URL_DATA_ENTRY_SERVICE = context.getResources().getString(R.string.ROOT_URL_DATA_ENTRY);

        dataEntryAPI = NetworkAPICreator.create(ROOT_URL_DATA_ENTRY_SERVICE, diskCacheClient);

    }


    private void initialiseSearchAPI(Context context) {

        String ROOT_URL_EMERGENCY_SERVICE = context.getResources().getString(R.string.ROOT_URL_SEARCH);

        searchAPI = NetworkAPICreator.create(ROOT_URL_EMERGENCY_SERVICE, diskCacheClient);

    }

    private void initialiseEducationAPI(Context context) {

        String ROOT_URL_EDUCATION_SERVICE = "http://prod-education.cityexploro.com/";

        educationAPI= NetworkAPICreator.create(ROOT_URL_EDUCATION_SERVICE);

    }

    private void initialiseDemographyAPI(Context context) {
        String ROOT_URL_DEMOGRAPHY = "http://uat-demography.cityexploro.com";
        demographyAPI = NetworkAPICreator.create(ROOT_URL_DEMOGRAPHY);

    }

    private void initialiseTransportAPI(Context context) {

        String ROOT_URL_TRANSPORT_SERVICE = "http://prod-transport.cityexploro.com/";

        transportAPI= NetworkAPICreator.create(ROOT_URL_TRANSPORT_SERVICE);

    }




    public NetworkAPI getEmergencyAPI() {
        return emergencyAPI;
    }


    public NetworkAPI getUserAPI() {
        return userAPI;
    }


    public NetworkAPI getDataEntryAPI() {
        return dataEntryAPI;
    }


    public  NetworkAPI getSearchAPI() {
        return searchAPI;
    }

    public  NetworkAPI getEducationAPI() {
        return educationAPI;
    }

    public  NetworkAPI getTransportAPI() {
        return transportAPI;
    }

    public NetworkAPI getDemographyAPI() {
        return demographyAPI;
    }


    public static void addCacheEntry(String ID, String response) {
        apiObservables.put(ID, response);
    }


    public static void deleteCacheEntry(String ID) {
        apiObservables.remove(ID);
    }


    public Observable<Response<ResponseBody>> getPreparedObservable(Observable<Response<ResponseBody>> unPreparedObservable, String id, boolean useCache){

        // method for cacheable requests

        Observable<Response<ResponseBody>> preparedObservable = null;

        if(useCache && apiObservables.get(id) != null)
        {
            // return cached response
            Response<ResponseBody> resp = Response.success(ResponseBody.create(MediaType.parse("application/json"), apiObservables.get(id)));
            preparedObservable = Observable.just(resp);
            preparedObservable.observeOn(AndroidSchedulers.mainThread());
            return preparedObservable;
        }

        // fetch response from network

        preparedObservable = unPreparedObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        return preparedObservable;

    }


    public Observable<Response<ResponseBody>> getPreparedObservable(Observable<Response<ResponseBody>> unPreparedObservable){

        // method for non cacheable requests

        Observable<Response<ResponseBody>> preparedObservable = unPreparedObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        return preparedObservable;

    }


    // only for development purposes
    // this function adds the certificate from our raw resources to trusted certificates
    private static SSLContext getSSLConfig(Context context) throws CertificateException, IOException,
            KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        // Loading CAs from an InputStream
        CertificateFactory cf = null;
        cf = CertificateFactory.getInstance("X.509");

        Certificate ca;
        // I'm using Java7. If you used Java6 close it manually with finally.
        try {
            InputStream cert = context.getResources().openRawResource(R.raw.ssl_certificate);
            ca = cf.generateCertificate(cert);
        }
        catch(Exception e) {
            return null;
        }

        // Creating a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore   = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        // Creating a TrustManager that trusts the CAs in our KeyStore.
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // Creating an SSLSocketFactory that uses our TrustManager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);


        return sslContext;
    }


    // call this function when the application starts to initialise our client
    public static void setDiskCacheClient(Context context) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10MB cache memory required
        Cache cache = new Cache(new File(context.getCacheDir(), "cityexploro_cache"), SIZE_OF_CACHE);


        // verify our hostname for development purposes (SSL verification)
        HostnameVerifier myHostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                //return true;
                HostnameVerifier hv =
                        HttpsURLConnection.getDefaultHostnameVerifier();
                return hv.verify("cityexploro.com", session);
            }
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                //.sslSocketFactory(getSSLConfig(context).getSocketFactory())
                //.hostnameVerifier(myHostnameVerifier)
                .addNetworkInterceptor(new CachingControlInterceptor())
                .build();
        diskCacheClient = client;
    }
}
