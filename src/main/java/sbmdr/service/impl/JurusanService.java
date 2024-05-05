package sbmdr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import sbmdr.config.KafkaConsumerConfig;
import sbmdr.config.KafkaProducerConfig;
import sbmdr.model.Dosen;
import sbmdr.model.Jurusan;
import sbmdr.model.MataKuliah;
import sbmdr.model.redis.RedisJurusan;
import sbmdr.repository.DosenRepository;
import sbmdr.repository.JurusanRepository;
import sbmdr.repository.MataKuliahRepository;
import sbmdr.repository.redis.RedisJurusanRepository;

@Service
public class JurusanService {

	@Autowired
	private JurusanRepository jurusanRepository;
	
	private RedisJurusan redisJurusan;
	
	@Autowired
	private RedisJurusanRepository redisJurusanRepo;
	
	// @Autowired
	// private KafkaTemplate<String,String> kafkaTemplate;
	
	private ObjectMapper mapper;

	private String result;

	private KafkaProducerConfig kafkaProducerJurusan;
private KafkaConsumerConfig kafkaConsumerJurusan;

private void kafkaProcessing(String result){
    kafkaProducerJurusan.sendMessage("kafka producer jurusan produce result: "+result);
    kafkaConsumerJurusan.consumeMessage("kafka consumer jurusan consume result: "+result);
}

	public String insertJurusan(Jurusan localJurusan) {

		try {
			Jurusan cekJur = getJurusan(localJurusan.getKodeJurusan());
			if(cekJur==null){
				jurusanRepository.insertDataJurusan(localJurusan.getKodeJurusan(), localJurusan.getNamaJurusan());
				
				redisJurusan.setKodeJurusan(localJurusan.getKodeJurusan());
				redisJurusan.setNamaJurusan(localJurusan.getNamaJurusan());
				redisJurusanRepo.save(redisJurusan);
				
				byte[] bytes = mapper.writeValueAsBytes(localJurusan);
				String str = new String(bytes);
				
				// kafkaTemplate.send("sbmdr",str);
				result="data jurusan berhasil dimasukkan dengan kode jurusan: "+localJurusan.getKodeJurusan();
			
			}else{
				result="data jurusan not found";
			}
			kafkaProcessing(result);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	

	public List<Jurusan> getAllJurusan(int limit) {
		List<Jurusan> listJurusan = new ArrayList<>();
		try {
			listJurusan = jurusanRepository.selectAllJurusan(limit);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listJurusan;
	}
	
	public Jurusan getJurusan(String kodeJurusan) {
		Jurusan jurusan = new Jurusan();
		try {
			jurusan= jurusanRepository.getJurusanByKodeJurusan(kodeJurusan);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return jurusan;
	}

	public String deleteJurusan(String kodeJurusan) {
		try {
			Jurusan cek = getJurusan(kodeJurusan);
			if(cek!=null){
				jurusanRepository.deleteDataJurusan(kodeJurusan);
				redisJurusanRepo.delete(redisJurusanRepo.findByKodeJurusan(kodeJurusan));
				result="data jurusan dengan kode jurusan: "+kodeJurusan+" telah dihapus";
			
			}else{
				result = "data jurusan not found";
			}
			 kafkaProcessing(result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateJurusan(Jurusan jurusan,String kodeJurusan) {
		
		try {
			Jurusan cek = getJurusan(kodeJurusan);
			if(cek!=null){
				jurusanRepository.updateDataJurusan(jurusan.getNamaJurusan(),kodeJurusan);
				
				jurusan.setKodeJurusan(kodeJurusan);
				
				redisJurusan.setKodeJurusan(jurusan.getKodeJurusan());
				redisJurusan.setNamaJurusan(jurusan.getNamaJurusan());
				redisJurusanRepo.save(redisJurusan);
				
				byte[]bytes = mapper.writeValueAsBytes(jurusan);
				String str = new String(bytes);
				// kafkaTemplate.send("sbmdr",str);
				
				System.err.println("sending message: "+str);
				
				
				result="data jurusan telah diperbaharui dengan kode jurusan: "+kodeJurusan;
			
			}else{
				result="data jurusan not found";
			}
			kafkaProcessing(result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}



}
