package sb.mdr.model.redis;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("redis_materi")
public class RedisMateri implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7201366650249544176L;
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
