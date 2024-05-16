package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Builder;

@JsonInclude(Include.NON_DEFAULT)
@Builder
public record MelonDTO(

        @NotBlank(message = "수집 시간은 필수 입력 사항입니다.")
        String collectTime, //수집 시간
        String song, // 노래 제목
        String singer, // 가수
        int singerCnt, //차트에 들록된 가수별 노래 수
        String updateSinger, // 수정할 가수명
        String nickname,
        List<String> member,
        String addFieldValue
) {

}
