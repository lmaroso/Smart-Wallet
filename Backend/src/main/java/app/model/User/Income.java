package app.model.User;

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
    private Integer amount;

    @NotEmpty
    private LocalDateTime date;

    @NotEmpty
    private Boolean programmed;

    //Constructor
    public Income() {}

    public Income(Integer amount, LocalDateTime date, Boolean programmed) {

        this.amount = amount;
        this.date = date;
        this.programmed = programmed;
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
