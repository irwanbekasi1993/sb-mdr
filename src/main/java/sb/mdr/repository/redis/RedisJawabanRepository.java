package sb.mdr.repository.redis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sb.mdr.model.Dosen;
import sb.mdr.model.Jawaban;
import sb.mdr.model.Semester;
import sb.mdr.model.Soal;
import sb.mdr.model.redis.RedisJawaban;

@Repository
public interface RedisJawabanRepository extends CrudRepository<RedisJawaban, String> {
}
