package sb.mdr.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jawaban")
public class Jawaban {
	@Id
	private String kodeJawaban;
	private String jenisJawaban;
	private String isiJawaban;
	
	

	public String getIsiJawaban() {
		return isiJawaban;
	}

	public void setIsiJawaban(String isiJawaban) {
		this.isiJawaban = isiJawaban;
	}


	public String getKodeJawaban() {
		return kodeJawaban;
	}

	public void setKodeJawaban(String kodeJawaban) {
		this.kodeJawaban = kodeJawaban;
	}

	public String getJenisJawaban() {
		return jenisJawaban;
	}

	public void setJenisJawaban(String jenisJawaban) {
		this.jenisJawaban = jenisJawaban;
	}

}
