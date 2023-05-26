package com.example.demo.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artist {
    private String id;
    private String name;
    private String imgSrc;
    private String url;
    private int rank;
    private List<Events> events;
}
