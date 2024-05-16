package kopo.poly.persistance.redis;

import java.util.List;
import java.util.Set;
import kopo.poly.dto.RedisDTO;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Redis;

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

    /**
     *
     * @param redisKey
     * @param pDTO
     * @return 결과 값
     */
    int saveStringJSON(String redisKey, RedisDTO pDTO) throws Exception;

    /**
     *
     * @param redisKey
     * @return
     */
    RedisDTO getStringJSON(String redisKey) throws Exception;

    /**
     * List타입에 여러 문자열로 저장하기(동기화)
     */

    int saveList(String readsKey, List<RedisDTO> pList) throws Exception;

    /**
     * List타입에 여러 문자열로 저장된 데이터 가져오기
     */
    List<String> getList(String redisKey) throws Exception;

    /**
     * List타입에 JOSN 형태로 저장하기
     */
    int saveListJSON(String redisKey, List<RedisDTO> pList) throws Exception;

    /*
     * List타입 JSON 정보 가져오기
     */
    List<RedisDTO> getListJSON(String redisKey) throws Exception;
    /*
     * Hash 타입에 문자열 현태로 저장하기
     */
    int saveHash(String redisKey, RedisDTO pDTO) throws Exception;

    /*
     * Hash 타입에 문자열 형태로 저장된 값 가져오기
     */
    RedisDTO getHash(String redisKey) throws Exception;

    /*
     * Set타입에 JSON 형태로 람다식 이용해 저장하기
     */
    int saveSetJSON(String redisKey, List<RedisDTO> pList) throws Exception;

    /*
     * Set타입에 JSON 형태로 람다식 이용하여 저장된 값 가져오기
     */
    Set<RedisDTO> getSetJSON(String redisKey) throws Exception;
    /*
     * ZSet 타입에 JSON 형태로 저장하기
     */
    int saveZSetJSON(String redisKey, List<RedisDTO> pList) throws Exception;
    /*
     * ZSet 타입에 JSON 형태로 저장된 값 가져오기
     */
    Set<RedisDTO> getZSetJSON(String redisKey) throws Exception;


}
