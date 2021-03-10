package sb.mdr.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="jurusan")
public class Jurusan {
	@Id
	private String kodeJurusan;
	private String namaJurusan;



	public String getKodeJurusan() {
		return kodeJurusan;
	}

	public void setKodeJurusan(String kodeJurusan) {
		this.kodeJurusan = kodeJurusan;
	}

	public String getNamaJurusan() {
		return namaJurusan;
	}

	public void setNamaJurusan(String namaJurusan) {
		this.namaJurusan = namaJurusan;
	}

}
