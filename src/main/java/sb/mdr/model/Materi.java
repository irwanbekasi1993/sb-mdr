package sb.mdr.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="materi_pembelajaran")
public class Materi {
	@Id
private String kodeMateri;
private String jenisMateri;
private String isiMateri;



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

}
