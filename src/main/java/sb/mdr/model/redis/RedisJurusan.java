package sb.mdr.model.redis;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("redis_jurusan")
public class RedisJurusan implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8973698154872066348L;
	@Id 
	private String kodeJurusan;
	private String namaJurusan;



	public String getKodeJurusan() {
		return kodeJurusan;
	}

	public void setKodeJurusan(String kodeJurusan) {
		this.kodeJurusan = kodeJurusan;
	}

	public String getNamaJurusan() {
		return namaJurusan;
	}

	public void setNamaJurusan(String namaJurusan) {
		this.namaJurusan = namaJurusan;
	}

}
