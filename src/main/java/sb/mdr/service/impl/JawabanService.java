package sb.mdr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import sb.mdr.model.Dosen;
import sb.mdr.model.Jawaban;
import sb.mdr.model.MataKuliah;
import sb.mdr.model.Semester;
import sb.mdr.model.Soal;
import sb.mdr.model.redis.RedisDosen;
import sb.mdr.model.redis.RedisJawaban;
import sb.mdr.repository.DosenRepository;
import sb.mdr.repository.JawabanRepository;
import sb.mdr.repository.MataKuliahRepository;
import sb.mdr.repository.SemesterRepository;
import sb.mdr.repository.SoalRepository;
import sb.mdr.repository.redis.RedisDosenRepository;
import sb.mdr.repository.redis.RedisJawabanRepository;

@Service
public class JawabanService {

	@Autowired
	private JawabanRepository jawabanRepository;

	private Jawaban jawaban;
	
	@Autowired
	private RedisJawabanRepository redisJawabanRepo;

	private RedisJawaban redisJawaban = new RedisJawaban();
	
	private ObjectMapper mapper;
	
	private KafkaTemplate<String,String> kafkaTemplate;


	public String insertJawaban(Jawaban localJawaban) {
		int increment = 0;
		int flagInsert = 0;
		String result=null;
		String cekJawaban = jawabanRepository.getLastKodeJawaban();
		if(cekJawaban==null) {
			increment += 1;
			localJawaban.setKodeJawaban("JWB" + increment);

		}else {
			increment=jawabanRepository.hitungJawaban()+1;
			localJawaban.setKodeJawaban("JWB" + increment);
		}
		try {

			if(flagInsert==1) {
				flagInsert = jawabanRepository.insertDataJawaban(localJawaban.getKodeJawaban(), 
						localJawaban.getJenisJawaban(),
						localJawaban.getIsiJawaban());
				
				redisJawaban.setKodeJawaban(localJawaban.getKodeJawaban());
				redisJawaban.setJenisJawaban(localJawaban.getJenisJawaban());
				redisJawaban.setIsiJawaban(localJawaban.getIsiJawaban());
				redisJawabanRepo.save(redisJawaban);
				
				byte[]bytes = mapper.writeValueAsBytes(localJawaban);
				String str= new String(bytes);
				
				System.err.println("sending message: "+str);
				
				kafkaTemplate.send("sbmdr", str);
				//kafkaConsumer.subscribe(Collections.singletonList("sbmdr"));
				receiveMessage("sbmdr");
				result="data jawaban berhasil dimasukkan dengan kode jawaban: "+cekJawaban;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	@KafkaListener(topics="sbmdr",groupId="sbmdr")
	public void receiveMessage(String data) {
		System.err.println("receive message: "+data);
	}

	public List<Jawaban> getAllJawaban(int limit) {
		List<Jawaban> listJawaban= new ArrayList<>();
		try {
			listJawaban = jawabanRepository.selectAllJawaban(limit);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listJawaban;
	}
	
	public Jawaban getJawaban(String kodeJawaban) {
		Jawaban jawaban = new Jawaban();
		try {
			jawaban= jawabanRepository.getJawabanByKodeJawaban(kodeJawaban);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return jawaban;
	}

	public String deleteJawaban(String kodeJawaban) {
		int deleteFlag = 0;
		String result=null;
		try {
			deleteFlag = jawabanRepository.deleteDataJawaban(kodeJawaban);
			if(deleteFlag==1) {
				
				result="data jawaban dengan kode jawaban: "+kodeJawaban+" telah dihapus";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateJawaban(Jawaban jawaban,String kodeJawaban) {
		int updateFlag = 0;
		String result=null;
		try {
			updateFlag = jawabanRepository.updateDataJawaban(jawaban.getJenisJawaban(),jawaban.getIsiJawaban()
					,kodeJawaban);
			
			
			if(updateFlag==1) {
				jawaban.setKodeJawaban(kodeJawaban);
				
				redisJawaban.setKodeJawaban(jawaban.getKodeJawaban());
				redisJawaban.setJenisJawaban(jawaban.getJenisJawaban());
				redisJawaban.setIsiJawaban(jawaban.getIsiJawaban());
				redisJawabanRepo.save(redisJawaban);
				
				byte[]bytes = mapper.writeValueAsBytes(jawaban);
				String str= new String(bytes);
				
				System.err.println("sending message: "+str);
				
				kafkaTemplate.send("sbmdr", str);
				result="data soal telah diperbaharui dengan kode soal: "+kodeJawaban;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public JawabanRepository getJawabanRepository() {
		return jawabanRepository;
	}

	public void setJawabanRepository(JawabanRepository jawabanRepository) {
		this.jawabanRepository = jawabanRepository;
	}

	public Jawaban getJawaban() {
		return jawaban;
	}

	public void setJawaban(Jawaban jawaban) {
		this.jawaban = jawaban;
	}




}
