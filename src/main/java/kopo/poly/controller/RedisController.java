package kopo.poly.controller;

import java.util.Optional;
import kopo.poly.controller.response.CommonResponse;
import kopo.poly.dto.RedisDTO;
import kopo.poly.service.IMyRedisSerivce;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Redis;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping(value = "/redis/v1")
@RequiredArgsConstructor
@RestController
public class RedisController {

    private final IMyRedisSerivce myRedisSerivce;

    @PostMapping(value = "saveString")
    public ResponseEntity saveString(@RequestBody RedisDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + "[컨트롤러] : saveString 시작");

        log.info("pDTO : " + pDTO);

        RedisDTO rDTO = Optional.ofNullable(myRedisSerivce.saveString(pDTO))
                        .orElseGet(() -> RedisDTO.builder().build());

        log.info(this.getClass().getName() + "[컨트롤러] : saveString 끝");

        return ResponseEntity.ok(
                CommonResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), rDTO));


    }

}
