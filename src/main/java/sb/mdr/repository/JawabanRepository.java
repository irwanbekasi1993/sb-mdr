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
import sb.mdr.model.Jawaban;
import sb.mdr.model.Semester;
import sb.mdr.model.Soal;

@Repository
public interface JawabanRepository extends JpaRepository<Jawaban, String> {

	@Modifying
	@Query(value = "insert into jawaban (kode_jawaban,jenis_jawaban,isi_jawaban)"
			+ "values(:kodeJawaban, :jenisJawaban,:isiJawaban)", nativeQuery = true)
	@Transactional
	int insertDataJawaban(@Param("kodeJawaban") String kodeJawaban, @Param("jenisJawaban") String jenisJawaban,
			@Param("isiJawaban") String isiJawaban);

	@Query(value = "select * from jawaban limit :limit", nativeQuery = true)
	List<Jawaban> selectAllJawaban(@Param("limit") int limit);

	@Modifying
	@Query(value = "update jawaban set jenis_jawaban=:jenisJawaban, isi_jawaban=:isiJawaban"
			+ " where kode_jawaban=:kodeJawaban", nativeQuery = true)
	@Transactional
	int updateDataJawaban(@Param("jenisJawaban") String jenisJawaban, @Param("isiJawaban") String isiJawaban,
			@Param("kodeSoal") String kodeSoal);

	@Modifying
	@Query(value = "delete from jawaban where kode_jawaban=:kodeJawaban", nativeQuery = true)
	@Transactional
	int deleteDataJawaban(@Param("kodejawaban") String kodeJawaban);

	@Query(value = "select * from jawaban where kode_jawaban=:kodeJawaban", nativeQuery = true)
	Jawaban getJawabanByKodeJawaban(@Param("kodejawaban") String kodeJawaban);

	@Query(value = "select count(kode_jawaban) from jawaban ", nativeQuery = true)
	int hitungJawaban();

	@Query(value = "select kode_jawaban from jawaban order by kode_jawaban desc limit 1", nativeQuery = true)
	String getLastKodeJawaban();
}
