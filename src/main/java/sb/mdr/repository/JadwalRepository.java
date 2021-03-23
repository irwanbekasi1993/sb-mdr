package sb.mdr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sb.mdr.model.Dosen;
import sb.mdr.model.Jadwal;
import sb.mdr.model.Jurusan;
import sb.mdr.model.Kelas;
import sb.mdr.model.Mahasiswa;
import sb.mdr.model.MataKuliah;
import sb.mdr.model.Semester;

@Repository
public interface JadwalRepository extends JpaRepository<Jadwal, String> {

	@Modifying
	@Query(value = "insert into jadwal (kodeKelas,kodeMatkul,nim,kodeDosen,kodeSemester,kodeJurusan)"
			+ " values(:kodeKelas, :kodeMatkul, :nim, :kodeDosen, :kodeSemester,:kodeJurusan)", nativeQuery = true)
	@Transactional
	int insertDataJadwal(@Param("kodeKelas")Optional<Kelas> kodeKelas, @Param("kodeMatkul")Optional<MataKuliah> kodeMatkul, @Param("nim")Optional<Mahasiswa> nim,
			@Param("kodeDosen")Optional<Dosen> kodeDosen, @Param("kodeSemester")Optional<Semester> kodeSemester,@Param("kodeJurusan")Optional<Jurusan> kodeJurusan);
	
	@Query(value="select * from jadwal limit :limit", nativeQuery=true)
	List<Jadwal> selectAllJadwal(@Param("limit")int limit);
	
	@Modifying
	@Query(value="update jadwal set kodeKelas=:kodeKelas, kodeMatkul=:kodeMatkul, "
			+ "nim=:nim, "
			+ "kodeDosen=:kodeDosen, kodeSemester=:kodeSemester, kodeJurusan=:kodeJurusan"
			+ " where kode_jadwal=:kodeJadwal", nativeQuery=true)
	@Transactional
	int updateDataJadwal(@Param("kodeKelas")Optional<Kelas> kodeKelas, @Param("kodeMatkul")Optional<MataKuliah> kodeMatkul, @Param("nim")Optional<Mahasiswa> nim,
			@Param("kodeDosen")Optional<Dosen> kodeDosen, @Param("kodeSemester")Optional<Semester> kodeSemester,@Param("kodeJurusan")Optional<Jurusan> kodeJurusan,
			@Param("kodeJadwal")String kodeJadwal);
	
	@Modifying
	@Query(value="delete from jadwal where kode_jadwal=:kodeJadwal", nativeQuery=true)
	@Transactional
	int deleteDataJadwal(@Param("kodeJadwal")String kodeJadwal);
	
	@Query(value="select * from jadwal where kodeJadwal=:kodeJadwal", nativeQuery=true)
	Jadwal getJadwalByKodeJadwal(@Param("kodeJadwal")String kodeJadwal);
	
	@Query(value="select count(kode_jadwal) from jadwal ", nativeQuery=true)
	int hitungJadwal();
	
	@Query(value="select kodeJadwal from jadwal order by kode_jadwal desc limit 1", nativeQuery=true)
	String getLastKodeJadwal();
}
