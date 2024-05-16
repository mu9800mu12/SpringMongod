package kopo.poly.service.impl;

import kopo.poly.dto.MongoDTO;
import kopo.poly.persistance.mongodb.IMongoMapper;
import kopo.poly.service.IMongoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@RequiredArgsConstructor
@Service
public class MongoService implements IMongoService {

    private final IMongoMapper mongoMapper;
    @Override
    public int mongoTest(MongoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + "mongoTest Start");

        String colNm = "MONGODB_TEST";

        // MongoDB에 데이터 저장하기
        int res = mongoMapper.insertData(pDTO, colNm);

        log.info(this.getClass().getName() + "mongoTest End");

        return res;
    }
}
