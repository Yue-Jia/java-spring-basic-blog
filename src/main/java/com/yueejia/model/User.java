package com.yueejia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@NoArgsConstructor
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String name;
    @Email
    private String email;
    private String password;
    private String avatar;
    private ZonedDateTime lastLogin;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<BlogPost> blogPost = new ArrayList<BlogPost>();
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Comment> comment = new ArrayList<>();
    @ManyToMany(fetch= FetchType.EAGER)
    private List<Role> role = new ArrayList<>();


}
