package sbmdr.repository.redis;

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

import sbmdr.model.Dosen;
import sbmdr.model.Semester;
import sbmdr.model.redis.RedisSemester;

@Repository
public interface RedisSemesterRepository extends CrudRepository<RedisSemester, String> {
}
