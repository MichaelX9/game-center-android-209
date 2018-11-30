//11/5/2018 code Adapted from: https://github.com/tonikami/

package fall2018.csc2017.launch_centre;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * The Register Requester
 */
public class RegisterRequest extends StringRequest {

    /**
     * link to the server php file
     */
    private static final String REGISTER_REQUEST_URL = "https://kayyzz.com/Register.php";

    /**
     * Map of strings returned to request
     */
    private Map<String, String> params;


    /**
     * Constructor that sends params to server
     */
    RegisterRequest(String name, String email, String username, String password,
                    Response.Listener<String> listener) {
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
