package com.yueejia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
    private boolean enabled = true;
    private String verification_code;
    @Enumerated(EnumType.STRING)
    private AuthenticationProvider auth_provider;
    @Email
    private String email;
    private String password;
    private String avatar;
    private ZonedDateTime lastLogin;
    @OneToMany(cascade = CascadeType.ALL)
    private List<BlogPost> blogPost = new ArrayList<BlogPost>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comment = new ArrayList<>();
    @ManyToMany(fetch= FetchType.EAGER)
    private List<Role> role = new ArrayList<>();

    public String getLastLoginStr() {
        DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss ");
        return formatter.format(this.lastLogin);
    }
    public boolean hasRole(String r){
        List<Role> roles = this.getRole();
        for(Role role: roles){
            if(role.getRoleName().endsWith(r)){
                return true;
            }

        }return false;
    }
}
