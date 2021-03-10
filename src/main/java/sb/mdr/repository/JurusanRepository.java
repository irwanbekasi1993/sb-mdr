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
import sb.mdr.model.Jurusan;
import sb.mdr.model.MataKuliah;

@Repository
public interface JurusanRepository extends JpaRepository<Jurusan, String> {

	@Modifying
	@Query(value = "insert into jurusan (kode_jurusan,nama_jurusan) values(:kodeJurusan, :namaJurusan)", nativeQuery = true)
	@Transactional
	int insertDataJurusan(@Param("kodeJurusan")String kodeJurusan, @Param("namaJurusan")String namaJurusan);
	
	@Query(value="select * from jurusan limit :limit", nativeQuery=true)
	List<Jurusan> selectAllJurusan(@Param("limit")int limit);
	
	@Modifying
	@Query(value="update jurusan set nama_jurusan=:namaJurusan", nativeQuery=true)
	@Transactional
	int updateDataJurusan(@Param("namaJurusan")String namaJurusan, @Param("kodeMatkul")String kodeMatkul);
	
	@Modifying
	@Query(value="delete from jurusan where kode_jurusan=:kodeJurusan", nativeQuery=true)
	@Transactional
	int deleteDataJurusan(@Param("kodeJurusan")String kodeJurusan);
	
	@Query(value="select * from jurusan where kode_jurusan=:kodeJurusan", nativeQuery=true)
	Jurusan getJurusanByKodeJurusan(@Param("kodeJurusan")String kodeJurusan);
	
	@Query(value="select count(kode_jurusan) from jurusan ", nativeQuery=true)
	int hitungJurusan();
	
	@Query(value="select kode_jurusan from jurusan order by kode_jurusan desc limit 1", nativeQuery=true)
	String getLastKodeJurusan();
}
