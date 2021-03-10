package sb.mdr.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="nilai")
public class Nilai {
	@Id
private String kodeNilai;
private String jenisNilai;
private int poin;
public String getKodeNilai() {
	return kodeNilai;
}
public void setKodeNilai(String kodeNilai) {
	this.kodeNilai = kodeNilai;
}
public String getJenisNilai() {
	return jenisNilai;
}
public void setJenisNilai(String jenisNilai) {
	this.jenisNilai = jenisNilai;
}
public int getPoin() {
	return poin;
}
public void setPoin(int poin) {
	this.poin = poin;
}


}
