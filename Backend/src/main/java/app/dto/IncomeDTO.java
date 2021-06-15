package app.dto;

import java.time.LocalDateTime;

public class IncomeDTO {

    private long id;
    private Long userId;
    private String name;
    private String description;
    private Double amount;
    private LocalDateTime date;
    private Boolean programmed;
    private Boolean cancelled;
    private int repetitionMilliSeconds;
    private int dayOfWeek;
    private int dayOfMonth;

    public IncomeDTO(){}

    public IncomeDTO(Long userId, String name, String description, Double amount, LocalDateTime date,
                     Boolean cancelled, Boolean programmed, int repetitionMilliSeconds, int dayOfWeek, int dayOfMonth){
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.programmed = programmed;
        this.cancelled  = cancelled;
        this.repetitionMilliSeconds = repetitionMilliSeconds;
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
    }

    public IncomeDTO(long id, Long userId, String name, String description, Double amount, LocalDateTime date,
                     Boolean programmed, Boolean cancelled, int repetitionMilliSeconds, int dayOfWeek, int dayOfMonth){
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.programmed = programmed;
        this.cancelled  = cancelled;
        this.repetitionMilliSeconds = repetitionMilliSeconds;
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
    }

    public long getId() { return id; }

    public void setId(long id) {
        this.id = id;
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

    public Boolean getCancelled(){ return cancelled; }

    public void setProgrammed(Boolean programmed) {
        this.programmed = programmed;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
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

