package com.MiniProject.eventregistration.entity;

import jakarta.persistence.Entity;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginDTO {
        private String email;
        private String password;
}
