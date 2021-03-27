package sb.mdr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
/*import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
*/import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import sb.mdr.model.Kelas;
import sb.mdr.model.redis.RedisKelas;
import sb.mdr.repository.KelasRepository;
import sb.mdr.repository.redis.RedisKelasRepository;

@Service
public class KelasService {

	@Autowired
	private KelasRepository kelasRepository;

	private Kelas kelas;
	
	private RedisKelas redisKelas;
	
	@Autowired
	private RedisKelasRepository redisKelasRepo;
	
	@Autowired
	private KafkaTemplate<String,Object> kafkaTemplate;
	
	private ObjectMapper mapper;

	public String insertKelas(Kelas localKelas) {
		int increment = 0;
		int flagInsert = 0;
		String result=null;
		String cekKelas = kelasRepository.getLastKodeKelas();
		if(cekKelas==null) {
			increment += 1;
			localKelas.setKodeKelas("KLS" + increment);

		}else {
			increment=kelasRepository.hitungKelas()+1;
			localKelas.setKodeKelas("KLS" + increment);
		}
		try {
			flagInsert = kelasRepository.insertDataKelas(localKelas.getKodeKelas(), localKelas.getNamaKelas(),
					localKelas.getKapasitasKelas(),localKelas.getJenisKelas());

			if(flagInsert==1) {
				
				redisKelas.setKodeKelas(localKelas.getKodeKelas());
				redisKelas.setNamaKelas(localKelas.getNamaKelas());
				redisKelas.setKapasitasKelas(localKelas.getKapasitasKelas());
				redisKelas.setJenisKelas(localKelas.getJenisKelas());
				redisKelasRepo.save(redisKelas);
				
				byte[] bytes = mapper.writeValueAsBytes(localKelas);
				String str = new String(bytes);
				/*kafkaTemplate.send("sbmdr",str);*/
				System.err.println("sending message: "+str);
				
				result="data kelas berhasil dimasukkan dengan kode kelas: "+cekKelas;
				kafkaTemplate.send("sbmdr","KLS", localKelas+"\n"+result);
			}
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
			kafkaTemplate.send("sbmdr","KLS", listKelas);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listKelas;
	}
	
	public Kelas getKelas(String kodeMatKul) {
		Kelas kelas = new Kelas();
		try {
			kelas= kelasRepository.getKelasByKodeKelas(kodeMatKul);
			kafkaTemplate.send("sbmdr","KLS", kelas);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return kelas;
	}

	public String deleteKelas(String kodeKelas) {
		int deleteFlag = 0;
		String result=null;
		try {
			deleteFlag = kelasRepository.deleteDataKelas(kodeKelas);
			if(deleteFlag==1) {
				
				result="data kelas dengan kode kelas: "+kodeKelas+" telah dihapus";
				kafkaTemplate.send("sbmdr","KLS", result);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateKelas(Kelas kelas,String kodeKelas) {
		int updateFlag = 0;
		String result=null;
		try {
			updateFlag = kelasRepository.updateDataKelas(kelas.getNamaKelas(),kelas.getKapasitasKelas(), 
					kelas.getJenisKelas(),kodeKelas);
			
			
			if(updateFlag==1) {
				kelas.setKodeKelas(kodeKelas);
				redisKelas.setKodeKelas(kelas.getKodeKelas());
				redisKelas.setNamaKelas(kelas.getNamaKelas());
				redisKelas.setKapasitasKelas(kelas.getKapasitasKelas());
				redisKelas.setJenisKelas(kelas.getJenisKelas());
				redisKelasRepo.save(redisKelas);
				
				byte[] bytes = mapper.writeValueAsBytes(kelas);
				String str = new String(bytes);
				/*kafkaTemplate.send("sbmdr",str);*/
				
				System.err.println("sending message: "+str);
				
				result="data kelas telah diperbaharui dengan kode kelas: "+kodeKelas;
				kafkaTemplate.send("sbmdr","KLS", kelas+"\n"+result);
			}
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

	public Kelas getKelas() {
		return kelas;
	}

	public void setKelas(Kelas kelas) {
		this.kelas = kelas;
	}




}
