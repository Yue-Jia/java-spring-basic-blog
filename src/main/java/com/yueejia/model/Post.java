package com.yueejia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String body;
    @ManyToOne
    private User user;
    private Date date;
    private String img;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comment = new ArrayList<>();


    public String getDateStr() {
        DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
        return outputFormatter.format(this.date);
    }


}
