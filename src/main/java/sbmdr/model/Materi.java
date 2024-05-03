package sbmdr.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="materi")
public class Materi {
	@Id
private String kodeMateri;
private String jenisMateri;
private String isiMateri;

@ManyToOne(cascade = CascadeType.ALL)
@JoinColumn(name="kode_soal")
private Soal soal;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="kode_matkul")
	private MataKuliah matakuliah;

	
	
	public void setMatakuliah(MataKuliah matakuliah) {
		this.matakuliah = matakuliah;
	}
	public MataKuliah getMatakuliah() {
		return matakuliah;
	}
	public void setMataKuliah(MataKuliah matakuliah) {
		this.matakuliah = matakuliah;
	}
public String getIsiMateri() {
	return isiMateri;
}
public void setIsiMateri(String isiMateri) {
	this.isiMateri = isiMateri;
}
public String getKodeMateri() {
	return kodeMateri;
}
public void setKodeMateri(String kodeMateri) {
	this.kodeMateri = kodeMateri;
}
public String getJenisMateri() {
	return jenisMateri;
}
public void setJenisMateri(String jenisMateri) {
	this.jenisMateri = jenisMateri;
}
public Soal getSoal() {
	return soal;
}
public void setSoal(Soal soal) {
	this.soal = soal;
}

}
