package service.security;

import controller.model.RegisterForm;
import org.apache.commons.math3.util.Pair;
import org.mindrot.jbcrypt.BCrypt;
import org.omg.CORBA.DynAnyPackage.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import service.entities.GraderUser;
import service.entities.UserRepository;

import javax.naming.InvalidNameException;

@Service
public class CredentialValidation {

    @Autowired
    UserRepository userRepository;

    public String checkUserName(String username) throws InvalidNameException {
        if (userRepository.findByUsername(username) != null || username.isEmpty())
            throw new InvalidNameException("Username already taken");
        return username;
    }

    public String checkPasswordMatch(RegisterForm form) throws InvalidValue {
        if (!form.getPassword().equals(form.getPasswordConfirmation()))
            throw new InvalidValue("Passwords don't match");
        return form.getPassword();
    }

    public GraderUser checkAuthentication(Authentication authentication)
    {
        GraderUser user = userRepository.findByUsername(authentication.getName());

        if (user == null || !BCrypt.checkpw(authentication.getCredentials().toString(), user.getPasswordHash()))
            throw new BadCredentialsException("Authentication failed for " + authentication.getName());
        return user;
    }

    public Pair<Boolean, String> modifyUser(Authentication authentication, RegisterForm form)
    {
        try {
            GraderUser user = userRepository.findByUsername(authentication.getName());
            if (!form.getUsername().equals(user.getUsername())) {
                user.setUsername(form.getUsername());
                checkUserName(user.getUsername());
            }

            String password = checkPasswordMatch(form);
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
