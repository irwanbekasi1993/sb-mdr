package sb.mdr.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "matakuliah")
public class MataKuliah {
	@Id
	private String kodeMatkul;
	private String namaMatkul;
	private int sks;

	public String getKodeMatkul() {
		return kodeMatkul;
	}

	public void setKodeMatkul(String kodeMatkul) {
		this.kodeMatkul = kodeMatkul;
	}

	public String getNamaMatkul() {
		return namaMatkul;
	}

	public void setNamaMatkul(String namaMatkul) {
		this.namaMatkul = namaMatkul;
	}

	public int getSks() {
		return sks;
	}

	public void setSks(int sks) {
		this.sks = sks;
	}

}
