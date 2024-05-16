package kopo.poly.persistance.redis.impl;

import java.sql.Time;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kopo.poly.dto.RedisDTO;
import kopo.poly.persistance.redis.IMyRedisMapper;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
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

    @Override
    public int saveStringJSON(String redisKey, RedisDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + "[매퍼] : saveStringJSON 시작!");

        int res;

        redisDB.setKeySerializer(new StringRedisSerializer());

        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisDTO.class));

        this.deleteRedisKey(redisKey);

        redisDB.opsForValue().set(redisKey, pDTO);

        redisDB.expire(redisKey, 2, TimeUnit.DAYS);

        res =1;

        log.info(this.getClass().getName() + "[매퍼] : saveStringJSON 끝!");

        return res;
    }

    @Override
    public RedisDTO getStringJSON(String redisKey) throws Exception {

        log.info(this.getClass().getName() + "[매퍼] : getStringJSON 시작!");

        RedisDTO rDTO = null;

        redisDB.setKeySerializer(new StringRedisSerializer());

        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisDTO.class));

        if(redisDB.hasKey(redisKey)) {
            rDTO = (RedisDTO) redisDB.opsForValue().get(redisKey);
        }

        log.info(this.getClass().getName() + "[매퍼] : getStringJSON 끝!");

        return rDTO;
    }

    @Override
    public int saveList(String redisKey, List<RedisDTO> pList) throws Exception {

        log.info(this.getClass().getName() + "[매퍼] : .saveList Start!");

        int res;
        /*
         * redis 저장 및 읽기에 대한 데이터 타입 지정(String 타입으로 지정함)
         */
        redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입
        redisDB.setValueSerializer(new StringRedisSerializer()); // String 타입

        this.deleteRedisKey(redisKey); // RedisDB 저장된 키 삭제

        pList.forEach(dto -> {
//            오름차순으로 저장하기
//            redisDB.opsForList().rightPush(redisKey, CmmUtil.nvl(text));

                    // 내림차순으로 저장하기
                    redisDB.opsForList().leftPush(redisKey, CmmUtil.nvl(dto.text()));
                }
        );

        // 저장되는 데이터의 유효기간(TTL)은 5시간으로 정의
        redisDB.expire(redisKey, 5, TimeUnit.HOURS);

        res = 1;

        log.info(this.getClass().getName() + "[매퍼] : .saveList End!");

        return res;
    }

    @Override
    public List<String> getList(String redisKey) throws Exception {

        log.info(this.getClass().getName() + "[매퍼] : .getRedisList Start!");

        List<String> rList = null;
        /*
         * redis 저장 및 읽기에 대한 데이터 타입 지정(String 타입으로 지정함)
         */
        redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입
        redisDB.setValueSerializer(new StringRedisSerializer()); // String 타입

        if (redisDB.hasKey(redisKey)) {
            rList = (List) redisDB.opsForList().range(redisKey, 0, -1);

        }

        log.info(this.getClass().getName() + "[매퍼] : .getRedisList End!");

        return rList;
    }

    @Override
    public int saveListJSON(String redisKey, List<RedisDTO> pList) throws Exception {
        log.info(this.getClass().getName() + "[매퍼] : .saveListJSON Start!");

        int res;
        /*
         * redis 저장 및 읽기에 대한 데이터 타입 지정(String 타입으로 지정함)
         */
        redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisDTO.class)); // String 타입

        this.deleteRedisKey(redisKey); // RedisDB 저장된 키 삭제

        pList.forEach(dto ->
//            오름차순으로 저장하기
//            redisDB.opsForList().rightPush(redisKey, CmmUtil.nvl(text));

                    // 내림차순으로 저장하기
                    redisDB.opsForList().rightPush(redisKey, dto));



        // 저장되는 데이터의 유효기간(TTL)은 5시간으로 정의
        redisDB.expire(redisKey, 5, TimeUnit.HOURS);

        res = 1;

        log.info(this.getClass().getName() + "[매퍼] : .saveListJSON End!");

        return res;
    }

    @Override
    public List<RedisDTO> getListJSON(String redisKey) throws Exception {
        log.info(this.getClass().getName() + "[매퍼] : .getListJSON Start!");

        List<RedisDTO> rList = null;
        /*
         * redis 저장 및 읽기에 대한 데이터 타입 지정(String 타입으로 지정함)
         */

        redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisDTO.class)); // String 타입

        if (redisDB.hasKey(redisKey)) {
            rList = (List) redisDB.opsForList().range(redisKey, 0, -1);

        }

        log.info(this.getClass().getName() + "[매퍼] : .getListJSON End!");

        return rList;
    }
}
