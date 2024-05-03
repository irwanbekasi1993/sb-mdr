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
import sbmdr.model.Soal;

@Repository
public interface SoalRepository extends JpaRepository<Soal, String> {

	@Modifying
	@Query(value = "insert into soal (kode_soal,jenis_soal,isi_soal,nilai) "
			+ "values(:kodeSoal,:jenisSoal,:isiSoal,:nilai)", nativeQuery = true)
	@Transactional
	int insertDataSoal(@Param("kodeSoal")String kodeSoal, @Param("jenisSoal")String jenisSoal,
			@Param("isiSoal")String isiSoal,@Param("nilai")int nilai);
	
	@Query(value="select * from soal limit :limit", nativeQuery=true)
	List<Soal> selectAllSoal(@Param("limit")int limit);
	
	@Modifying
	@Query(value="update soal set jenis_soal=:jenisSoal, isi_soal=:isiSoal, nilai=:nilai "
			+ " where kode_soal=:kodeSoal", nativeQuery=true)
	@Transactional
	int updateDataSoal(@Param("jenisSoal")String jenisSoal,@Param("isiSoal")String isiSoal,@Param("nilai")int nilai,
			@Param("kodeSoal")String kodeSoal);
	
	@Modifying
	@Query(value="delete from soal where kode_soal=:kodeSoal", nativeQuery=true)
	@Transactional
	int deleteDataSoal(@Param("kodeSoal")String kodeSoal);
	
	@Query(value="select * from soal where jenis_soal=:jenisSoal", nativeQuery=true)
	Soal getSoalByKodeSoal(@Param("jenisSoal")String jenisSoal);
	
	@Query(value="select count(kode_soal) from soal ", nativeQuery=true)
	int hitungSoal();
	
	@Query(value="select kode_soal from soal order by kode_soal desc limit 1", nativeQuery=true)
	String getLastKodeSoal();
}
