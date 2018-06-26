package service.security;

import controller.model.RegisterForm;
import org.omg.CORBA.DynAnyPackage.InvalidValue;
import org.apache.commons.math3.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import service.entities.GraderUser;
import service.entities.UserRepository;

import javax.naming.InvalidNameException;
import java.util.ArrayList;
import java.util.List;

@Service
public class Authorization implements AuthenticationProvider {
    
    @Autowired
    private UserRepository userRepository;
    
    public Pair<Boolean, String> register(RegisterForm form)
    {
        try
        {
            String username = checkUserName(form.getUsername());
            String password = checkPasswordMatch(form);

            String hash = BCrypt.hashpw(password, BCrypt.gensalt());
            userRepository.save(new GraderUser(username, hash));
            return new Pair(true, "Account created");
        }
        catch (Exception e)
        {
            return new Pair(false, e.getMessage());
        }
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        GraderUser user = checkAuthentication(authentication);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPasswordHash(), authorities);

        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private String checkUserName(String username) throws InvalidNameException{
        if (userRepository.findByUsername(username) != null || username.isEmpty())
            throw new InvalidNameException("Username already taken");
        return username;
    }

    private String checkPasswordMatch(RegisterForm form) throws InvalidValue{
        if (!form.getPassword().equals(form.getPasswordConfirmation()))
            throw new InvalidValue("Passwords don't match");
        return form.getPassword();
    }

    private GraderUser checkAuthentication(Authentication authentication)
    {
        GraderUser user = userRepository.findByUsername(authentication.getName());

        if (user == null || !BCrypt.checkpw(authentication.getCredentials().toString(), user.getPasswordHash()))
            throw new BadCredentialsException("Authentication failed for " + authentication.getName());
        return user;
    }

}
