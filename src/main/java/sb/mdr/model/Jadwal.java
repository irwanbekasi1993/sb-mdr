package sb.mdr.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Jadwal {

	@Id
	private String kodeJadwal;
	
	@ManyToOne
	@JoinColumn(name="kodeKelas")
	public Kelas kelas;
	
	@ManyToOne
	@JoinColumn(name="kodeMatkul")
	public MataKuliah mataKuliah;
	
	@ManyToOne
	@JoinColumn(name="nim")
	public Mahasiswa mahasiswa;
	
	@ManyToOne
	@JoinColumn(name="kodeDosen")
	public Dosen dosen;
	
	@ManyToOne
	@JoinColumn(name="kodeSemester")
	public Semester semester;
	
	@ManyToOne
	@JoinColumn(name="kodeJurusan")
	public Jurusan jurusan;
	
	

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public Jurusan getJurusan() {
		return jurusan;
	}

	public void setJurusan(Jurusan jurusan) {
		this.jurusan = jurusan;
	}

	public Mahasiswa getMahasiswa() {
		return mahasiswa;
	}

	public void setMahasiswa(Mahasiswa mahasiswa) {
		this.mahasiswa = mahasiswa;
	}

	public Dosen getDosen() {
		return dosen;
	}

	public void setDosen(Dosen dosen) {
		this.dosen = dosen;
	}

	public String getKodeJadwal() {
		return kodeJadwal;
	}

	public void setKodeJadwal(String kodeJadwal) {
		this.kodeJadwal = kodeJadwal;
	}

	public Kelas getKelas() {
		return kelas;
	}

	public void setKelas(Kelas kelas) {
		this.kelas = kelas;
	}

	public MataKuliah getMataKuliah() {
		return mataKuliah;
	}

	public void setMataKuliah(MataKuliah mataKuliah) {
		this.mataKuliah = mataKuliah;
	}
	
	
}
