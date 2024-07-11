package com.example.GradProJM.Model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PasswordChangeChecking {
    private String oldPass;
    private String newPass;
    private String newPass2;
}
