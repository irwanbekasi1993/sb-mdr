package sb.mdr.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	private KafkaTemplate<String, String> kafkaTemplate;

	private KafkaConsumer<String, Dosen> kafkaConsumer;
	
	private ObjectMapper mapper = new ObjectMapper();

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
				
				kafkaTemplate.send("sbmdr", str);
				//kafkaConsumer.subscribe(Collections.singletonList("sbmdr"));
				
				result = "data dosen berhasil dimasukkan dengan kode dosen: " + cekDosen;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public List<Dosen> getAllDosen(int limit) {
		List<Dosen> listDosen = new ArrayList<>();
		try {
			listDosen = dosenRepository.selectAllDosen(limit);

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
				
				kafkaTemplate.send("sbmdr", str);
				//kafkaConsumer.subscribe(Collections.singletonList("sbmdr"));
				result = "data dosen telah diperbaharui dengan kode dosen: " + kodeDosen;
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
