package sb.mdr.model.redis;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("redis_matakuliah")
public class RedisMataKuliah implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3486932179441104647L;
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
