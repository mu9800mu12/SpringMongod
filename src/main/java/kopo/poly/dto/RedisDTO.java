package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@JsonInclude
@Builder
public record RedisDTO(

        String name,
        String email,
        String addr,
        String text,
        float order // 정렬 순서
) {

}


//레디스에서 제공한 타입은 시리얼라이즈를 구조를 만들어야함

//데이터 타입을 지정을해야만 저장할때 필요함
//
//레디스에 저장할 떄
//저장명렁어 전에 저장된 데이터 타입을 명시해줘야함

