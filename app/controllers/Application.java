package controllers;

import com.google.gson.Gson;
import com.interact911.sso.DevSsoService;
import play.Logger;
import play.mvc.*;

import java.util.*;

import models.*;
import play.mvc.results.Forbidden;

public class Application extends Controller {

    public static DevSsoService ssoService = DevSsoService.INSTANCE;

    public static void index() {
        render();
    }

    /*
     * curl -i -X GET -H "Authorization: c63673eb-71ed-4523-8823-a9ed5a708089" http://localhost:9000/api/v2/Default/auth
     */
    public static void authVerify(String tenantName) {
        String token = request.headers.get("authorization").value();
        renderJSON(ssoService.newValidateTokenJob(token).doJobWithResult());
    }

    /*
     * curl http://localhost:9000/api/v2/help
     */
    public static void help() {
        List<String> help = new ArrayList<String>();
        help.add("Help: curl http://localhost:9000/api/v2/help");
        help.add("Login: curl -i -H \"Content-Type: application/json\" -H \"Accept: application/json\" -X POST -d \"{'username':'jeff1234','password':'password'}\" http://localhost:9000/api/v2/Default/auth");
        help.add("Logout: curl -i -X DELETE -H \"Authorization: 41fe294f-85d6-4964-b6e2-553dac434d48\" http://localhost:9000/api/v2/Default/auth");
        help.add("Verify: curl -i -X GET -H \"Authorization: c63673eb-71ed-4523-8823-a9ed5a708089\" http://localhost:9000/api/v2/Default/auth");
        renderJSON(help);
    }

    /*
     * curl -i -H "Content-Type: application/json" -H "Accept: application/json" -X POST -d "{'username':'jeff1234','password':'password'}" http://localhost:9000/api/v2/Default/auth
     */
    public static void login(String tenantName, String username, String password) {
        SystemLogin sysLogin;

        if (username == null && password == null) {
            String[] body = params.getAll("body");
            sysLogin = new Gson().fromJson(body[0], SystemLogin.class);
        } else {
            sysLogin = new SystemLogin();
            sysLogin.username = username;
            sysLogin.password = password;
        }

        String token = ssoService.newLoginJob(sysLogin.username, sysLogin.password).doJobWithResult();
        if (token == null) {
            throw new Forbidden("Invalid credentials");
        } else {
            renderJSON(token);
        }
    }

    /*
     * curl -i -X DELETE -H "Authorization: 41fe294f-85d6-4964-b6e2-553dac434d48" http://localhost:9000/api/v2/Default/auth
     */
    public static void logout(String tenantName) {
        String token = request.headers.get("authorization").value();
        renderJSON(ssoService.newLogoutJob(token).doJobWithResult());
    }

}