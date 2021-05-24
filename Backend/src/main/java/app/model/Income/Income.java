package app.model.Income;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Income {

    //Parameters
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    private Long userId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotEmpty
    private Integer amount;

    @NotEmpty
    private LocalDateTime date;

    @NotEmpty
    private Boolean programmed;

    //Constructor
    public Income() {}

    public Income(Long userId, String name, String description, Integer amount, LocalDateTime date, Boolean programmed) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.programmed = programmed;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Boolean getProgrammed() {
        return programmed;
    }

    public void setProgrammed(Boolean programmed) {
        this.programmed = programmed;
    }

    public Boolean isAvailable(){
        return this.amount > 0;
    }

    public Boolean isProgrammed(Boolean programmed){
        return this.programmed;
    }

}
