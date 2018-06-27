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

    @PostMapping(path="/updateUserInformation")
    public Pair<Boolean, String> changeCredentials(Authentication authentication, @RequestBody RegisterForm form){

        try{
            GraderUser user = userRepository.findByUsername(authentication.getName());
            user.setUsername(form.getUsername());
            auth.checkUserName(user.getUsername());

            String password = auth.checkPasswordMatch(form);
            user.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));

            userRepository.save(user);
            return new Pair(true, "Credentials changed!");
        }
        catch (Exception e)
        {
            return new Pair(false, e.getMessage());
        }
    }
}
