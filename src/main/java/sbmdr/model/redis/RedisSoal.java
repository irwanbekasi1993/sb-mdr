package sbmdr.model.redis;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("redis_soal")
public class RedisSoal implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3123151842605192837L;
	@Id 
	private String kodeSoal;
	private String jenisSoal;
	private String isiSoal;
	private int nilai;
	private String jawaban;
	
	

	public String getJawaban() {
		return jawaban;
	}

	public void setJawaban(String jawaban) {
		this.jawaban = jawaban;
	}

	public int getNilai() {
		return nilai;
	}

	public void setNilai(int nilai) {
		this.nilai = nilai;
	}

	public String getIsiSoal() {
		return isiSoal;
	}

	public void setIsiSoal(String isiSoal) {
		this.isiSoal = isiSoal;
	}


	public String getKodeSoal() {
		return kodeSoal;
	}

	public void setKodeSoal(String kodeSoal) {
		this.kodeSoal = kodeSoal;
	}

	public String getJenisSoal() {
		return jenisSoal;
	}

	public void setJenisSoal(String jenisSoal) {
		this.jenisSoal = jenisSoal;
	}

}
