package kopo.poly.persistance.redis;

import kopo.poly.dto.RedisDTO;

public interface IMyRedisMapper {

    /**
     *
     * @param redisKey 레디스 저장키
     * @param pDTO 저장정보
     * @return 성공여부
     */
    int saveString(String redisKey, RedisDTO pDTO) throws Exception;

    /**
     *
     * @param redisKey 가져올 레디스 키
     * @return 결과 값
     */
    RedisDTO getString(String redisKey) throws Exception;

}
