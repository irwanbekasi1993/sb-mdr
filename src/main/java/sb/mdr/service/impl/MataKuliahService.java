package sb.mdr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
*/
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import sb.mdr.model.Dosen;
import sb.mdr.model.MataKuliah;
import sb.mdr.model.redis.RedisMataKuliah;
import sb.mdr.repository.DosenRepository;
import sb.mdr.repository.MataKuliahRepository;
import sb.mdr.repository.redis.RedisMataKuliahRepository;

@Service
public class MataKuliahService {

	@Autowired
	private MataKuliahRepository matKulRepository;

	private MataKuliah matkul;

	private RedisMataKuliah redisMatkul;
	
	@Autowired
	private RedisMataKuliahRepository redisMatkulRepo;
	
/*	@Autowired
	private KafkaTemplate<String,String> kafkaTemplate;
*/	
	private ObjectMapper objectMapper;
	
	
	public String insertMatKul(MataKuliah localMatKul) {
		int increment = 0;
		int flagInsert = 0;
		String result=null;
		String cekMatKul = matKulRepository.getLastKodeMatKul();
		if(cekMatKul==null) {
			increment += 1;
			localMatKul.setKodeMatkul("MK" + increment);

		}else {
			increment=matKulRepository.hitungMatKul()+1;
			localMatKul.setKodeMatkul("MK" + increment);
		}
		try {
			flagInsert = matKulRepository.insertDataMatKul(localMatKul.getKodeMatkul(), localMatKul.getNamaMatkul(),
					localMatKul.getSks());

			if(flagInsert==1) {
				
				redisMatkul.setKodeMatkul(localMatKul.getKodeMatkul());
				redisMatkul.setNamaMatkul(localMatKul.getNamaMatkul());
				redisMatkul.setSks(localMatKul.getSks());
				redisMatkulRepo.save(redisMatkul);
				
				byte[] bytes = objectMapper.writeValueAsBytes(localMatKul);
				String str = new String(bytes);
				/*kafkaTemplate.send("sbmdr",str);*/
				System.err.println("sending message: "+str);
				
				result="data matakuliah berhasil dimasukkan dengan kode matakuliah: "+cekMatKul;
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
			matKul= matKulRepository.getMatkulByKodeMatKul(kodeMatKul);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return matKul;
	}

	public String deleteMatKul(String kodeMatKul) {
		int deleteFlag = 0;
		String result=null;
		try {
			deleteFlag = matKulRepository.deleteDataMatKul(kodeMatKul);
			if(deleteFlag==1) {
				
				result="data mata kuliah dengan kode mata kuliah: "+kodeMatKul+" telah dihapus";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateMatKul(MataKuliah matKul,String kodeMatKul) {
		int updateFlag = 0;
		String result=null;
		try {
			updateFlag = matKulRepository.updateDataMatKul(matKul.getNamaMatkul(),matKul.getSks(), kodeMatKul);
			if(updateFlag==1) {
				
				matKul.setKodeMatkul(kodeMatKul);
				
				redisMatkul.setKodeMatkul(matKul.getKodeMatkul());
				redisMatkul.setNamaMatkul(matKul.getNamaMatkul());
				redisMatkul.setSks(matKul.getSks());
				redisMatkulRepo.save(redisMatkul);
				
				byte[] bytes = objectMapper.writeValueAsBytes(matKul);
				String str = new String(bytes);
				/*kafkaTemplate.send("sbmdr",str);*/
				System.err.println("sending message: "+str);
				
				result="data mata kuliah telah diperbaharui dengan kode mata kuliah: "+kodeMatKul;
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

	public MataKuliah getMatkul() {
		return matkul;
	}

	public void setMatkul(MataKuliah matkul) {
		this.matkul = matkul;
	}



}
