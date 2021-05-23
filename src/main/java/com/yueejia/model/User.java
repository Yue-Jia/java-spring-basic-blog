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
    private String name;
    @Email
    private String email;
    private String password;
    private String avatar;
    private ZonedDateTime lastLogin;
    @OneToMany(cascade = CascadeType.ALL)
    private List<BlogPost> blogPost = new ArrayList<BlogPost>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comment = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Role> role = new ArrayList<>();

    public User(String name,String email,String password){
        this.name = name;
        this.email= email;
        this.password= password;
        this.avatar="";
        this.blogPost=new ArrayList<BlogPost>();
        this.comment= new ArrayList<Comment>();
        this.lastLogin= null;
        this.role=new ArrayList<Role>();
    }
}
