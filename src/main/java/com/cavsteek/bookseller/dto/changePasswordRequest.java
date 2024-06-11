package com.cavsteek.bookseller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class changePasswordRequest {
    private String currentPassword;
    private String newPassword;
    private String confirmationPassword;
}
