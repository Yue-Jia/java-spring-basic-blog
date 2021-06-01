package com.yueejia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String cmtContent;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private ZonedDateTime zonedDateTime;
    @ManyToOne(fetch = FetchType.LAZY)
    private BlogPost blogPost;

    public String getDateStr() {
        DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss ");
        return formatter.format(this.zonedDateTime);
    }

    public Comment(String cmtContent){
        this.cmtContent = cmtContent;
    }
}
