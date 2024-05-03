package sbmdr.model.redis;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("redis_kelas")
public class RedisKelas implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6706078995883166665L;
	@Id 
	private String kodeKelas;
	private String namaKelas;
	private int kapasitasKelas;
	private String jenisKelas;



	public String getKodeKelas() {
		return kodeKelas;
	}

	public void setKodeKelas(String kodeKelas) {
		this.kodeKelas = kodeKelas;
	}

	public String getNamaKelas() {
		return namaKelas;
	}

	public void setNamaKelas(String namaKelas) {
		this.namaKelas = namaKelas;
	}

	public int getKapasitasKelas() {
		return kapasitasKelas;
	}

	public void setKapasitasKelas(int kapasitasKelas) {
		this.kapasitasKelas = kapasitasKelas;
	}

	public String getJenisKelas() {
		return jenisKelas;
	}

	public void setJenisKelas(String jenisKelas) {
		this.jenisKelas = jenisKelas;
	}

}
