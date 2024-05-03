package sbmdr.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sbmdr.model.Dosen;
import sbmdr.model.Semester;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, String> {

	@Modifying
	@Query(value = "insert into semester (kode_semester,periode_mulai,periode_selesai)"
			+ " values(:kodeSemester, :periodeMulai, :periodeSelesai)", nativeQuery = true)
	@Transactional
	int insertDataSemester(@Param("kodeSemester")String kodeSemester, @Param("periodeMulai") String periodeMulai,@Param("periodeSelesai") String periodeSelesai
			);
	
	@Query(value="select * from semester limit :limit", nativeQuery=true)
	List<Semester> selectAllSemester(@Param("limit")int limit);
	
	@Modifying
	@Query(value="update semester set periode_mulai=:periodeMulai , periode_selesai=:periodeSelesai "
			+ " where kode_semester=:kodeSemester", nativeQuery=true)
	@Transactional
	int updateDataSemester(@Param("periodeMulai") String periodeMulai, @Param("periodeSelesai") String periodeSelesai , @Param("kodeSemester")String kodeSemester);
	
	@Modifying
	@Query(value="delete from semester where kode_semester=:kodeSemester", nativeQuery=true)
	@Transactional
	int deleteDataSemester(@Param("kodeSemester")String kodeSemester);
	
	@Query(value="select * from semester where kode_semester=:kodeSemester ", nativeQuery=true)
	Semester getSemesterByKodeSemester(@Param("kodeSemester")String kodeSemester);
	
	@Query(value="select count(kode_semester) from semester ", nativeQuery=true)
	int hitungSemester();
	
	@Query(value="select kode_semester from semester order by kode_semester desc limit 1", nativeQuery=true)
	String getLastKodeSemester();
}
