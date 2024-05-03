package sbmdr.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.redis.core.RedisHash;


@Entity
@Table(name = "dosen")
public class Dosen implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6400753748337189167L;
	/**
	 * 
	 */
	@Id
	private String nip;
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

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
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
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Dosen [nip="+nip+",namaDosen="+namaDosen+",jenisKelaminDosen="+jenisKelaminDosen+",alamatDosen="+alamatDosen+",statusDosen="+statusDosen+",nohp="+nohp+",email="+email+"]";
	}
	

}
