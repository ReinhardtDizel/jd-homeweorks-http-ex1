package ru.netology;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Fact {
    private String id;
    private String text;
    private String type;
    private String user;
    private Integer upvotes;
}
