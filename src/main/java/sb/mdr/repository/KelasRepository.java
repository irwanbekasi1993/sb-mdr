package sb.mdr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sb.mdr.model.Kelas;

@Repository
public interface KelasRepository extends CrudRepository<Kelas, String> {

	@Modifying
	@Query(value = "insert into kelas (kode_kelas,nama_kelas,kapasitas_kelas,jenis_kelas) "
			+ "values(:kodeKelas, :namaKelas, :kapasitasKelas, :jenisKelas)", nativeQuery = true)
	@Transactional
	int insertDataKelas(@Param("kodeKelas")String kodekelas, @Param("namaKelas")String namakelas,
			@Param("kapasitasKelas")int kapasitasKelas, @Param("jenisKelas")String jenisKelas);
	
	@Query(value="select * from kelas limit :limit", nativeQuery=true)
	List<Kelas> selectAllKelas(@Param("limit")int limit);
	
	@Modifying
	@Query(value="update kelas set nama_kelas=:namaKelas, kapasitas_kelas=:kapasitasKelas,"
			+ "jenis_kelas=:jenisKelas where kode_kelas=:kodeKelas", nativeQuery=true)
	@Transactional
	int updateDataKelas(@Param("namaKelas")String namaKelas,
			@Param("kapasitasKelas")int kapasitasKelas, 
			@Param("jenisKelas")String jenisKelas, @Param("kodeKelas")String kodeKelas);
	
	@Modifying
	@Query(value="delete from kelas where kode_kelas=:kodeKelas", nativeQuery=true)
	@Transactional
	int deleteDataKelas(@Param("kodeKelas")String kodeKelas);
	
	@Query(value="select * from kelas where kode_kelas=:kodeKelas", nativeQuery=true)
	Kelas getKelasByKodeKelas(@Param("kodeKelas")String kodeKelas);
	
	@Query(value="select count(kode_kelas) from kelas ", nativeQuery=true)
	int hitungKelas();
	
	@Query(value="select kode_kelas from kelas order by kode_kelas desc limit 1", nativeQuery=true)
	String getLastKodeKelas();
}
