package rukina.vivegam.android.serviceinterfaces;


import org.json.JSONObject;

/**
 * Created by zahid.r on 10/30/2015.
 */
public interface ISignUpServiceListener {
    public void onSignUp(JSONObject response);

    public void onSignUpError(String error);
}
