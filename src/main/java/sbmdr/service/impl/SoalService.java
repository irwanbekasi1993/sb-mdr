package sbmdr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import sbmdr.config.KafkaConsumerConfig;
import sbmdr.config.KafkaProducerConfig;
import sbmdr.model.Dosen;
import sbmdr.model.MataKuliah;
import sbmdr.model.Semester;
import sbmdr.model.Soal;
import sbmdr.model.redis.RedisSoal;
import sbmdr.repository.DosenRepository;
import sbmdr.repository.MataKuliahRepository;
import sbmdr.repository.SemesterRepository;
import sbmdr.repository.SoalRepository;
import sbmdr.repository.redis.RedisSoalRepository;

@Service
public class SoalService {

	@Autowired
	private SoalRepository soalRepository;
	
	private RedisSoal redisSoal;
	
	@Autowired
	private RedisSoalRepository redisSoalRepo;
	
	// @Autowired
	// private KafkaTemplate<String,String> kafkaTemplate;
	
	private ObjectMapper objectMapper;
	
	private String result;

	private KafkaProducerConfig kafkaProducerSoal;
private KafkaConsumerConfig kafkaConsumerSoal;

private void kafkaProcessing(String result){
    kafkaProducerSoal.sendMessage("kafka producer soal produce result: "+result);
    kafkaConsumerSoal.consumeMessage("kafka consumer soal consume result: "+result);
}

	public String insertSoal(Soal localSoal) {
		
		try {
			Soal cek = getSoal(localSoal.getKodeSoal());
			if(cek==null){
				 soalRepository.insertDataSoal(localSoal.getKodeSoal(), localSoal.getJenisSoal(),
					localSoal.getIsiSoal(),localSoal.getNilai());

				redisSoal.setKodeSoal(localSoal.getKodeSoal());
				redisSoal.setJenisSoal(localSoal.getJenisSoal());
				redisSoal.setIsiSoal(localSoal.getIsiSoal());
				redisSoal.setNilai(localSoal.getNilai());
				redisSoalRepo.save(redisSoal);
				
				byte[]bytes = objectMapper.writeValueAsBytes(localSoal);
				String str = new String(bytes);
				// kafkaTemplate.send("sbmdr",str);
				System.err.println("sending message: "+str);
				
				result="data semester berhasil dimasukkan dengan kode soal: "+localSoal.getKodeSoal();
			
			}else{
				result="soal not found";
			}
			kafkaProcessing(result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public List<Soal> getAllSoal(int limit) {
		List<Soal> listSoal= new ArrayList<>();
		try {
			listSoal = soalRepository.selectAllSoal(limit);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listSoal;
	}
	
	public Soal getSoal(String kodeSoal) {
		Soal soal = new Soal();
		try {
			soal= soalRepository.getSoalByKodeSoal(kodeSoal);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return soal;
	}

	public String deleteSoal(String kodeSoal) {
		try {
			Soal cek = getSoal(kodeSoal);
			if(cek!=null){
			soalRepository.deleteDataSoal(kodeSoal);
				redisSoalRepo.delete(redisSoalRepo.findByKodeSoal(kodeSoal));
				result="data soal dengan kode soal: "+kodeSoal+" telah dihapus";
			}else{
				result="data soal not found";
			}
			kafkaProcessing(result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateSoal(Soal soal,String kodeSoal) {
		try {
			Soal cek = getSoal(kodeSoal);
			if(cek!=null){
			 soalRepository.updateDataSoal(soal.getJenisSoal(),soal.getIsiSoal(),soal.getNilai(),kodeSoal);
				
				soal.setKodeSoal(kodeSoal);
				redisSoal.setKodeSoal(soal.getKodeSoal());
				redisSoal.setJenisSoal(soal.getJenisSoal());
				redisSoal.setIsiSoal(soal.getIsiSoal());
				redisSoal.setNilai(soal.getNilai());
				redisSoalRepo.save(redisSoal);
				
				byte[]bytes = objectMapper.writeValueAsBytes(soal);
				String str = new String(bytes);
				// kafkaTemplate.send("sbmdr",str);
				System.err.println("sending message: "+str);
				
				result="data soal telah diperbaharui dengan kode soal: "+kodeSoal;
			}else{
				result="data soal not found";
			}
			kafkaProcessing(result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	

	public SoalRepository getSoalRepository() {
		return soalRepository;
	}

	public void setSoalRepository(SoalRepository soalRepository) {
		this.soalRepository = soalRepository;
	}

	





}
