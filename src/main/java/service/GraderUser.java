package service;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GraderUser {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String username;
    private String passwordHash;
    
    protected GraderUser() {}
    
    public GraderUser(String username, String passwordHash)
    {
        this.username = username;
        this.passwordHash = passwordHash;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
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
