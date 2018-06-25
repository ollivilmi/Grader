package service.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class GraderUser {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String username;
    @NotNull
    private String passwordHash;
    private String role;
    
    protected GraderUser() {}
    
    public GraderUser(String username, String passwordHash)
    {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = "ROLE_USER";
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getRole()
    {
        return role;
    }
    
    public void setRole(String role)
    {
        this.role = role;
    }

    public String getUsername()
    {
        return username;
    }

    public void setPasswordHash(String passwordHash)
    {
        this.passwordHash = passwordHash;
    }
    
    public String getPasswordHash()
    {
        return passwordHash;
    }
}
