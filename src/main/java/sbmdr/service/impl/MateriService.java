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
import sbmdr.model.Materi;
import sbmdr.model.Semester;
import sbmdr.model.Soal;
import sbmdr.model.redis.RedisMateri;
import sbmdr.repository.DosenRepository;
import sbmdr.repository.MataKuliahRepository;
import sbmdr.repository.MateriRepository;
import sbmdr.repository.SemesterRepository;
import sbmdr.repository.SoalRepository;
import sbmdr.repository.redis.RedisMateriRepository;

@Service
public class MateriService {

	@Autowired
	private MateriRepository materiRepository;

	private Materi materi;
	
	private RedisMateri redisMateri;
	
	@Autowired
	private RedisMateriRepository redisMateriRepo;
	
	// @Autowired
	// private KafkaTemplate<String,String> kafkaTemplate;
	
	private ObjectMapper objectMapper;
	
	private String result;

	private KafkaProducerConfig kafkaProducerMateri;
private KafkaConsumerConfig kafkaConsumerMateri;

private void kafkaProcessing(String result){
    kafkaProducerMateri.sendMessage("kafka producer materi produce result: "+result);
    kafkaConsumerMateri.consumeMessage("kafka consumer materi consume result: "+result);
}

	public String insertMateri(Materi localMateri) {
		
		try {
			Materi cek = getMateri(localMateri.getKodeMateri());
			if(cek==null){
				materiRepository.insertDataMateri(localMateri.getKodeMateri(), localMateri.getJenisMateri(),
					localMateri.getIsiMateri());

				redisMateri.setKodeMateri(localMateri.getKodeMateri());
				redisMateri.setJenisMateri(localMateri.getJenisMateri());
				redisMateri.setIsiMateri(localMateri.getIsiMateri());
				redisMateriRepo.save(redisMateri);
				
				byte[]bytes = objectMapper.writeValueAsBytes(localMateri);
				String str = new String(bytes);
				// kafkaTemplate.send("sbmdr",str);
				System.err.println("sending message: "+str);
				
				result = "data materi berhasil dimasukkan dengan kode materi: " + localMateri.getKodeMateri();
			
			}else{
				result = "materi not found";
			}
			kafkaProcessing(result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	

	public List<Materi> getAllMateri(int limit) {
		List<Materi> listMateri = new ArrayList<>();
		try {
			listMateri = materiRepository.selectAllMateri(limit);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listMateri;
	}

	public Materi getMateri(String kodeMateri) {
		try {
			materi = materiRepository.getMateriByKodeMateri(kodeMateri);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return materi;
	}

	public String deleteMateri(String kodeMateri) {
		try {
			Materi cekMateri = getMateri(kodeMateri);
			if(cekMateri!=null){
				 materiRepository.deleteDataMateri(kodeMateri);
				redisMateriRepo.delete(redisMateriRepo.findByKodeMateri(kodeMateri));
				result = "data materi dengan kode materi: " + kodeMateri + " telah dihapus";
			
			}else{
				result="data materi not found";
			}
			kafkaProcessing(result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateMateri(Materi materi, String kodeMateri) {
		try {
			Materi cekMateri = getMateri(kodeMateri);
			if(cekMateri!=null){
				 materiRepository.updateDataMateri(materi.getJenisMateri(), materi.getIsiMateri(), kodeMateri);
				
				materi.setKodeMateri(kodeMateri);
				redisMateri.setKodeMateri(materi.getKodeMateri());
				redisMateri.setJenisMateri(materi.getJenisMateri());
				redisMateri.setIsiMateri(materi.getIsiMateri());
				redisMateriRepo.save(redisMateri);
				
				byte[]bytes = objectMapper.writeValueAsBytes(materi);
				String str = new String(bytes);
				System.err.println("sending message: "+str);
				// kafkaTemplate.send("sbmdr",str);
				
				result = "data materi telah diperbaharui dengan kode materi: " + kodeMateri;
			
			}else{
				result="data materi not found";
			}
			kafkaProcessing(result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public MateriRepository getMateriRepository() {
		return materiRepository;
	}

	public void setMateriRepository(MateriRepository materiRepository) {
		this.materiRepository = materiRepository;
	}


}
