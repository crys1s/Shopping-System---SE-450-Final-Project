package depaul.edu;

import java.util.HashMap;
import java.util.Map;

public class UserAuthentication {
    private static UserAuthentication instance;
    private Map<String, String> users;

    public UserAuthentication() {
        users = new HashMap<>();
        users.put("joel", "java");
        users.put("sysadmin", "toor");
        users.put("admin", "toor");
    }

    public static synchronized UserAuthentication getInstance() {
        if (instance == null) {
            instance = new UserAuthentication();
        }
        return instance;
    }

    public boolean authenticate(String username, String password) {
        if (users.containsKey(username)) {
            return users.get(username).equals(password);
        }
        return false;
    }
}
