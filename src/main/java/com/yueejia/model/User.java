package com.yueejia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private Date lastLogin;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Post> post = new ArrayList<Post>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comment = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Role> role = new ArrayList<>();
}
