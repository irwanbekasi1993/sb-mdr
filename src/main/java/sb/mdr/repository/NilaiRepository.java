package sb.mdr.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sb.mdr.model.Dosen;
import sb.mdr.model.Nilai;
import sb.mdr.model.Semester;

@Repository
public interface NilaiRepository extends JpaRepository<Nilai, String> {

	@Modifying
	@Query(value = "insert into nilai (kode_nilai,jenis_nilai,poin)"
			+ " values(:kodeNilai,:jenisNilai,:poin)", nativeQuery = true)
	@Transactional
	int insertDataNilai(@Param("kodeNilai") String kodeNilai, @Param("jenisNilai") String jenisNilai,
			@Param("poin") int poin);

	@Query(value = "select * from nilai limit :limit", nativeQuery = true)
	List<Nilai> selectAllNilai(@Param("limit") int limit);

	@Modifying
	@Query(value = "update nilai set jenisNilai=:jenisNilai, poin=:poin "
			+ " where kode_nilai=:kodeNilai", nativeQuery = true)
	@Transactional
	int updateDataNilai(@Param("jenisNilai") String jenisNilai, @Param("poin") int poin,
			@Param("kodeSemester") String kodeNilai);

	@Modifying
	@Query(value = "delete from nilai where kode_nilai=:kodeNilai", nativeQuery = true)
	@Transactional
	int deleteDataNilai(@Param("kodeNilai") String kodeNilai);

	@Query(value = "select * from nilai where kode_nilai=:kodeNilai", nativeQuery = true)
	Nilai getNilaiByKodeNilai(@Param("kodeNilai") String kodeNilai);

	@Query(value = "select count(kode_nilai) from nilai ", nativeQuery = true)
	int hitungNilai();

	@Query(value = "select kode_nilai from nilai order by kode_nilai desc limit 1", nativeQuery = true)
	String getLastKodeNilai();
}
