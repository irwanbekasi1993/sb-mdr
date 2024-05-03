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

import sbmdr.model.Dosen;
import sbmdr.model.MataKuliah;
import sbmdr.model.redis.RedisMataKuliah;
import sbmdr.repository.DosenRepository;
import sbmdr.repository.MataKuliahRepository;
import sbmdr.repository.redis.RedisMataKuliahRepository;

@Service
public class MataKuliahService {

	@Autowired
	private MataKuliahRepository matKulRepository;
	
	@Autowired
	private RedisMataKuliahRepository redisMatkulRepo;
	
	// @Autowired
	// private KafkaTemplate<String,String> kafkaTemplate;
	
	private ObjectMapper objectMapper;
	
	
	public String insertMatKul(MataKuliah localMatKul) {
		String result=null;
		
		try {
			MataKuliah cekMatkul = getMatKul(localMatKul.getKodeMatkul());
			if(cekMatkul==null){
				 matKulRepository.insertDataMatKul(localMatKul.getKodeMatkul(), localMatKul.getNamaMatkul(),
					localMatKul.getSks());

				
				RedisMataKuliah redisMatkul = new RedisMataKuliah();
				redisMatkul.setKodeMatkul(localMatKul.getKodeMatkul());
				redisMatkul.setNamaMatkul(localMatKul.getNamaMatkul());
				redisMatkul.setSks(localMatKul.getSks());
				redisMatkulRepo.save(redisMatkul);
				
				objectMapper = new ObjectMapper();
				byte[] bytes = objectMapper.writeValueAsBytes(localMatKul);
				String str = new String(bytes);
				// kafkaTemplate.send("sbmdr",str);
				System.err.println("sending message: "+str);
				
				result="data matakuliah berhasil dimasukkan dengan kode matakuliah: "+localMatKul.getKodeMatkul();
			
			}else{
				result="data kuliah not found";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	

	public List<MataKuliah> getAllMatKul(int limit) {
		List<MataKuliah> listMatKul = new ArrayList<>();
		try {
			listMatKul = matKulRepository.selectAllMatKul(limit);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listMatKul;
	}
	
	public MataKuliah getMatKul(String kodeMatKul) {
		MataKuliah matKul = new MataKuliah();
		try {
			matKul= matKulRepository.getMatkulByKodeMatkul(kodeMatKul);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return matKul;
	}

	public String deleteMatKul(String kodeMatKul) {
		String result=null;
		try {
			MataKuliah cekMK = getMatKul(kodeMatKul);
			if(cekMK!=null){
				matKulRepository.deleteDataMatKul(kodeMatKul);
				redisMatkulRepo.delete(redisMatkulRepo.findByKodeMatKul(kodeMatKul));
				result="data mata kuliah dengan kode mata kuliah: "+kodeMatKul+" telah dihapus";
			
			}else{
				result="data mata kuliah not found";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateMatKul(MataKuliah matKul,String kodeMatKul) {
		String result=null;
		try {
			MataKuliah cekMatkul = getMatKul(kodeMatKul);
			if(cekMatkul!=null){
				 matKulRepository.updateDataMatKul(matKul.getNamaMatkul(),matKul.getSks(), kodeMatKul);
			
				
				matKul.setKodeMatkul(kodeMatKul);
				
				RedisMataKuliah redisMatkul = new RedisMataKuliah();
				redisMatkul.setKodeMatkul(matKul.getKodeMatkul());
				redisMatkul.setNamaMatkul(matKul.getNamaMatkul());
				redisMatkul.setSks(matKul.getSks());
				redisMatkulRepo.save(redisMatkul);
				
				objectMapper = new ObjectMapper();
				byte[] bytes = objectMapper.writeValueAsBytes(matKul);
				String str = new String(bytes);
				// kafkaTemplate.send("sbmdr",str);
				System.err.println("sending message: "+str);
				
				result="data mata kuliah telah diperbaharui dengan kode mata kuliah: "+kodeMatKul;
			
			}else{
				result="data mata kuliah not found";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public MataKuliahRepository getMatKulRepository() {
		return matKulRepository;
	}

	public void setMatKulRepository(MataKuliahRepository matKulRepository) {
		this.matKulRepository = matKulRepository;
	}




}