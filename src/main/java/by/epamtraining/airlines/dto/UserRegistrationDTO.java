package by.epamtraining.airlines.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegistrationDTO {

    @NotNull
    @Size(min = 2, max = 30)
    private String email;


    @NotNull
    @Size(min = 3, max = 50)
    private String name;


    @NotNull
    @Size(min = 3, max = 50)
    private String password;

    private String password2;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
