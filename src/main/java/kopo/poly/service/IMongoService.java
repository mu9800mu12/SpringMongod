package kopo.poly.service;

import kopo.poly.dto.MongoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

public interface IMongoService {

    // 간단한 데이터 저장
    int mongoTest(MongoDTO pDTO) throws Exception;

}
