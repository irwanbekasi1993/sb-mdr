package sbmdr.model;

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
