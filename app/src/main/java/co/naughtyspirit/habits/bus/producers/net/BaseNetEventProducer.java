package co.naughtyspirit.habits.bus.producers.net;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import retrofit.RetrofitError;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/1/15.
 * *
 * * NaughtySpirit 2015
 */
public abstract class BaseNetEventProducer {


    private static final String TAG = BaseNetEventProducer.class.getName();

    protected static String getErrorMessage(RetrofitError error) {
        String message = null;

        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(
                    error.getResponse().getBody().in(), "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            JSONObject object = new JSONObject(responseStrBuilder.toString());
            message =  object.getString("message");
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            message = "Request failure!";
        }

        return message;
    }
}
