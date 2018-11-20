//11/5/2018 code Adapted from: https://github.com/tonikami/
package fall2018.csc2017.LaunchCentre;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Login Activity
 */
public class LoginRequest extends StringRequest {

    /**
     * Link to the server php file
     */
    private static final String LOGIN_REQUEST_URL = "https://kayyzz.com/Login.php";

    /**
     * Map of strings returned to request
     */
    private Map<String, String> params;

    /**
     * Constructor that sends params to server
     */
    LoginRequest(String username, String password, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
