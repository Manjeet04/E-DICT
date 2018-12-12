package edict.lib.network;

/**
 * Created by Necra on 24-12-2017.
 */

public abstract class OnNetworkResponseListener {

    final Class<?> typeParameterClass;
    public static final int DEFAULT = 200;

    public OnNetworkResponseListener(Class<?> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public Class<?> getTypeParameterClass() {
        return typeParameterClass;
    }

    public abstract void onResponse(Object response);
    public abstract void onError(String error, int code);

}