package app.dto;

import java.time.LocalDateTime;

public class ExpenseDTO {

    public String type;
    public String name;
    public String description;
    public Integer amount;
    public LocalDateTime date;
    public Boolean programmed;

    public ExpenseDTO(String type, String name, String description, Integer amount, LocalDateTime date, Boolean programmed){
        this.type = type;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.programmed = programmed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}