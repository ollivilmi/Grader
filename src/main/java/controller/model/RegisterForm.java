package controller.model;

public class RegisterForm {
    private String username;
    private String password;
    private String passwordConfirmation;
    
    public RegisterForm(String username, String password, String passwordConfirmation)
    {
        this.username = username;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }
    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
}
