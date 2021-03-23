package sb.mdr.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sb.mdr.model.Semester;

@Repository
public interface SemesterRepository extends CrudRepository<Semester, String> {

	@Modifying
	@Query(value = "insert into semester (kode_semester,period,waktu_masuk,waktu_keluar,ganjil_genap)"
			+ " values(:kodeSemester, :period, :waktuMasuk, :waktuKeluar, :ganjilGenap)", nativeQuery = true)
	@Transactional
	int insertDataSemester(@Param("kodeSemester")String kodeSemester, @Param("period")int period, 
			@Param("waktuMasuk")Date waktuMasuk,
			@Param("waktuKeluar")Date waktuKeluar,
			@Param("ganjilGenap")String ganjilGenap
			);
	
	@Query(value="select * from semester limit :limit", nativeQuery=true)
	List<Semester> selectAllSemester(@Param("limit")int limit);
	
	@Modifying
	@Query(value="update semester set period=:period, waktu_masuk=:waktuMasuk, "
			+ "waktu_keluar=:waktuKeluar, "
			+ "ganjil_genap=:ganjilGenap"
			+ " where kode_semester=:kodeSemester", nativeQuery=true)
	@Transactional
	int updateDataSemester(@Param("period")int period,@Param("waktuMasuk")Date waktuMasuk,
			@Param("waktuKeluar")Date waktuKeluar, @Param("ganjilGenap")String ganjilGenap,
			@Param("kodeSemester")String kodeSemester);
	
	@Modifying
	@Query(value="delete from semester where kode_semester=:kodeSemester", nativeQuery=true)
	@Transactional
	int deleteDataSemester(@Param("kodeSemester")String kodeSemester);
	
	@Query(value="select * from semester where kode_semester=:kodeSemester", nativeQuery=true)
	Semester getSemesterByKodeSemester(@Param("kodeSemester")String kodeSemester);
	
	@Query(value="select count(kode_semester) from semester ", nativeQuery=true)
	int hitungSemester();
	
	@Query(value="select kode_semester from semester order by kode_semester desc limit 1", nativeQuery=true)
	String getLastKodeSemester();
}
