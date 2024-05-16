package kopo.poly.service;

import java.util.List;
import kopo.poly.dto.MelonDTO;

public interface IMelonService {

    /**
     *  멜론 노래 리스트 저장하기
     */
    int collectMelonSong() throws Exception;

    /**
     * 오늘 수집된 멜론 노래리스트 가져오기
     */
    List<MelonDTO> getSongList() throws Exception;
    /**
     *
     * @param colNm 조회할 컬렉션 이름
     * @return 노래 리스트
     */
    List<MelonDTO> getSingerSongCnt() throws Exception;

    List<MelonDTO> getSingerSong(MelonDTO pDTO) throws Exception;
    /**
     * 수집된 멜론 차트 저장된 몽고디비 컬렌션 삭제
     */
    int dropCollection() throws Exception;

    /**
     * 멜론 노래 리스트 한 번에 저장하기
     */
    List<MelonDTO> insertManyField() throws Exception;

    /**
     * singer 필드의 값인 방탄소년단을 BTS로 변경하기
     */
    List<MelonDTO> updateField(MelonDTO pDTO) throws Exception;

    /**
     * BTs 노래에 member 필드 추가하고,
     * 그member 필드에 BTS 멤버 이름들을 List로 저장하기
     */
    List<MelonDTO> updateAddField(MelonDTO pDTO) throws Exception;

    /**
     * BTS 노래에 member필드 추가하고,
     * 그 member 필드에 BTS 멤버 이름들을 List 저장하기
     */
    List<MelonDTO> updateFieldAndAddField(MelonDTO pDTO) throws Exception;

    /**
     * 방탄 노래 삭제하기
     */
    public List<MelonDTO> deleteDocument(MelonDTO pDTO) throws Exception;

}
