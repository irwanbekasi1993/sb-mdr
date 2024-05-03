package sbmdr.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "semester")
public class Semester {
	@Id
	private String kodeSemester;
	private String periodeMulai;
	private String periodeSelesai;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="kode_jurusan")
	private Jurusan jurusan;

	public String getKodeSemester() {
		return kodeSemester;
	}
	public void setKodeSemester(String kodeSemester) {
		this.kodeSemester = kodeSemester;
	}
	
	public Jurusan getJurusan() {
		return jurusan;
	}
	public void setJurusan(Jurusan jurusan) {
		this.jurusan = jurusan;
	}
	public String getPeriodeMulai() {
		return periodeMulai;
	}
	public void setPeriodeMulai(String periodeMulai) {
		this.periodeMulai = periodeMulai;
	}
	public String getPeriodeSelesai() {
		return periodeSelesai;
	}
	public void setPeriodeSelesai(String periodeSelesai) {
		this.periodeSelesai = periodeSelesai;
	}
	
	

	

}
