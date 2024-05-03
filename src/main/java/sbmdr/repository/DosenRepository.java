package sbmdr.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sbmdr.model.Dosen;

@Repository
public interface DosenRepository extends JpaRepository<Dosen, String> {

	@Modifying
	@Query(value = "insert into dosen (nip,nama_dosen,email,status_dosen) values(:nip, :namaDosen,:email,:statusDosen)", nativeQuery = true)
	@Transactional
	int insertDataDosen(@Param("nip")String nip, @Param("namaDosen")String namaDosen, 
			@Param("email")String email,@Param("statusDosen")String statusDosen);
	
	@Query(value="select * from dosen limit :limit", nativeQuery=true)
	List<Dosen> selectAllDosen(@Param("limit")int limit);
	
	@Modifying
	@Query(value="update dosen set nama_dosen=:namaDosen, alamat_dosen=:alamatDosen, "
			+ "jenis_kelamin_dosen=:jenisKelaminDosen, "
			+ "status_dosen=:statusDosen, email=:email, nohp=:nohp"
			+ " where nip=:nip", nativeQuery=true)
	@Transactional
	int updateDataDosen(@Param("namaDosen")String namaDosen,@Param("alamatDosen")String alamatDosen,
			@Param("jenisKelaminDosen")String jenisKelaminDosen, @Param("statusDosen")String statusDosen,
			@Param("nohp")String nohp,
			@Param("email")String email,
			@Param("nip")String nip);
	
	@Modifying
	@Query(value="delete from dosen where nip=:nip", nativeQuery=true)
	@Transactional
	int deleteDataDosen(@Param("nip")String nip);
	
	@Query(value="select * from dosen where nip=:nip", nativeQuery=true)
	Dosen getDosenByNip(@Param("nip")String nip);
	
	@Query(value="select count(nip) from dosen ", nativeQuery=true)
	int hitungDosen();
	
	@Query(value="select nip from dosen order by nip desc limit 1", nativeQuery=true)
	String getLastNip();

}
