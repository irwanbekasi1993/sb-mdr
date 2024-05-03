package sbmdr.model.redis;

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
	private String periodeMulai;
	private String periodeSelesai;
	
	public String getKodeSemester() {
		return kodeSemester;
	}
	public void setKodeSemester(String kodeSemester) {
		this.kodeSemester = kodeSemester;
	}
	public String getPeriodeMulai() {
		return periodeMulai;
	}
	public void setPeriodeMulai(String periodeMulai) {
		this.periodeMulai = periodeMulai;
	}
	public String getPeriodeSelesai() {
		return periodeSelesai;
	}
	public void setPeriodeSelesai(String periodeSelesai) {
		this.periodeSelesai = periodeSelesai;
	}
	
	

	

}
