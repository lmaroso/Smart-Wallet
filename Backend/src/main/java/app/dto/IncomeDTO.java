package app.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class IncomeDTO {

    public Integer amount;
    public LocalDateTime date;
    public Boolean programmed;

    public IncomeDTO(Integer amount, LocalDateTime date, Boolean programmed){

        this.amount = amount;
        this.date = date;
        this.programmed = programmed;

    }
}
