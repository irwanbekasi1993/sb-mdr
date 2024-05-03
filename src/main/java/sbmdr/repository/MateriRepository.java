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
import sbmdr.model.Materi;
import sbmdr.model.Semester;
import sbmdr.model.Soal;

@Repository
public interface MateriRepository extends JpaRepository<Materi, String> {

	@Modifying
	@Query(value = "insert into materi (kode_materi,jenis_materi,isi_materi) "
			+ "values(:kodeMateri,:jenisMateri,:isiMateri)", nativeQuery = true)
	@Transactional
	int insertDataMateri(@Param("kodeMateri")String kodeMateri, @Param("jenisMateri")String jenisMateri,
			@Param("isiMateri")String isiMateri);
	
	@Query(value="select * from materi limit :limit", nativeQuery=true)
	List<Materi> selectAllMateri(@Param("limit")int limit);
	
	@Modifying
	@Query(value="update materi set jenis_materi=:jenisMateri, isi_materi=:isiMateri where kode_materi=:kodeMateri", nativeQuery=true)
	@Transactional
	int updateDataMateri(@Param("jenisMateri")String jenisMateri,@Param("isiMateri")String isiMateri,
			@Param("kodeMateri")String kodeMateri);
	
	@Modifying
	@Query(value="delete from materi where kode_materi=:kodeMateri", nativeQuery=true)
	@Transactional
	int deleteDataMateri(@Param("kodeMateri")String kodeMateri);
	
	@Query(value="select * from materi where kode_materi=:kodeMateri", nativeQuery=true)
	Materi getMateriByKodeMateri(@Param("kodeMateri")String kodeMateri);
	
	@Query(value="select count(kode_materi) from materi ", nativeQuery=true)
	int hitungMateri();
	
	@Query(value="select kode_materi from materi order by kode_materi desc limit 1", nativeQuery=true)
	String getLastKodeMateri();
}
