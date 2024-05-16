package kopo.poly.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MongoDTO(

        @NotBlank(message = "이름은 필수 입력 사항입니다.")
        @Size(max = 10, message = "이름은 10글자까지 입력가능합니다")
        String userName,

        @NotBlank(message = "주소는 필수 입력 사항입니다.")
        String addr,

        @NotBlank(message = "이메일은 필수 입력 사항입니다.")
        String email
) {

}
