package kopo.poly.persistance.redis;

import java.util.List;
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



}
