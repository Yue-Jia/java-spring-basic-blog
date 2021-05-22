package com.yueejia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;
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
    private String content;
    @ManyToOne
    private User user;
    private ZonedDateTime zonedDateTime;
    @ManyToOne
    private BlogPost blogPost;
}
