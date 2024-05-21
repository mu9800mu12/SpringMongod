package kopo.poly.service.impl;

import java.util.LinkedList;
import java.util.List;
import kopo.poly.dto.MelonDTO;
import kopo.poly.persistance.mongodb.IMelonMapper;
import kopo.poly.persistance.redis.IMelonCacheMapper;
import kopo.poly.service.IMelonService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MelonService implements IMelonService {

    private final IMelonMapper melonMapper;
    
    private final IMelonCacheMapper melonCacheMapper;
    

    private List<MelonDTO> doCollect() throws Exception {
        log.info(this.getClass().getName() + "두 셀렉트 시작!");

        List<MelonDTO> pList = new LinkedList<>();

        String url = "https://www.melon.com/chart/index.htm";

        Document doc = Jsoup.connect(url).get();

        // Corrected class name
        Elements element = doc.select("div.service_list_song");

        for (Element songInfo : element.select("div.wrap_song_info")) {
            String song = CmmUtil.nvl(songInfo.select("div.ellipsis.rank01 a").text());
            String singer = CmmUtil.nvl(songInfo.select("div.ellipsis.rank02 a").eq(0).text());

            log.info("song" + song);
            log.info("singer" + singer);

            if ((song.length() > 0) && (singer.length() > 0)) {
                MelonDTO pDTO = MelonDTO.builder()
                        .collectTime(DateUtil.getDateTime("yyyyMMddhhmmss"))
                        .song(song)
                        .singer(singer)
                        .build();

                pList.add(pDTO);
            }
        }

        log.info(this.getClass().getName() + "두컬렉션 끝");
        return pList;
    }

    @Override
    public int collectMelonSong() throws Exception {
        int res = 0;

        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        List<MelonDTO> rList = this.doCollect();

        res = melonMapper.insertSong(rList, colNm);
        
        if(!melonCacheMapper.getExistKey(colNm)) { //Redis에 데이터가 없다면
            res = melonCacheMapper.insertSong(rList, colNm); // Redis에 저장하기
        }

        log.info(this.getClass().getName() + "컬렌션 맬론 송 끝");

        return res;
    }

    @Override
    public List<MelonDTO> getSongList() throws Exception {
        log.info(this.getClass().getName() + "겟송 리스트 시작");

        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");
        
        List<MelonDTO> rList;
        
        if (melonCacheMapper.getExistKey(colNm)) {  //레디스에 데이터가 없다면
            rList = melonCacheMapper.getSongList(colNm); // 저장하기
            
        } else {
            rList = melonMapper.getSongList(colNm); // 몽고디비에서 데이터 가져오기
        }

        log.info(this.getClass().getName() + "겟송 리스트 끝");
        

        return rList;
    }

    @Override
    public List<MelonDTO> getSingerSongCnt() throws Exception {

        log.info(this.getClass().getName() + "getSingerSongCnt Start! 시자아아악!!");

        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        List<MelonDTO> rList = melonMapper.getSingerSongCnt(colNm);

        log.info(this.getClass().getName() + "getSingerSongCnt 끝! 끄으으읏!!");

        return rList;
    }

    @Override
    public List<MelonDTO> getSingerSong(MelonDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getSingerSong Start!");

        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        List<MelonDTO> rList = null;

        if (this.collectMelonSong() == 1) {

            rList = melonMapper.getSingerSong(colNm, pDTO);

        }
        log.info(this.getClass().getName() + ".getSingerSong End!");

        return rList;
    }

    @Override
    public int dropCollection() throws Exception {

        log.info(this.getClass().getName() + "dropCollection Start!");

        int res = 0;

        // 몽고디비에 저장된 켈렌션 이름
        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        // 기존 수지된 멜론탑100 수집한 컬렌션 삭제하기
        res = melonMapper.dropCollection(colNm);

        log.info(this.getClass().getName() + "dropCollection End!");

        return res;
    }

    @Override
    public List<MelonDTO> insertManyField() throws Exception {

        log.info(this.getClass().getName() + "인서트매니필드 스타트");

        List<MelonDTO> rList = null; //변경된 조회 결과

        // 생성할 컬렉션 명
        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        // 몽고디비에 데이터 저장하기
        if(melonMapper.insertManyField(colNm, this.doCollect())==1){

            // 변경된 값을 확인하기 위해 몽고디비로부터 데이터 조회하기
            rList = melonMapper.getSongList(colNm);

        }
        log.info(this.getClass().getName() + "인서트매니필드 끝");

        return rList;
    }


    @Override
    public List<MelonDTO> updateField(MelonDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + " 업데이트 필드 시작");

        List<MelonDTO> rList = null;

        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        melonMapper.dropCollection(colNm);

        if (this.collectMelonSong() == 1) {

            if(melonMapper.updateField(colNm, pDTO) == 1) {

                rList = melonMapper.getUpdateSinger(colNm, pDTO);
            }
        }

        log.info(this.getClass().getName() + " 업데이트 필드 끝");


        return rList;
    }




    @Override
    public List<MelonDTO> updateAddField(MelonDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + " 업데이트 추가 필드 시작");

        List<MelonDTO> rList = null;

        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        melonMapper.dropCollection(colNm);

        if (this.collectMelonSong() == 1) {

            if (melonMapper.updateAddField(colNm, pDTO) == 1){

                rList = melonMapper.getSingerSongNickname(colNm, pDTO);
            }
        }

        log.info(this.getClass().getName() + " 업데이트 추가 필드 끝");

        return rList;
    }

    @Override
    public List<MelonDTO> updateFieldAndAddField(MelonDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + " List필드 추가 서비스 시작");

        List<MelonDTO> rList = null;

        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        melonMapper.dropCollection(colNm);

        if (this.collectMelonSong() == 1) {

            if (melonMapper.updateAddListField(colNm,pDTO) == 1) {

                rList = melonMapper.getSingerSongMember(colNm,pDTO);
            }
        }

        log.info(this.getClass().getName() + " List필드 추가 서비스 끝");

        return rList;
    }


    @Override
    public List<MelonDTO> deleteDocument(MelonDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + " 데이터 삭제 서비스 시작");

        List<MelonDTO> rList = null;

        String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

        melonMapper.dropCollection(colNm);

        if (this.collectMelonSong() == 1){

            if(melonMapper.deleteDocument(colNm, pDTO) == 1) {

                rList = melonMapper.getSongList(colNm);
            }
        }


        log.info(this.getClass().getName() + " 데이터 삭제 서비스 끝");

        return rList;
    }
}























