package edict.lib.network;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by Necra on 23-02-2018.
 */

public class NetworkErrorHandler {

    public static String handleErrorResponse(Response<ResponseBody> response) {

        return "Something Went Wrong";

    }

}
