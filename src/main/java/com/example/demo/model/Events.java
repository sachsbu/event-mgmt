package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Events {

    private String title;
    private String id;
    private String dateStatus;
    private String timeZone;
    @DateTimeFormat(pattern="yyyy-MM-ddTHH:mm:ss")
    private Date startDate;
    private List<IdDto> artists;
    private IdDto venue;
}
