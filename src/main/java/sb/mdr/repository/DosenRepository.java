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

@Repository
public interface DosenRepository extends JpaRepository<Dosen, String> {

	@Modifying
	@Query(value = "insert into dosen (kode_dosen,alamat_dosen,jenis_kelamin_dosen,nama_dosen,status_dosen,nohp,email) values(:kodeDosen, :alamatDosen, :jenisKelaminDosen, :namaDosen, :statusDosen,:nohp,:email)", nativeQuery = true)
	@Transactional
	int insertDataDosen(@Param("kodeDosen")String kodeDosen, @Param("namaDosen")String namaDosen, @Param("jenisKelaminDosen")String jenisKelaminDosen,
			@Param("alamatDosen")String alamatDosen, @Param("statusDosen")String statusDosen,@Param("nohp")String nohp,
			@Param("email")String email);
	
	@Query(value="select * from dosen limit :limit", nativeQuery=true)
	List<Dosen> selectAllDosen(@Param("limit")int limit);
	
	@Modifying
	@Query(value="update dosen set nama_dosen=:namaDosen, alamat_dosen=:alamatDosen, "
			+ "jenis_kelamin_dosen=:jenisKelaminDosen, "
			+ "status_dosen=:statusDosen, email=:email, nohp=:nohp"
			+ " where kode_dosen=:kodeDosen", nativeQuery=true)
	@Transactional
	int updateDataDosen(@Param("namaDosen")String namaDosen,@Param("alamatDosen")String alamatDosen,
			@Param("jenisKelaminDosen")String jenisKelaminDosen, @Param("statusDosen")String statusDosen,
			@Param("nohp")String nohp,
			@Param("email")String email,
			@Param("kodeDosen")String kodeDosen);
	
	@Modifying
	@Query(value="delete from dosen where kode_dosen=:kodeDosen", nativeQuery=true)
	@Transactional
	int deleteDataDosen(@Param("kodeDosen")String kodeDosen);
	
	@Query(value="select * from dosen where kode_dosen=:kodeDosen", nativeQuery=true)
	Dosen getDosenByKodeDosen(@Param("kodeDosen")String kodeDosen);
	
	@Query(value="select count(kode_dosen) from dosen ", nativeQuery=true)
	int hitungDosen();
	
	@Query(value="select kode_dosen from dosen order by kode_dosen desc limit 1", nativeQuery=true)
	String getLastKodeDosen();
}
