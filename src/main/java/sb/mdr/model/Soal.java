package sb.mdr.model;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "soal")
public class Soal {
	@Id
	private String kodeSoal;
	private String jenisSoal;
	private Date waktuPengerjaan;
	private Date waktuPengumpulan;
	private String isiSoal;
	
	

	public String getIsiSoal() {
		return isiSoal;
	}

	public void setIsiSoal(String isiSoal) {
		this.isiSoal = isiSoal;
	}

	public Date getWaktuPengerjaan() {
		return waktuPengerjaan;
	}

	public void setWaktuPengerjaan(Date waktuPengerjaan) {
		this.waktuPengerjaan = waktuPengerjaan;
	}

	public Date getWaktuPengumpulan() {
		return waktuPengumpulan;
	}

	public void setWaktuPengumpulan(Date waktuPengumpulan) {
		this.waktuPengumpulan = waktuPengumpulan;
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

}
