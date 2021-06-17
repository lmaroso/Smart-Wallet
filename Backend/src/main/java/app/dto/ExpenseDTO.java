package app.dto;

import java.time.LocalDateTime;

public class ExpenseDTO {

    public long id;
    public Long userId;
    public String name;
    public String description;
    public Double amount;
    public LocalDateTime date;
    public Boolean programmed;
    private int repetitionMilliSeconds;
    private int dayOfWeek;
    private int dayOfMonth;

    public ExpenseDTO(){}

    public ExpenseDTO(long id, Long userId, String name, String description, Double amount, LocalDateTime date,
                      Boolean programmed, int repetitionMilliSeconds, int dayOfWeek, int dayOfMonth){
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.programmed = programmed;
        this.repetitionMilliSeconds = repetitionMilliSeconds;
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;

    }
    public ExpenseDTO(Long userId, String name, String description, Double amount, LocalDateTime date,
                      Boolean programmed, int repetitionMilliSeconds, int dayOfWeek, int dayOfMonth){
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.programmed = programmed;
        this.repetitionMilliSeconds = repetitionMilliSeconds;
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
    }

    public long getId() { return id; }

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

    public Boolean getProgrammed() {
        return programmed;
    }

    public void setProgrammed(Boolean programmed) {
        this.programmed = programmed;
    }

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
