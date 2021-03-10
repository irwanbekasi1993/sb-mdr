package sb.mdr.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sb.mdr.model.Dosen;
import sb.mdr.model.MataKuliah;

@Repository
public interface MataKuliahRepository extends JpaRepository<MataKuliah, String> {

	@Modifying
	@Query(value = "insert into matakuliah (kode_matkul,nama_matkul,sks) values(:kodeMatKul, :namaMatKul, :sks)", nativeQuery = true)
	@Transactional
	int insertDataMatKul(@Param("kodeMatKul")String kodeMatKul, @Param("namaMatKul")String namaMatKul,
			@Param("sks")int sks);
	
	@Query(value="select * from matakuliah limit :limit", nativeQuery=true)
	List<MataKuliah> selectAllMatKul(@Param("limit")int limit);
	
	@Modifying
	@Query(value="update matakuliah set nama_matkul=:namaMatKul, sks=:sks where kode_matkul=:kodeMatkul", nativeQuery=true)
	@Transactional
	int updateDataMatKul(@Param("namaMatKul")String namaMatKul,@Param("sks")int sks, @Param("kodeMatkul")String kodeMatkul);
	
	@Modifying
	@Query(value="delete from matakuliah where kode_matkul=:kodeMatKul", nativeQuery=true)
	@Transactional
	int deleteDataMatKul(@Param("kodeMatKul")String kodeMatKul);
	
	@Query(value="select * from matakuliah where kode_matkul=:kodeMatKul", nativeQuery=true)
	MataKuliah getMatkulByKodeMatKul(@Param("kodeMatKul")String kodeMatKul);
	
	@Query(value="select count(kode_matkul) from matakuliah ", nativeQuery=true)
	int hitungMatKul();
	
	@Query(value="select kode_matkul from matakuliah order by kode_matkul desc limit 1", nativeQuery=true)
	String getLastKodeMatKul();
}
