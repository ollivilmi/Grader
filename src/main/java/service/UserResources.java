package service;

import controller.model.RegisterForm;
import org.apache.commons.math3.util.Pair;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import service.entities.GraderUser;
import service.entities.UserRepository;
import service.security.Authorization;
import service.security.CredentialValidation;

@RestController
@RequestMapping(value = {"/userApi/"})
public class UserResources {

    // Login / Register service
    @Autowired
    private Authorization auth;

    @Autowired
    private CredentialValidation validation;

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

    @PostMapping(path="/updateUserInformation")
    public ResponseEntity changeCredentials(Authentication authentication, @RequestBody RegisterForm form){
        Pair<Boolean, String> headers = validation.modifyUser(authentication, form);
        return new ResponseEntity(headers.getValue(), headers.getKey() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
