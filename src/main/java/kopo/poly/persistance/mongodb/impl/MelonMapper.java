package kopo.poly.persistance.mongodb.impl;

import static com.mongodb.client.model.Updates.set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.print.Doc;
import kopo.poly.dto.MelonDTO;
import kopo.poly.persistance.mongodb.AbstractMongoDBComon;
import kopo.poly.persistance.mongodb.IMelonMapper;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MelonMapper extends AbstractMongoDBComon implements IMelonMapper {

    private final MongoTemplate mongodb;


    @Override
    public int insertSong(List<MelonDTO> pList, String colNm) throws Exception {

        log.info(this.getClass().getName() + "insertSong Start");

        int res = 0;

        if (pList == null) {
            pList = new LinkedList<>();
        }

        super.createCollection(mongodb, colNm, "collectTime");

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        for (MelonDTO pDTO : pList) {

            col.insertOne(new Document(new ObjectMapper().convertValue(pDTO, Map.class)));

        }

        res = 1;
        log.info(this.getClass().getName() + "insertSong End");

        return res;
    }

    @Override
    public List<MelonDTO> getSongList(String colNm) throws Exception {

        log.info(this.getClass().getName() + "getSongList Start 시자아악");

        //조회 결과를 전달하기 위한 객체 생성하기
        List<MelonDTO> rList = new LinkedList<>();

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        // 조회 결과 중 출력할 컬럼들(SQL의 SELECT절과 FROM절 가운데 컬럼들과 유사함
        Document projection = new Document();
        projection.append("song", "$song");
        projection.append("singer", "$singer");

        // MongoDB는 무조건 ObjectId가 자동생성되며, 오브젝트아이디는 사용하지 않을 때 조회할 필요가 없음
        // 오브젝트아이디를 가지고 오지 않을 때 사용함
        projection.append("_id", 0);

        // 몽고디비의 find 명령어를 통해 조회할 경우 사용함
        // 조회하는 데이터의 양이 적은 경우, find를 사용하고, 데이터양이 많은 경우 무조건 Aggregate 사용한다.
        FindIterable<Document> rs = col.find(new Document()).projection(projection);

        for (Document doc : rs) {
            String song = CmmUtil.nvl(doc.getString("song"));
            String singer = CmmUtil.nvl(doc.getString("singer"));

            log.info("song : " + song + "singier" + singer);

            MelonDTO rDTO = MelonDTO.builder().song(song).singer(singer).build();

            // 레코드 결과를List에 저장하기
            rList.add(rDTO);
        }
        log.info(this.getClass().getName() + "getSongList End! 끄으으읕");

        return rList;
    }

    @Override
    public List<MelonDTO> getSingerSongCnt(String colNm) throws Exception {

        log.info(this.getClass().getName() + "가수 이름 가져오기 숫자세기 시작");

        // 조회 결과를 전달하기 위한 객체 생성하기
        List<MelonDTO> rList = new LinkedList<>();

        // MelonDB 조회 커리
        List<? extends Bson> pipeline = Arrays.asList(
                new Document()
                        .append("$group",
                                new Document().append("_id",
                                                new Document().append("singer", "$singer"))
                                        .append("COUNT(singer)",
                                                new Document().append("$sum", 1))),
                new Document()
                        .append("$project",
                                new Document().append("singer", "$_id.singer")
                                        .append("singerCnt", "$COUNT(singer)")
                                        .append("_id", 0)
                        ),
                new Document()
                        .append("$sort", new Document()
                                .append("singerCnt", -1)
                        )
        );

        MongoCollection<Document> col = mongodb.getCollection(colNm);
        AggregateIterable<Document> rs = col.aggregate(pipeline).allowDiskUse(true);

        for (Document doc : rs) {
            String singer = doc.getString("singer");
            int singerCnt = doc.getInteger("singerCnt", 0);

            log.info("singer : " + singer + "/ singerCnt : " + singerCnt);

            MelonDTO rDTO = MelonDTO.builder()
                    .singer(singer)
                    .singerCnt(singerCnt)
                    .build();

            rList.add(rDTO);

        }

        log.info(this.getClass().getName() + "getSingerSongCnt End! 끄으으으으읕");

        return rList;
    }


    @Override
    public List<MelonDTO> getSingerSong(String colNm, MelonDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getSingerSong Start!");

        // 조회 결과를 전달하기 위한 객체 생성하기
        List<MelonDTO> rList = new LinkedList<>();

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        Document query = new Document();
        query.append("singer", CmmUtil.nvl(pDTO.singer()));

        Document projection = new Document();
        projection.append("song", "$song");
        projection.append("singer", "$singer");

        projection.append("_id", 0);

        FindIterable<Document> rs = col.find(query).projection(projection);

        for (Document doc : rs) {
            String song = doc.getString("song");
            String singer = doc.getString("singer");

            log.info("song : " + song + "/ singer : " + singer);

            MelonDTO rDTO = MelonDTO.builder().song(song).singer(singer).build();

            rList.add(rDTO);
        }

        log.info(this.getClass().getName() + ".getSingerSong End!");

        return rList;
    }

    @Override
    public int dropCollection(String colNm) throws Exception {

        log.info(this.getClass().getName() + "드랍 컬렉션 시작");

        int res = 0;

        super.dropCollection(mongodb, colNm);

        res = 1;

        log.info(this.getClass().getName() + "드랍 컬렉션 끝");

        return res;
    }


    @Override
    public int insertManyField(String colNm, List<MelonDTO> pList) throws Exception {

        log.info(this.getClass().getName() + "인서트매니필드 시작");

        int res = 0;

        if (pList == null) {
            pList = new LinkedList<>();
        }

        super.createCollection(mongodb, colNm, " collectTime");

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        List<Document> list = new ArrayList<>();

        // 람다식 활용 stream과 -> 사용
        pList.parallelStream().forEach(melon ->
                list.add(new Document(new ObjectMapper().convertValue(melon, Map.class))));

        // 레코드 리스트 단위로 한번에 저장하기
        col.insertMany(list);

        res = 1;

        log.info(this.getClass().getName() + "인서트매니필드 끝");

        return res;

    }

    @Override
    public int updateField(String colNm, MelonDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + " 업데이트 필드 시작");

        int res = 0;

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        String singer = CmmUtil.nvl(pDTO.singer());
        String updateSinger = CmmUtil.nvl(pDTO.updateSinger());

        log.info("ColNm" + colNm);
        log.info("singer" + singer);
        log.info("updateSinger" + updateSinger);

        // 조회할 조건 (SQL의 WHERE역할 / SELECT * FROM MELON_20~~ WHERE SINGER ='방탄소년단')
        Document query = new Document();
        query.append("singer", singer);

        // 몽고디비 데이터 수정은 반드시 컬렉션을 조회하고 조회된 오브젝트 아이디를 기반으로 데이터를 수정함
        // 몽고디비 환경은 분산환경으로 구성될수 있기 때문에 정확한 pK에 매핑하기 위해서임
        FindIterable<Document> rs = col.find(query);

        // 람다식 활용하여 컬렉션에 조회된 데이터들을 수정하기
        rs.forEach(doc -> col.updateOne(doc,
                new Document("$set", new Document("singer", updateSinger))));

        res = 1;

        log.info(this.getClass().getName() + " 업데이트 필드 끝");

        return res;
    }

    @Override
    public int updateAddField(String colNm, MelonDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + " 업데이트 추가 필드 시작");

        int res = 0;

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        String singer = CmmUtil.nvl(pDTO.singer());
        String nickname = CmmUtil.nvl(pDTO.nickname());

        log.info("colNm" + colNm);
        log.info("singer" + singer);
        log.info("nickname" + nickname);

        Document query = new Document();
        query.append("singer", singer);

        FindIterable<Document> rs = col.find(query);

        rs.forEach(doc -> col.updateOne(doc, set("nickname", nickname)));

        res = 1;

        log.info(this.getClass().getName() + " 업데이트 추가 필드 끝");

        return res;
    }

    @Override
    public List<MelonDTO> getSingerSongNickname(String colNm, MelonDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + " 수정된 결과 조회 시작");

        List<MelonDTO> rList = new LinkedList<>();

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        Document query = new Document();
        query.append("singer", CmmUtil.nvl(pDTO.singer()));

        Document projection = new Document();
        projection.append("song", "$song");
        projection.append("singer", "$singer");
        projection.append("nickname", "$nickname");

        projection.append("_id", 0);

        FindIterable<Document> rs = col.find(query).projection(projection);

        for (Document doc : rs) {

            String song = CmmUtil.nvl(doc.getString("song"));
            String singer = CmmUtil.nvl(doc.getString("singer"));
            String nickname = CmmUtil.nvl(doc.getString("nickname"));

            log.info("song" + song + "/ singer" + singer + "/nickname" + nickname);

            MelonDTO rDTO = MelonDTO.builder()
                    .singer(singer)
                    .song(song)
                    .nickname(nickname)
                    .build();

            rList.add(rDTO);

        }

        log.info(this.getClass().getName() + " 수정된 결과 조회 끝");

        return rList;
    }

    @Override
    public int updateAddListField(String colNm, MelonDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + " 업데이트추가 리스트 필드 매퍼 시작");

        int res = 0;

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        String singer = CmmUtil.nvl(pDTO.singer());
        List<String> member = pDTO.member();

        log.info("singer" + singer);
        log.info("member" + member);

        Document query = new Document();
        query.append("singer", singer);

        FindIterable<Document> rs = col.find(query);

        rs.forEach(doc -> col.updateOne(doc, set("member", member)));

        res = 1;

        log.info(this.getClass().getName() + " 업데이트추가 리스트 필드 매퍼 끝");

        return res;
    }

    @Override
    public List<MelonDTO> getSingerSongMember(String colNm, MelonDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + " 가수 가수 멤머 가져오기 시작");

        List<MelonDTO> rList = new LinkedList<>();

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        Document query = new Document();
        query.append("singer", CmmUtil.nvl(pDTO.singer()));

        Document projection = new Document();
        projection.append("song", "$song");
        projection.append("singer", "$singer");
        projection.append("member", "$member");

        projection.append("_id", 0);

        FindIterable<Document> rs = col.find(query).projection(projection);

        for (Document doc : rs) {

            String song = CmmUtil.nvl(doc.getString("song"));
            String singer = CmmUtil.nvl(doc.getString("singer"));
            List<String> member = doc.getList("member", String.class, new ArrayList<>());

            log.info("song :" + song + "/ singer :" + singer + "/ member" + member);

            MelonDTO rDTO = MelonDTO.builder()
                    .member(member)
                    .song(song)
                    .singer(singer)
                    .build();

            rList.add(rDTO);
        }

        log.info(this.getClass().getName() + " 가수 가수 멤머 가져오기 끝");

        return rList;
    }


    @Override
    public List<MelonDTO> getUpdateSinger(String colNm, MelonDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + " 업데이트 가수 시작");

        // 조회 결과를 전달하기 우한 객체 생성하기
        List<MelonDTO> rList = new LinkedList<>();

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        Document query = new Document();
        query.append("singer", CmmUtil.nvl(pDTO.updateSinger()));

        Document projection = new Document();
        projection.append("song", "$song");
        projection.append("singer", "$singer");

        projection.append("_id", 0);

        FindIterable<Document> rs = col.find(query).projection(projection);

        for (Document doc : rs) {

            String song = CmmUtil.nvl(doc.getString("song"));
            String singer = CmmUtil.nvl(doc.getString("singer"));

            log.info("song : " + song + "/singer : " + singer);

            MelonDTO rDTO = MelonDTO.builder().singer(singer).song(song).build();

            rList.add(rDTO);

        }

        log.info(this.getClass().getName() + " 업데이트 가수 끝");

        return rList;
    }

    @Override
    public int updateFieldAndAddField(String colNm, MelonDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + " 업데이트 필드 수정및 추가 시작");

        int res = 0;

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        String singer = CmmUtil.nvl(pDTO.singer());
        String updateSinger = CmmUtil.nvl(pDTO.updateSinger());
        String addFieldValue = CmmUtil.nvl(pDTO.addFieldValue());

        log.info("pColNm :" + colNm);
        log.info("pDTO :" + pDTO);

        Document query = new Document();
        query.append("singer", singer);

        FindIterable<Document> rs = col.find(query);

        Document updateDoc = new Document();
        updateDoc.append("singer", updateDoc);
        updateDoc.append("addData", addFieldValue);

        rs.forEach(doc -> col.updateOne(doc, new Document("$set",updateDoc)));

        res = 1;

        log.info(this.getClass().getName() + " 업데이트 필드 수정및 추가 끝");

        return res;
    }

    @Override
    public List<MelonDTO> getSingerSongAddData(String colNm, MelonDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + " 수정된 가수 이름으로 조회 및 추가된 필드 조회 시작");

        List<MelonDTO> rList = new LinkedList<>();

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        Document query = new Document();
        query.append("singer", CmmUtil.nvl(pDTO.updateSinger()));

        Document projection = new Document();
        projection.append("song", "$song");
        projection.append("singer", "$singer");
        projection.append("addDataA", "$addData");

        projection.append("_id", 0);

        FindIterable<Document> rs = col.find(query);

        for (Document doc : rs) {

            String song = CmmUtil.nvl(doc.getString("song"));
            String singer = CmmUtil.nvl(doc.getString("singer"));
            String addData = CmmUtil.nvl(doc.getString("addData"));

            log.info("song :" + song + "/singer :" + singer + "/addData :" + addData);

            MelonDTO rDTO = MelonDTO.builder()
                    .singer(singer)
                    .song(song)
                    .addFieldValue(addData)
                    .build();

            rList.add(rDTO);
        }
        log.info(this.getClass().getName() + " 수정된 가수 이름으로 조회 및 추가된 필드 조회 끝");

        return rList;
    }

    @Override
    public int deleteDocument(String colNm, MelonDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + "delete Start!");

        int res =0;

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        String singer = CmmUtil.nvl(pDTO.singer());

        log.info("pColNm :" + colNm);
        log.info("pDTO :" + pDTO);

        Document query = new Document();
        query.append("singer", singer);

        FindIterable<Document> rs = col.find(query);

        rs.forEach(col::deleteOne);

        res =1;

        log.info(this.getClass().getName() + "delete End!");


        return res;
    }
}

