package sb.mdr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
/*import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;*/
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import sb.mdr.model.Soal;
import sb.mdr.model.redis.RedisSoal;
import sb.mdr.repository.SoalRepository;
import sb.mdr.repository.redis.RedisSoalRepository;

@Service
public class SoalService {

	@Autowired
	private SoalRepository soalRepository;

	private Soal soal;
	
	private RedisSoal redisSoal;
	
	@Autowired
	private RedisSoalRepository redisSoalRepo;
	
	@Autowired
	private KafkaTemplate<String,Object> kafkaTemplate;
	
	private ObjectMapper objectMapper;
	

	public String insertSoal(Soal localSoal) {
		int increment = 0;
		int flagInsert = 0;
		String result=null;
		String cekSoal = soalRepository.getLastKodeSoal();
		if(cekSoal==null) {
			increment += 1;
			localSoal.setKodeSoal("TGS" + increment);

		}else {
			increment=soalRepository.hitungSoal()+1;
			localSoal.setKodeSoal("TGS" + increment);
		}
		try {
			flagInsert = soalRepository.insertDataSoal(localSoal.getKodeSoal(), localSoal.getJenisSoal(),
					localSoal.getWaktuPengerjaan(),localSoal.getWaktuPengumpulan(),localSoal.getIsiSoal(),localSoal.getNilai());

			if(flagInsert==1) {
				
				redisSoal.setKodeSoal(localSoal.getKodeSoal());
				redisSoal.setJenisSoal(localSoal.getJenisSoal());
				redisSoal.setWaktuPengerjaan(localSoal.getWaktuPengerjaan());
				redisSoal.setWaktuPengumpulan(localSoal.getWaktuPengumpulan());
				redisSoal.setIsiSoal(localSoal.getIsiSoal());
				redisSoal.setNilai(localSoal.getNilai());
				redisSoalRepo.save(redisSoal);
				
				byte[]bytes = objectMapper.writeValueAsBytes(localSoal);
				String str = new String(bytes);
				/*kafkaTemplate.send("sbmdr",str);*/
				System.err.println("sending message: "+str);
				
				result="data semester berhasil dimasukkan dengan kode soal: "+cekSoal;
				kafkaTemplate.send("sbmdr","TGS", result);
			}
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
			kafkaTemplate.send("sbmdr","DSN", listSoal);
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
			kafkaTemplate.send("sbmdr","TGS", soal);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return soal;
	}

	public String deleteSoal(String kodeSoal) {
		int deleteFlag = 0;
		String result=null;
		try {
			deleteFlag = soalRepository.deleteDataSoal(kodeSoal);
			if(deleteFlag==1) {
				
				result="data soal dengan kode soal: "+kodeSoal+" telah dihapus";
				kafkaTemplate.send("sbmdr","TGS", result);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateSoal(Soal soal,String kodeSoal) {
		int updateFlag = 0;
		String result=null;
		try {
			updateFlag = soalRepository.updateDataSoal(soal.getJenisSoal(),soal.getWaktuPengerjaan(),
					soal.getWaktuPengumpulan(),soal.getIsiSoal(),soal.getNilai(),kodeSoal);
			if(updateFlag==1) {
				
				soal.setKodeSoal(kodeSoal);
				
				redisSoal.setKodeSoal(soal.getKodeSoal());
				redisSoal.setJenisSoal(soal.getJenisSoal());
				redisSoal.setWaktuPengerjaan(soal.getWaktuPengerjaan());
				redisSoal.setWaktuPengumpulan(soal.getWaktuPengumpulan());
				redisSoal.setIsiSoal(soal.getIsiSoal());
				redisSoal.setNilai(soal.getNilai());
				redisSoalRepo.save(redisSoal);
				
				byte[]bytes = objectMapper.writeValueAsBytes(soal);
				String str = new String(bytes);
				/*kafkaTemplate.send("sbmdr",str);*/
				System.err.println("sending message: "+str);
				
				result="data soal telah diperbaharui dengan kode soal: "+kodeSoal;
				kafkaTemplate.send("sbmdr","TGS", soal+"\n"+result);
			}
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

	public Soal getSoal() {
		return soal;
	}

	public void setSoal(Soal soal) {
		this.soal = soal;
	}





}
