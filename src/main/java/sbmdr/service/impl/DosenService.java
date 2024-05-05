package sbmdr.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import sbmdr.config.KafkaConsumerConfig;
import sbmdr.config.KafkaProducerConfig;
import sbmdr.model.Dosen;
import sbmdr.model.redis.RedisDosen;
import sbmdr.repository.DosenRepository;
import sbmdr.repository.RoleRepository;
import sbmdr.repository.UserRepository;
import sbmdr.repository.redis.RedisDosenRepository;

@Service
public class DosenService {

	@Autowired
	private DosenRepository dosenRepository;

	@Autowired
	private RedisDosenRepository redisDosenRepo;

	private RedisDosen redisDosen = new RedisDosen();


	// @Autowired
	// private KafkaTemplate<String, String> kafkaTemplate;

	private KafkaProducerConfig kafkaProducerDosen;
	private KafkaConsumerConfig kafkaConsumerDosen;
	
	private ObjectMapper mapper = new ObjectMapper();

	private String result;
	
	private void kafkaProcessing(String result){
		kafkaProducerDosen.sendMessage("kafka producer dosen produce result: "+result);
		kafkaConsumerDosen.consumeMessage("kafka consumer dosen consume result: "+result);
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

	public Dosen getDosen(String nip) {
		Dosen dosen = new Dosen();
		try {
			dosen = dosenRepository.getDosenByNip(nip);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dosen;
	}

	public String deleteDosen(String nip) {
		
		try {
			Dosen cek = getDosen(nip);
			if(cek!=null){
			dosenRepository.deleteDataDosen(nip);
				redisDosen.setNip(nip);
				redisDosenRepo.deleteById(redisDosen.getNip());
				result = "data dosen dengan kode dosen: " + nip + " telah dihapus";
			}else{
				result="data dosen not found";
			}
			kafkaProcessing(result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateDosen(Dosen dosen, String nip) {
		
		try {
			Dosen cek = getDosen(nip);
			if(cek!=null){
				dosenRepository.updateDataDosen(dosen.getNamaDosen(), dosen.getAlamatDosen(),
					dosen.getJenisKelaminDosen(), dosen.getStatusDosen(), dosen.getNohp(), dosen.getEmail(), nip);
					
				dosen.setNip(nip);
				redisDosen.setNip(dosen.getNip());
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
				
				// kafkaTemplate.send("sbmdr", str);
				//kafkaConsumer.subscribe(Collections.singletonList("sbmdr"));
				result = "data dosen telah diperbaharui dengan kode dosen: " + nip;
				
			}else{
				result="data dosen not found";
			}
			kafkaProcessing(result);
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


}
