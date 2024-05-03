package sbmdr.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "mahasiswa")
public class Mahasiswa {
	@Id
	private String nim;
	private String namaMahasiswa;
	private String alamatMahasiswa;
	private String jenisKelaminMahasiswa;
	private String nohp;
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
	public String getStatusMahasiswa() {
		return statusMahasiswa;
	}
	public void setStatusMahasiswa(String statusMahasiswa) {
		this.statusMahasiswa = statusMahasiswa;
	}
	private String email;
	private String statusMahasiswa;
	
	
}
