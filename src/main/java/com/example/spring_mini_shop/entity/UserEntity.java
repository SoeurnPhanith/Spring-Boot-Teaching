package com.example.spring_mini_shop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_users")
public class UserEntity {

    @Id
    @GeneratedValue (strategy =  GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    @JsonBackReference
    @ManyToOne //many use having one role
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @OneToMany(mappedBy = "user")
    private List<OrdersEntity> orders;
}
