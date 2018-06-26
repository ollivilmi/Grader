package service;

import controller.model.RegisterForm;
import org.apache.commons.math3.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
