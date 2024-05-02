package kopo.poly.persistance.redis.impl;

import java.util.concurrent.TimeUnit;
import kopo.poly.dto.RedisDTO;
import kopo.poly.persistance.redis.IMyRedisMapper;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MyRedisMapper implements IMyRedisMapper {

    private final RedisTemplate<String, Object> redisDB;

    private void deleteRedisKey(String redisKey) {

        if(redisDB.hasKey(redisKey)) {
            redisDB.delete(redisKey); //

            log.info("삭제 성공!");
        }
    }

    @Override
    public int saveString(String redisKey, RedisDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + "[매퍼] : .saveString 시작 ");

        int res;

        String saveData = CmmUtil.nvl(pDTO.text());

        /*
        * reids 저장 및 읽기에 대한 데이터 타입 지정 (String)
        */
        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new StringRedisSerializer());

        this.deleteRedisKey(redisKey);

        redisDB.opsForValue().set(redisKey, saveData);

        //TTL설정 2일이 지나면 자동으로 데이터가 삭제되게 설정
        redisDB.expire(redisKey, 2, TimeUnit.DAYS);

        res = 1;

        log.info(this.getClass().getName() + "[매퍼] : .saveString 끝 ");

        return res;
    }

    @Override
    public RedisDTO getString(String redisKey) throws Exception {

        log.info(this.getClass().getName() + "[매퍼] : .getString 시작 ");

        log.info("String redisKey :" + redisKey);

        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new StringRedisSerializer());

        RedisDTO rDTO = null;

        if (redisDB.hasKey(redisKey)) {
            String res = (String) redisDB.opsForValue().get(redisKey);

            log.info("res :" + res);

            rDTO = RedisDTO.builder()
                    .text(res).build();
        }

        log.info(this.getClass().getName() + "[매퍼] : .getString 끝 ");

        return rDTO;
    }
}
