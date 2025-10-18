package com.genomebank.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInDTO {
    private String username;
    private String email;
    private String password;
    private Boolean isActive;
    private List<Long> roleIds;
}
