package service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import controller.model.RegisterForm;
import org.apache.commons.math3.util.Pair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import service.entities.UserRepository;
import service.security.Authorization;

@RestController
@RequestMapping(value = {"/userApi/"})
public class UserResources {

    // Login / Register service
    @Autowired
    private Authorization auth;

    // Database
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/register")
    public ResponseEntity register(@RequestBody RegisterForm form){
        Pair<Boolean, String> headers = auth.register(form);
        return new ResponseEntity(headers.getValue(), headers.getKey() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path="/getCurrentUserName")
    public String getCurrentUserName(Authentication authentication)
    {
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return new JSONObject().put("username", currentUserName).toString();
        }
        return null;
    }

}
