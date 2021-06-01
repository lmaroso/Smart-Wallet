package app.dto;

public class ProfileDTO {

    private String name;
    private String email;

    public ProfileDTO(String name, String email){

        this.name = name;
        this.email = email;

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}