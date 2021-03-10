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
import sb.mdr.model.Mahasiswa;

@Repository
public interface MahasiswaRepository extends JpaRepository<Mahasiswa, String> {

	@Modifying
	@Query(value = "insert into mahasiswa (nim,nama_mahasiswa,jenis_kelamin_mahasiswa,alamat_mahasiswa,nohp,email,status_mahasiswa) "
			+ "values(:nim, :namaMahasiswa, :jeniskelaminMahasiswa, :alamatMahasiswa, :nohp, :email, :statusMahasiswa)", nativeQuery = true)
	@Transactional
	int insertDataMahasiswa(
			@Param("nim")String nim,
			@Param("namaMahasiswa")String namaMahasiswa, 
			@Param("jenisKelaminMahasiswa")String jenisKelaminMahasiswa,
			@Param("alamatMahasiswa")String alamatMahasiswa,
			@Param("nohp")String nohp, @Param("email")String email, @Param("statusMahasiswa")String statusMahasiswa);
	
	@Query(value="select * from mahasiswa limit :limit", nativeQuery=true)
	List<Mahasiswa> selectAllMahasiswa(@Param("limit")int limit);
	
	@Modifying
	@Query(value="update mahasiswa"
			+ " set nama_mahasiswa=:namaMahasiswa,"
			+ " jenis_kelamin_mahasiswa=:jenisKelaminMahasiswa,"
			+ " alamat_mahasiswa=:alamatMahasiswa, "
			+ "nohp=:nohp,email=:email,status_mahasiswa=:statusMahasiswa where nim=:nim", nativeQuery=true)
	@Transactional
	int updateDataMahasiswa(@Param("namaMahasiswa")String namaMahasiswa, 
			@Param("jenisKelaminMahasiswa")String jenisKelaminMahasiswa,
			@Param("alamatMahasiswa")String alamatMahasiswa,
			@Param("nohp")String nohp, @Param("email")String email, @Param("statusMahasiswa")String statusMahasiswa,
			@Param("nim")String nim);
	
	@Modifying
	@Query(value="delete from mahasiswa where nim=:nim", nativeQuery=true)
	@Transactional
	int deleteDataMahasiswa(@Param("nim")String nim);
	
	@Query(value="select * from mahasiswa where nim=:nim", nativeQuery=true)
	Mahasiswa getMahasiswaByNim(@Param("nim")String nim);
	
	@Query(value="select count(nim) from mahasiswa ", nativeQuery=true)
	int hitungMahasiswa();
	
	@Query(value="select kode_dosen from mahasiswa order by nim desc limit 1", nativeQuery=true)
	String getLastNim();
}
