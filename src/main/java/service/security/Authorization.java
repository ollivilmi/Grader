package service.security;

import controller.model.RegisterForm;
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
    
    public boolean register(RegisterForm form)
    {
        try
        {
            String username = form.getUsername();
            if (userRepository.findByUsername(username) != null)
                throw new InvalidNameException();

            String hash = BCrypt.hashpw(form.getPassword(), BCrypt.gensalt());
            userRepository.save(new GraderUser(username, hash));
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        GraderUser user = userRepository.findByUsername(authentication.getName());
        if (!BCrypt.checkpw(authentication.getCredentials().toString(), user.getPasswordHash()))
            throw new BadCredentialsException("Authentication failed for " + authentication.getName());

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPasswordHash(), authorities);

        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
