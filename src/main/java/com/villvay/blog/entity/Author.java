package com.villvay.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long id;

    private String name;

    @Column(name = "user_name")
    private String userName;

    private String email;

    private String address;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> postList;
}
