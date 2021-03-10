package sb.mdr.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "dosen")
public class Dosen {
	@Id
	private String kodeDosen;
	private String namaDosen;
	private String alamatDosen;
	private String jenisKelaminDosen;
	private String statusDosen;
	private String nohp;
	private String email;
	
	

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

	public String getKodeDosen() {
		return kodeDosen;
	}

	public void setKodeDosen(String kodeDosen) {
		this.kodeDosen = kodeDosen;
	}

	public String getNamaDosen() {
		return namaDosen;
	}

	public void setNamaDosen(String namaDosen) {
		this.namaDosen = namaDosen;
	}

	public String getAlamatDosen() {
		return alamatDosen;
	}

	public void setAlamatDosen(String alamatDosen) {
		this.alamatDosen = alamatDosen;
	}

	public String getJenisKelaminDosen() {
		return jenisKelaminDosen;
	}

	public void setJenisKelaminDosen(String jenisKelaminDosen) {
		this.jenisKelaminDosen = jenisKelaminDosen;
	}

	public String getStatusDosen() {
		return statusDosen;
	}

	public void setStatusDosen(String statusDosen) {
		this.statusDosen = statusDosen;
	}

}
