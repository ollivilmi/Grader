package service.security;

import controller.model.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import service.GraderUser;
import service.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class Authorization {
    
    @Autowired
    private UserRepository userRepository;
    
    private static final Logger log = LoggerFactory.getLogger(Authorization.class);
    
    public boolean login(LoginForm loginForm)
    {
        try {
            GraderUser user = userRepository.findByUsername(loginForm.getUsername()).get(0);  
            return BCrypt.checkpw(loginForm.getPassword(), user.getPasswordHash());
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    public boolean register(LoginForm loginForm)
    {
        try {
            String username = loginForm.getUsername();
            String hash = BCrypt.hashpw(loginForm.getPassword(), BCrypt.gensalt());
            userRepository.save(new GraderUser(username, hash));
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
