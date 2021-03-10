package sb.mdr.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "kelas")
public class Kelas {
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
