package app.dto;

public class UserDTO {

    private String name;
    private String email;
    private String password;

    public UserDTO(String name, String email, String password){

        this.name = name;
        this.email = email;
        this.password = password;

    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
