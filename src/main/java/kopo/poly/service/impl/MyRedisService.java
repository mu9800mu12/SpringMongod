package kopo.poly.service.impl;

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
}
