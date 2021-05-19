package app.model.Expense;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Expense {

    //Parameters
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    private String type;

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
    public Expense() {}

    public Expense(String type, String name, String description, Integer amount, LocalDateTime date, Boolean programmed) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.programmed = programmed;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

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
