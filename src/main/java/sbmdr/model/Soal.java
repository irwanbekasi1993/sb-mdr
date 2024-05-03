package sbmdr.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "soal")
public class Soal {
	@Id
	private String kodeSoal;
	private String jenisSoal;
	private String isiSoal;
	private int nilai;
	private String jawaban;


	public int getNilai() {
		return nilai;
	}

	public void setNilai(int nilai) {
		this.nilai = nilai;
	}

	public String getIsiSoal() {
		return isiSoal;
	}

	public void setIsiSoal(String isiSoal) {
		this.isiSoal = isiSoal;
	}


	public String getKodeSoal() {
		return kodeSoal;
	}

	public void setKodeSoal(String kodeSoal) {
		this.kodeSoal = kodeSoal;
	}

	public String getJenisSoal() {
		return jenisSoal;
	}

	public void setJenisSoal(String jenisSoal) {
		this.jenisSoal = jenisSoal;
	}

	public String getJawaban() {
		return jawaban;
	}

	public void setJawaban(String jawaban) {
		this.jawaban = jawaban;
	}


	

}
