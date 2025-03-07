package com.example.clone.Member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@ToString
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique=true)
    private String userId;
    private String userPassword;
    private String userName;
    private String userEmail;
    @CreationTimestamp
    private String postDate;
}
