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
import sbmdr.model.Kelas;
import sbmdr.model.MataKuliah;
import sbmdr.model.redis.RedisKelas;
import sbmdr.repository.DosenRepository;
import sbmdr.repository.KelasRepository;
import sbmdr.repository.MataKuliahRepository;
import sbmdr.repository.redis.RedisKelasRepository;

@Service
public class KelasService {

	@Autowired
	private KelasRepository kelasRepository;
	
	private RedisKelas redisKelas;
	
	@Autowired
	private RedisKelasRepository redisKelasRepo;
	
	// @Autowired
	// private KafkaTemplate<String,String> kafkaTemplate;
	
	private ObjectMapper mapper;

	private String result;

	private KafkaProducerConfig kafkaProducerKelas;
private KafkaConsumerConfig kafkaConsumerKelas;

private void kafkaProcessing(String result){
    kafkaProducerKelas.sendMessage("kafka producer kelas produce result: "+result);
    kafkaConsumerKelas.consumeMessage("kafka consumer kelas consume result: "+result);
}

	public String insertKelas(Kelas localKelas) {
		
		try {
			Kelas cek = getKelas(localKelas.getNamaKelas());
			if(cek==null){
				kelasRepository.insertDataKelas(localKelas.getKodeKelas(), localKelas.getNamaKelas(),
					localKelas.getKapasitasKelas(),localKelas.getJenisKelas());

				redisKelas.setKodeKelas(localKelas.getKodeKelas());
				redisKelas.setNamaKelas(localKelas.getNamaKelas());
				redisKelas.setKapasitasKelas(localKelas.getKapasitasKelas());
				redisKelas.setJenisKelas(localKelas.getJenisKelas());
				redisKelasRepo.save(redisKelas);
				
				byte[] bytes = mapper.writeValueAsBytes(localKelas);
				String str = new String(bytes);
				// kafkaTemplate.send("sbmdr",str);
				System.err.println("sending message: "+str);
				
				result="data kelas berhasil dimasukkan dengan kode kelas: "+localKelas.getKodeKelas();
			
			}else{
				result="data kelas not found";
			}
			kafkaProcessing(result);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public List<Kelas> getAllKelas(int limit) {
		List<Kelas> listKelas = new ArrayList<>();
		try {
			listKelas = kelasRepository.selectAllKelas(limit);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listKelas;
	}
	
	public Kelas getKelas(String kodeKelas) {
		Kelas kelas = new Kelas();
		try {
			kelas= kelasRepository.getKelasByKodeKelas(kodeKelas);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return kelas;
	}

	public String deleteKelas(String kodeKelas) {
		try {
			Kelas cek = getKelas(kodeKelas);
			if(cek!=null){
				kelasRepository.deleteDataKelas(kodeKelas);
				redisKelasRepo.delete(redisKelasRepo.findByKodeKelas(kodeKelas));
				result="data kelas dengan kode kelas: "+kodeKelas+" telah dihapus";
			
			}else{
				result="data kelas not found";
			}
			kafkaProcessing(result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateKelas(Kelas kelas,String namaKelas) {
		try {
			Kelas cekKls = getKelas(namaKelas);
			if(cekKls!=null) {
			
			kelasRepository.updateDataKelas(kelas.getNamaKelas(),kelas.getKapasitasKelas(), 
					kelas.getJenisKelas(),namaKelas);

				redisKelas.setKodeKelas(kelas.getKodeKelas());
				redisKelas.setNamaKelas(kelas.getNamaKelas());
				redisKelas.setKapasitasKelas(kelas.getKapasitasKelas());
				redisKelas.setJenisKelas(kelas.getJenisKelas());
				redisKelasRepo.save(redisKelas);
				
				byte[] bytes = mapper.writeValueAsBytes(kelas);
				String str = new String(bytes);
				// kafkaTemplate.send("sbmdr",str);
				
				System.err.println("sending message: "+str);
				
				result="data kelas telah diperbaharui dengan kode kelas: "+cekKls;
			
		}else{
			result="data kelas not found";
		}
		kafkaProcessing(result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	

	public KelasRepository getKelasRepository() {
		return kelasRepository;
	}

	public void setKelasRepository(KelasRepository kelasRepository) {
		this.kelasRepository = kelasRepository;
	}





}
