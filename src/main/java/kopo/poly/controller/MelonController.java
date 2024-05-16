package kopo.poly.controller;

import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kopo.poly.controller.response.CommonResponse;
import kopo.poly.dto.MelonDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.persistance.mongodb.IMelonMapper;
import kopo.poly.service.IMelonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/melon/v1")
@RestController
public class MelonController {

    private final IMelonService melonService;


    @PostMapping(value = "collectMelonSong")
    public ResponseEntity collectMelonSong() throws Exception {
        // 수집 결과 출력
        String msg = "";

        int res = melonService.collectMelonSong();

        if (res == 1) {
            msg = "멜론차트 수집 성공!";

        } else {
            msg = "멜론차트 수집 실패!";

        }

        MsgDTO dto = MsgDTO.builder().result(res).msg(msg).build();

        log.info(this.getClass().getName() + "collectMelonSong End!");

        return ResponseEntity.ok(
                CommonResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), dto));


    }

    @PostMapping(value = "getSongList")
    public ResponseEntity getSongList() throws Exception {

        log.info(this.getClass().getName() + ".getSongList Start!");

        List<MelonDTO> rList = Optional.ofNullable(melonService.getSongList())
                .orElseGet(ArrayList::new);

        log.info(this.getClass().getName() + "getSongList End!");

        return ResponseEntity.ok(
                CommonResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), rList));

    }

    /**
     * 기수별 수집된 노래의 수 가져오기
     */
    @PostMapping(value = "getSingerSongCnt")
    public ResponseEntity getSingerSongCnt() throws Exception {

        log.info(this.getClass().getName() + ".getSingerSongCnt Start!");

        // Java 8 부터 제공되는 Optional 활용하여 NPE(Null Pointer Exception) 처리
        List<MelonDTO> rList = Optional.ofNullable(melonService.getSingerSongCnt())
                .orElseGet(ArrayList::new);

        log.info(this.getClass().getName() + ".getSingerSongCnt End!");

        return ResponseEntity.ok(
                CommonResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), rList));
    }

    /**
     * 가수 이름으로 조회하기
     */
    @PostMapping(value = "getSingerSong")
    public ResponseEntity getSingerSong(@RequestBody MelonDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getSingerSong Start!");

        log.info("pDTO : " + pDTO);

        // Java 8 부터 제공되는 Optional 활용하여 NPE(Null Pointer Exception) 처리
        List<MelonDTO> rList = Optional.ofNullable(melonService.getSingerSong(pDTO))
                .orElseGet(ArrayList::new);

        log.info(this.getClass().getName() + ".getSingerSong End!");

        return ResponseEntity.ok(
                CommonResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), rList));
    }

    @PostMapping(value = "dropCollection")
    public ResponseEntity dropCollection() throws Exception {

        log.info(this.getClass().getName() + "드랍켈렌션 시작");

        String msg = "";

        int res = melonService.dropCollection();

        if (res == 1) {
            msg = "멜론차트 삭제 성공!";
        } else {
            msg = "멜론차트 삭제 실패!";
        }
        MsgDTO dto = MsgDTO.builder().result(res).msg(msg).build();

        log.info(this.getClass().getName() + "드랍켈렌션 끝");

        return ResponseEntity.ok(
                CommonResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), dto));

    }

    @PostMapping(value = "insertManyField")
    public ResponseEntity insertManyField() throws Exception {

        log.info(this.getClass().getName() + "인서트매니필드 시작");

        List<MelonDTO> rList = Optional.ofNullable(melonService.insertManyField())
                .orElseGet(ArrayList::new);

        log.info(this.getClass().getName() + "인서트매니필드 끝");

        return ResponseEntity.ok(
                CommonResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), rList));

    }

    @PostMapping(value = "updateField")
    public ResponseEntity updateField(@RequestBody MelonDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + "업데이트 필드 스타트");

        log.info("pDTO : " + pDTO);

        List<MelonDTO> rList = Optional.ofNullable(melonService.updateField(pDTO))
                .orElseGet(ArrayList::new);

        log.info(this.getClass().getName() + "업데이트 필드 스타트");

        return ResponseEntity.ok(
                CommonResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), rList));
    }

    @PostMapping(value = "updateAddField")
    public ResponseEntity updateAddField(@RequestBody MelonDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + " 업데이트 추가필드 컨트롤러 시작");

        log.info("pDTO : " + pDTO);

        List<MelonDTO> rList = Optional.ofNullable(melonService.updateAddField(pDTO))
                .orElseGet(ArrayList::new);

        log.info(this.getClass().getName() + " 업데이트 추가필드 컨트롤러 끝");

        return ResponseEntity.ok(
                CommonResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), rList));
    }

    @PostMapping(value = "updateAddListField")
    public ResponseEntity updateAddListField(@RequestBody MelonDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + "유저리스트필드 추가 시작");

        log.info("pDTO : " + pDTO);

        List<MelonDTO> rList = Optional.ofNullable(melonService.updateFieldAndAddField(pDTO))
                .orElseGet(ArrayList::new);

        log.info(this.getClass().getName() + "유저리스트필드 추가 끝");

        return ResponseEntity.ok(
                CommonResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), rList));

    }

    @PostMapping(value = "updateFieldAndAddField")
    public ResponseEntity updateFieldAndAddField(@RequestBody MelonDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + "updateFieldAndAddField Start!");

        log.info("pDTO :" + pDTO );

        List<MelonDTO> rList = Optional.ofNullable(melonService.updateFieldAndAddField(pDTO))
                .orElseGet(ArrayList::new);

        log.info(this.getClass().getName() + "updateFieldAndAddField End!");

        return ResponseEntity.ok(
                CommonResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), rList));

    }

    @PostMapping(value = "deleteDocument")
    public ResponseEntity deleteDocument(@RequestBody MelonDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + "deleteDocument Start!");

        log.info("pDTO :" + pDTO );

        List<MelonDTO> rList = Optional.ofNullable(melonService.updateFieldAndAddField(pDTO))
                .orElseGet(ArrayList::new);

        log.info(this.getClass().getName() + "deleteDocument End!");

        return ResponseEntity.ok(
                CommonResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), rList));

    }

}
