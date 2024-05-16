package kopo.poly.service.impl;

import java.util.List;
import java.util.Set;
import kopo.poly.dto.RedisDTO;
import kopo.poly.persistance.redis.IMyRedisMapper;
import kopo.poly.service.IMyRedisSerivce;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyRedisService implements IMyRedisSerivce {

    private final IMyRedisMapper myRedisMapper;

    @Override
    public RedisDTO saveString(RedisDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + "[서비스] : saveString 시작!");

        String redisKey = "myRedis_String";

        RedisDTO rDTO = null;

        int res = myRedisMapper.saveString(redisKey, pDTO);

        if (res == 1) {
            rDTO = myRedisMapper.getString(redisKey);
        } else {
            log.info("Redis 저장 실패");
            new Exception("Redis 저장 실패!!");
        }

        log.info(this.getClass().getName() + "[서비스] : saveString 끝!");

        return rDTO;
    }

    @Override
    public RedisDTO saveStringJSON(RedisDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + "[서비스] : saveStringJSON 시작!");

        String redisKey = "myRedis_String_JSON";

        RedisDTO rDTO = null;

        int res = myRedisMapper.saveStringJSON(redisKey, pDTO);

        if (res == 1) {
            rDTO = myRedisMapper.getStringJSON(redisKey);

        } else {
            log.info("공습 경보!!! 공습 경보!! 레디스 저장실패!!");
            throw new Exception("레디스 저장 실패!!");
        }

        log.info(this.getClass().getName() + "[서비스] : saveStringJSON 끝!");

        return rDTO;
    }

    @Override
    public List<String> saveList(List<RedisDTO> pList) throws Exception {

        log.info(this.getClass().getName() + "[서비스] : List String 시작!");

        String redisKey ="myRedis_List";

        List<String> rList = null;

        int res = myRedisMapper.saveList(redisKey, pList);

        if(res == 1) {
            rList = myRedisMapper.getList(redisKey);

        } else {
            log.info("지성현공화국 공습 경보 !!! 레디스 저장 실패 !!");
            throw new Exception("Reids 저장 실패");
        }

        log.info(this.getClass().getName() + "[서비스] : List String 끝!");


        return rList;
    }

    @Override
    public List<RedisDTO> saveListJSON(List<RedisDTO> pList) throws Exception {
        log.info(this.getClass().getName() + "[서비스] : saveList JSON 시작!");

        String redisKey ="myRedis_List_JSON";

        List<RedisDTO> rList = null;

        int res = myRedisMapper.saveListJSON(redisKey, pList);

        if(res == 1) {
            rList = myRedisMapper.getListJSON(redisKey);

        } else {
            log.info("지성현공화국 공습 경보 !!! 레디스 저장 실패 !!");
            throw new Exception("Reids 저장 실패");
        }

        log.info(this.getClass().getName() + "[서비스] : saveList JSON 끝!");


        return rList;
    }

    @Override
    public RedisDTO saveHash(RedisDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + "[서비스] : saveHash 시작!");

        String redisKey ="myRedis_Hash";

        RedisDTO rDTO;

        int res = myRedisMapper.saveHash(redisKey, pDTO);

        if(res == 1) {
            rDTO = myRedisMapper.getHash(redisKey);

        } else {
            log.info("지성현공화국 공습 경보 !!! 레디스 저장 실패 !!");
            throw new Exception("Reids 저장 실패");
        }

        log.info(this.getClass().getName() + "[서비스] : saveHash 끝!");


        return rDTO;
    }

    @Override
    public Set<RedisDTO> saveSetJSON(List<RedisDTO> pList) throws Exception {
        log.info(this.getClass().getName() + "[서비스] : saveSetJSON 시작!");

        String redisKey ="myRedis_Set_JSON";

        Set<RedisDTO> rSet;

        int res = myRedisMapper.saveSetJSON(redisKey, pList);

        if(res == 1) {
            rSet = myRedisMapper.getSetJSON(redisKey);

        } else {
            log.info("지성현공화국 공습 경보 !!! 레디스 저장 실패 !!");
            throw new Exception("Reids 저장 실패");
        }

        log.info(this.getClass().getName() + "[서비스] : saveSetJSON 끝!");


        return rSet;
    }

    @Override
    public Set<RedisDTO> saveZSetJSON(List<RedisDTO> pList) throws Exception {
        log.info(this.getClass().getName() + "[서비스] : saveZSetJSON 시작!");

        String redisKey ="myRedis_ZSet_JSON";

        Set<RedisDTO> rSet;

        int res = myRedisMapper.saveZSetJSON(redisKey, pList);

        if(res == 1) {
            rSet = myRedisMapper.getZSetJSON(redisKey);

        } else {
            log.info("지성현공화국 공습 경보 !!! 레디스 저장 실패 !!");
            throw new Exception("Reids 저장 실패");
        }

        log.info(this.getClass().getName() + "[서비스] : saveZSetJSON 끝!");


        return rSet;
    }
}
