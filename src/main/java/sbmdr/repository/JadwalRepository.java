package sbmdr.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sbmdr.model.Jadwal;

@Repository
public interface JadwalRepository extends JpaRepository<Jadwal,Long>{

    @Query(value="select * from jadwal limit=:limit",nativeQuery = true)
    List<Jadwal> viewAllJadwal(@Param("limit")int limit);

    @Query(value="select * from jadwal where kode_dosen=:kodeDosen and kode_jurusan=:kodeJurusan and kode_kelas=:kodeKelas and kode_matkul=:kodeMatkul and nim=:nim and kode_semester=:kodeSemester", nativeQuery = true)
    Jadwal searchJadwal(@Param("kodeDosen")String kodeDosen,
    @Param("kodeJurusan")String kodeJurusan,
    @Param("kodeKelas")String kodeKelas,
    @Param("kodeMatkul")String kodeMatkul,
    @Param("nim")String nim,
    @Param("kodeSemester")String kodeSemester);

    @Transactional
    @Modifying
    @Query(value="update jadwal set kode_dosen=:kodeDosen, kode_jurusan=:kodeJurusan, kode_kelas=:kodeKelas, kode_matkul=:kodeMatkul,nim=:nim, kode_semester=:kodeSemester where kode_dosen=:kodeDosen and kode_jurusan=:kodeJurusan and kode_kelas=:kodeKelas and kode_matkul=:kodeMatkul nim=:nim and kode_semester=:kodeSemester",nativeQuery=true)
    int updateJadwal(@Param("kodeDosen")String kodeDosen,
    @Param("kodeJurusan")String kodeJurusan,
    @Param("kodeKelas")String kodeKelas,
    @Param("kodeMatkul")String kodeMatkul,
    @Param("nim") String nim,
    @Param("kodeSemester")String kodeSemester);

    @Transactional
    @Modifying
    @Query(value="delete from jadwal where kode_dosen=:kodeDosen and kode_jurusan=:kodeJurusan and kode_kelas=:kodeKelas and kode_matkul=:kodeMatkul and nim=:nim and kode_semester=:kodeSemester",nativeQuery = true)
    int deleteJadwal(@Param("kodeDosen")String kodeDosen,
    @Param("kodeJurusan")String kodeJurusan,
    @Param("kodeKelas")String kodeKelas,
    @Param("kodeMatkul")String kodeMatkul,
    @Param("nim")String nim,
    @Param("kodeSemester")String kodeSemester);
}
