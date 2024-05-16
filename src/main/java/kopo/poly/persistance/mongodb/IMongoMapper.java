package kopo.poly.persistance.mongodb;

import java.util.List;
import kopo.poly.dto.MelonDTO;
import kopo.poly.dto.MongoDTO;

public interface IMongoMapper {

    int insertData(MongoDTO pDTO, String colNm) throws Exception;

}
