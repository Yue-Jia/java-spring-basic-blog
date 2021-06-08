package com.yueejia.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    @Email
    private String email;
    private String linkedin;
    private String gitHub;
    @Lob
    private String profile;
}
