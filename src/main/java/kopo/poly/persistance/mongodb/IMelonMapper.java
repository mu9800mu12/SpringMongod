package kopo.poly.persistance.mongodb;

import java.util.List;
import kopo.poly.dto.MelonDTO;

public interface IMelonMapper {

    /**
     *
     * @param pList 저장될 정보
     * @param colNm 저장할 컬렌션 이름
     * @return 저장결과
     */
    int insertSong(List<MelonDTO> pList, String colNm) throws Exception;

    /**
     *
     * @param colNm 조회할 컬렉션 이름
     * @return 노래 리스트
     * @throws Exception
     */
    List<MelonDTO> getSongList(String colNm) throws Exception;


    List<MelonDTO> getSingerSongCnt(String colNm) throws Exception;

    List<MelonDTO> getSingerSong(String colNm, MelonDTO pDTO) throws Exception;

    /**
     *
     * @param colNm
     * @return
     * @throws Exception
     */
    int dropCollection(String colNm) throws Exception;

    int insertManyField(String colNm, List<MelonDTO> pList) throws Exception;

    /**
     * 필드 값 수정하기
     * 예 : 가수의 이름 수정하기
     *
     * @param colNm 저장될 컬렉션 이름
     * @param pDTO  수정할 가수이름, 수정될 가수 이름 정보
     * @return 저장 결과
     */
    int updateField(String colNm, MelonDTO pDTO) throws Exception;

    /**
     * 수정된 가수이름의 노래 가져오기
     *
     * @param colNm 조회할 컬렉션 이름
     * @param pDTO 가수명
     * @return 노래 리스트
     */
    List<MelonDTO> getUpdateSinger(String colNm, MelonDTO pDTO) throws Exception;

    /**
     *
     * @param colNm
     * @param pDTO
     * @return 저장결과
     */
    int updateAddField(String colNm, MelonDTO pDTO) throws Exception;

    /**
     *
     * @param colNm
     * @param pDTO
     * @return 노래 리스트
     */
    List<MelonDTO> getSingerSongNickname(String colNm, MelonDTO pDTO) throws Exception;

    /**
     *
     * @param colNm
     * @param pDTO
     * @return 저장결과
     */
    int updateAddListField(String colNm, MelonDTO pDTO) throws Exception;

    /**
     *
     * @param colNm
     * @param pDTO
     * @return 리스트
     */

    List<MelonDTO> getSingerSongMember(String colNm, MelonDTO pDTO) throws Exception;

    /**
     *  가수 이름 노래 제목 수정 및 신규 필드 추가(복합 수정하기)
     *
     * @param colNm
     * @param pDTO
     * @return 저장결과
     */
    int updateFieldAndAddField(String colNm, MelonDTO pDTO) throws Exception;

    /**
     * 가수의 노래 가져오기(임의 추가한 필드 포함 조회)
     *
     * @param colNm
     * @param pDTO
     * @return 노래 리스트
     */
    List<MelonDTO> getSingerSongAddData(String colNm, MelonDTO pDTO) throws Exception;

    /**
     * 노래 삭제
     *
     * @param colNm
     * @param pDTO
     * @return 저장결과
     */
    int deleteDocument(String colNm, MelonDTO pDTO) throws Exception;
}
