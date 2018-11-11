//11/5/2018 code Adapted from: https://github.com/tonikami/

package fall2018.csc2017.LaunchCentre;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Register Requester
 * @var REGISTER_REQUEST_URL: link to the server php file
 * @var params: Map of strings returned to request
 */
public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://kayyzz.com/Register.php";
    private Map<String, String> params;


    /**
     * Constructor that sends params to server
     */
    public RegisterRequest(String name, String email, String username, String password, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
