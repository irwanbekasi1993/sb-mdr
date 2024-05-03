package sbmdr.repository.redis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sbmdr.model.Dosen;
import sbmdr.model.Jurusan;
import sbmdr.model.MataKuliah;
import sbmdr.model.redis.RedisJurusan;

@Repository
public interface RedisJurusanRepository extends CrudRepository<RedisJurusan, String> {

RedisJurusan findByKodeJurusan(String kodeJurusan);

}
