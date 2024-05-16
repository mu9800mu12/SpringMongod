package kopo.poly.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    @PostMapping(value = "saveStringJSON")
    public ResponseEntity saveStringJSON(@RequestBody RedisDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + "[컨트롤러] : saveStringJSON 시작!");

        log.info("pDTO : " + pDTO);

        RedisDTO rDTO = Optional.ofNullable(myRedisSerivce.saveStringJSON(pDTO))
                .orElseGet(() -> RedisDTO.builder().build());

        log.info(this.getClass().getName() + "[컨트롤러] : saveStringJSON 끝!");

        return ResponseEntity.ok(
                CommonResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), rDTO)
        );
    }

    @PostMapping(value = "saveList")
    public ResponseEntity saveList(@RequestBody List<RedisDTO> pList) throws Exception {

        log.info(this.getClass().getName() + "[컨트롤러] : saveList 시작!");

        log.info("pList : " + pList);

        List<String> rList = Optional.ofNullable(myRedisSerivce.saveList(pList))
                .orElseGet(ArrayList::new);

        log.info(this.getClass().getName() + "[컨트롤러] : saveList 끝!");

        return ResponseEntity.ok(
                CommonResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), rList));

    }

    @PostMapping(value = "saveListJSON")
    public ResponseEntity saveListJSON(@RequestBody List<RedisDTO> pList) throws Exception {

        log.info(this.getClass().getName() + "[컨트롤러] : saveListJSON 시작!");

        log.info("pList : " + pList);

        List<RedisDTO> rList = Optional.ofNullable(myRedisSerivce.saveListJSON(pList))
                .orElseGet(ArrayList::new);

        log.info(this.getClass().getName() + "[컨트롤러] : saveListJSON 끝!");

        return ResponseEntity.ok(
                CommonResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), rList));

    }

    @PostMapping(value = "saveHash")
    public ResponseEntity saveHash(@RequestBody RedisDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + "[컨트롤러] : saveHash 시작!");

        log.info("pDTO : " + pDTO);

        RedisDTO rDTO = Optional.ofNullable(myRedisSerivce.saveHash(pDTO))
                .orElseGet(() -> RedisDTO.builder().build());

        log.info(this.getClass().getName() + "[컨트롤러] : saveHash 끝!");

        return ResponseEntity.ok(
                CommonResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), rDTO));

    }

    @PostMapping(value = "saveSetJSON")
    public ResponseEntity saveSetJSON(@RequestBody List<RedisDTO> pList) throws Exception {

        log.info(this.getClass().getName() + "[컨트롤러] : saveSetJSON 시작!");

        log.info("pList : " + pList);

        Set<RedisDTO> rSet = Optional.ofNullable(myRedisSerivce.saveSetJSON(pList))
                .orElseGet(HashSet::new);

        log.info(this.getClass().getName() + "[컨트롤러] : saveSetJSON 끝!");

        return ResponseEntity.ok(
                CommonResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), rSet));

    }

    @PostMapping(value = "saveZSetJSON")
    public ResponseEntity saveZSetJSON(@RequestBody List<RedisDTO> pList) throws Exception {

        log.info(this.getClass().getName() + "[컨트롤러] : saveZSetJSON 시작!");

        log.info("pList : " + pList);

        Set<RedisDTO> rSet = Optional.ofNullable(myRedisSerivce.saveSetJSON(pList))
                .orElseGet(HashSet::new);

        log.info(this.getClass().getName() + "[컨트롤러] : saveZSetJSON 끝!");

        return ResponseEntity.ok(
                CommonResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), rSet));

    }
}