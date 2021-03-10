package sb.mdr.model.redis;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("redis_semester")
public class RedisSemester implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3798482953957788539L;
	@Id 
	private String kodeSemester;
	private int periode;
	private Date waktuMasuk;
	private Date waktuKeluar;
	private String ganjilGenap;

	

	public String getGanjilGenap() {
		return ganjilGenap;
	}

	public void setGanjilGenap(String ganjilGenap) {
		this.ganjilGenap = ganjilGenap;
	}

	public String getKodeSemester() {
		return kodeSemester;
	}

	public void setKodeSemester(String kodeSemester) {
		this.kodeSemester = kodeSemester;
	}

	public int getPeriode() {
		return periode;
	}

	public void setPeriode(int periode) {
		this.periode = periode;
	}

	public Date getWaktuMasuk() {
		return waktuMasuk;
	}

	public void setWaktuMasuk(Date waktuMasuk) {
		this.waktuMasuk = waktuMasuk;
	}

	public Date getWaktuKeluar() {
		return waktuKeluar;
	}

	public void setWaktuKeluar(Date waktuKeluar) {
		this.waktuKeluar = waktuKeluar;
	}

}
