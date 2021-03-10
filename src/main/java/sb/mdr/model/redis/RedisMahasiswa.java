package sb.mdr.model.redis;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("redis_mahasiswa")
public class RedisMahasiswa implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3432157588865774697L;
	@Id 
	private String nim;
	private String namaMahasiswa;
	private String alamatMahasiswa;
	private String jenisKelaminMahasiswa;
	private String nohp;
	private String email;
	private String statusMahasiswa;
	
	
	

	public String getStatusMahasiswa() {
		return statusMahasiswa;
	}

	public void setStatusMahasiswa(String statusMahasiswa) {
		this.statusMahasiswa = statusMahasiswa;
	}

	public String getJenisKelaminMahasiswa() {
		return jenisKelaminMahasiswa;
	}

	public void setJenisKelaminMahasiswa(String jenisKelaminMahasiswa) {
		this.jenisKelaminMahasiswa = jenisKelaminMahasiswa;
	}

	public String getNohp() {
		return nohp;
	}

	public void setNohp(String nohp) {
		this.nohp = nohp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNim() {
		return nim;
	}

	public void setNim(String nim) {
		this.nim = nim;
	}

	public String getNamaMahasiswa() {
		return namaMahasiswa;
	}

	public void setNamaMahasiswa(String namaMahasiswa) {
		this.namaMahasiswa = namaMahasiswa;
	}

	public String getAlamatMahasiswa() {
		return alamatMahasiswa;
	}

	public void setAlamatMahasiswa(String alamatMahasiswa) {
		this.alamatMahasiswa = alamatMahasiswa;
	}

}
