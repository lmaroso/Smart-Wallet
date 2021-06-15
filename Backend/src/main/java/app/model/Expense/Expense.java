package app.model.Expense;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Expense {

    //Parameters
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private Long userId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotNull
    private Double amount;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private Boolean programmed;

    @NotNull
    private Boolean cancelled;

    @NotNull
    private int repetitionMilliSeconds;

    @NotNull
    private int dayOfWeek;

    @NotNull
    private int dayOfMonth;

    //Constructor
    public Expense() {}

    public Expense(Long userId, String name, String description, Double amount, LocalDateTime date, Boolean programmed,
                   Boolean cancelled, int repetitionMilliSeconds, int dayOfWeek, int dayOfMonth){

        this.userId = userId;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.programmed = programmed;
        this.cancelled = cancelled;
        this.repetitionMilliSeconds = repetitionMilliSeconds;
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
    }

    public Expense(long id, Long userId, String name, String description, Double amount, LocalDateTime date, Boolean programmed,
                   Boolean cancelled, int repetitionMilliSeconds, int dayOfWeek, int dayOfMonth) {

        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.programmed = programmed;
        this.cancelled = cancelled;
        this.repetitionMilliSeconds = repetitionMilliSeconds;
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
    }

    public long getId() { return id; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setProgrammed(Boolean programmed) {
        this.programmed = programmed;
    }

    public Boolean isAvailable(){
        return this.amount > 0;
    }

    public Boolean isProgrammed(){
        return this.programmed;
    }

    public Boolean isCancelled(){ return this.cancelled; }

    public int getRepetitionMilliSeconds(){
        return this.repetitionMilliSeconds;
    }

    public int getDayOfWeek(){
        return this.dayOfWeek;
    }

    public int getDayOfMonth(){
        return this.dayOfMonth;
    }

}
