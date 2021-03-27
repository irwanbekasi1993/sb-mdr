package sb.mdr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
/*import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
*/import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import sb.mdr.filter.JwtTokenFilter;
import sb.mdr.model.Dosen;
import sb.mdr.model.redis.RedisDosen;
import sb.mdr.repository.DosenRepository;
import sb.mdr.repository.redis.RedisDosenRepository;

@Service
public class DosenService {

	@Autowired
	private DosenRepository dosenRepository;

	@Autowired
	private RedisDosenRepository redisDosenRepo;

	private RedisDosen redisDosen = new RedisDosen();

	private Dosen dosen;
	
	@Autowired
	private JwtTokenFilter jwtTokenFilter;

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	private KafkaConsumer<String, Object> kafkaConsumer;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	List<Dosen> listDosen = new ArrayList<>();

	public String insertDosen(Dosen localDosen) {
		
		int increment = 0;
		int flagInsert = 0;
		String result = null;
		String cekDosen = dosenRepository.getLastKodeDosen();
		if (cekDosen == null) {
			increment += 1;
			localDosen.setKodeDosen("DSN" + increment);

		} else {
			increment = dosenRepository.hitungDosen() + 1;
			localDosen.setKodeDosen("DSN" + increment);
		}
		try {
			flagInsert = dosenRepository.insertDataDosen(localDosen.getKodeDosen(), localDosen.getNamaDosen(),
					localDosen.getJenisKelaminDosen(), localDosen.getAlamatDosen(), localDosen.getStatusDosen(),
					localDosen.getNohp(), localDosen.getEmail());

			redisDosen.setKodeDosen(localDosen.getKodeDosen());
			redisDosen.setNamaDosen(localDosen.getNamaDosen());
			redisDosen.setJenisKelaminDosen(localDosen.getJenisKelaminDosen());
			redisDosen.setAlamatDosen(localDosen.getAlamatDosen());
			redisDosen.setStatusDosen(localDosen.getStatusDosen());
			redisDosen.setNohp(localDosen.getNohp());
			redisDosen.setEmail(localDosen.getEmail());
			redisDosenRepo.save(redisDosen);

			if (flagInsert == 1) {
				
				byte[]bytes = mapper.writeValueAsBytes(dosen);
				String str= new String(bytes);
				
				System.err.println("sending message: "+str);
				
				result = "data dosen berhasil dimasukkan dengan kode dosen: " + cekDosen;
				kafkaTemplate.send("sbmdr","DSN", result);
				//kafkaConsumer.subscribe(Collections.singletonList("sbmdr"));
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public List<Dosen> getAllDosen(int limit) {
		
		try {
			listDosen = dosenRepository.selectAllDosen(limit);
			kafkaTemplate.send("sbmdr","DSN", listDosen);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listDosen;
	}

	public Dosen getDosen(String kodeDosen) {
		Dosen dosen = new Dosen();
		try {
			dosen = dosenRepository.getDosenByKodeDosen(kodeDosen);
			kafkaTemplate.send("sbmdr","DSN", dosen);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dosen;
	}

	public String deleteDosen(String kodeDosen) {
		int deleteFlag = 0;
		String result = null;
		try {
			deleteFlag = dosenRepository.deleteDataDosen(kodeDosen);
			if (deleteFlag == 1) {
				redisDosen.setKodeDosen(kodeDosen);
				redisDosenRepo.deleteById(redisDosen.getKodeDosen());
				result = "data dosen dengan kode dosen: " + kodeDosen + " telah dihapus";
				kafkaTemplate.send("sbmdr","DSN", result);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateDosen(Dosen dosen, String kodeDosen) {
		int updateFlag = 0;
		String result = null;
		try {
			updateFlag = dosenRepository.updateDataDosen(dosen.getNamaDosen(), dosen.getAlamatDosen(),
					dosen.getJenisKelaminDosen(), dosen.getStatusDosen(), dosen.getNohp(), dosen.getEmail(), kodeDosen);
			if (updateFlag == 1) {
				dosen.setKodeDosen(kodeDosen);
				redisDosen.setKodeDosen(dosen.getKodeDosen());
				redisDosen.setNamaDosen(dosen.getNamaDosen());
				redisDosen.setJenisKelaminDosen(dosen.getJenisKelaminDosen());
				redisDosen.setAlamatDosen(dosen.getAlamatDosen());
				redisDosen.setStatusDosen(dosen.getStatusDosen());
				redisDosen.setNohp(dosen.getNohp());
				redisDosen.setEmail(dosen.getEmail());
				redisDosenRepo.save(redisDosen);
				
				byte[]bytes = mapper.writeValueAsBytes(dosen);
				String str= new String(bytes);
				
				System.err.println("sending message: "+str);
				
				/*kafkaTemplate.send("sbmdr", str);*/
				//kafkaConsumer.subscribe(Collections.singletonList("sbmdr"));
				result = "data dosen telah diperbaharui dengan kode dosen: " + kodeDosen;
				kafkaTemplate.send("sbmdr","DSN", dosen+"\n"+result);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	

	public DosenRepository getDosenRepository() {
		return dosenRepository;
	}

	public void setDosenRepository(DosenRepository dosenRepository) {
		this.dosenRepository = dosenRepository;
	}

	public Dosen getDosen() {
		return dosen;
	}

	public void setDosen(Dosen dosen) {
		this.dosen = dosen;
	}

}
