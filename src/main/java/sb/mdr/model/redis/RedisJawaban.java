package sb.mdr.model.redis;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("redis_jawaban")
public class RedisJawaban implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3686649238485249001L;
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
