package com.practice.ecommerce.payloads;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginCredential {

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    private String password;
}
