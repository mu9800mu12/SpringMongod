package kopo.poly.persistance.redis;

import java.util.List;
import kopo.poly.dto.MovieDTO;

public interface IMovieMapper {

    // CGV 영화 정보 저장하기
    int insertMovie(MovieDTO pDTO, String redisKey) throws Exception;

    // 수집된 영화 정보 존재여부 체크하기
    boolean getExistKey(String redisKey) throws Exception;

    // 1시간 이내 수집 및 호출된 영화 정보 가져오기
    List<MovieDTO> getMovieList(String redisKey) throws Exception;

}
