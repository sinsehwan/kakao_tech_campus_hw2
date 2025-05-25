package com.example.calendar.calendar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ScheduleRequestDto {
    private Long id; // 수정, 삭제 용도

    // step 6 : validation
    @NotBlank(message = "할 일은 필수 입력 값입니다.")
    @Size(max = 200, message = "200자 제한")
    private String todo;
    @NotBlank(message = "작성자는 필수 입력 값입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String author;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;
}
