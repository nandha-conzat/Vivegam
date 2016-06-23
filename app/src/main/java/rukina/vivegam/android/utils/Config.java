package rukina.vivegam.android.utils;

public interface Config {

    // used to share GCM regId with application server - using php app server

    static final String BASE_URL = "http://budnetdesign.in/vivegam/api/";
    static final String APP_SERVER_URL = BASE_URL+"login?username=%s&password=%s";
    static final String REG_USER = BASE_URL+"register?name=%s&mobile_no=%s&username=%s&password=%s&gcm_registration_id=%s";

    // Google Project Number
    static final String GOOGLE_PROJECT_ID = "170935596267";
    static final String MESSAGE_KEY = "message";

    public static String PARAM_MESSAGE = "msg";
    public static final String GET_SIGN_UP_URL = "http://hobbistan.com/app/hobbistan/api.php?";
    public static final String PARAMS_FUNC_NAME = "func_name";
    public static final String PARAMS_USER_NAME = "username";

    // Alert Dialog Constants
    public static String ALERT_DIALOG_TITLE = "alertDialogTitle";
    public static String ALERT_DIALOG_MESSAGE = "alertDialogMessage";
    public static String ALERT_DIALOG_TAG = "alertDialogTag";
    public static String ALERT_DIALOG_INPUT_HINT = "alert_dialog_input_hint";
    public static String ALERT_DIALOG_POS_BUTTON = "alert_dialog_pos_button";
    public static String ALERT_DIALOG_NEG_BUTTON = "alert_dialog_neg_button";

    // Preferences
    public static final String KEY_LOG_STATUS = "log_status";
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_USER_PASSWORD = "user_password";


}
