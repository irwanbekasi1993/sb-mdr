package sb.mdr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import sb.mdr.model.Dosen;
import sb.mdr.model.Jadwal;
import sb.mdr.model.pojo.JadwalDto;
import sb.mdr.repository.DosenRepository;
import sb.mdr.repository.JadwalRepository;
import sb.mdr.repository.JurusanRepository;
import sb.mdr.repository.KelasRepository;
import sb.mdr.repository.MahasiswaRepository;
import sb.mdr.repository.MataKuliahRepository;
import sb.mdr.repository.SemesterRepository;

@Service
public class JadwalService {

	@Autowired
	private JadwalRepository jadwalRepository;

	private Jadwal jadwal;
	
	@Autowired
	private KelasRepository kelasRepository;
	
	@Autowired
	private MataKuliahRepository matkulRepository;
	
	@Autowired
	private MahasiswaRepository mahasiswaRepository;
	
	@Autowired
	private DosenRepository dosenRepository;
	
	@Autowired
	private SemesterRepository semesterRepository;
	
	@Autowired
	private JurusanRepository jurusanRepository;
	

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private KafkaConsumer<String, Dosen> kafkaConsumer;
	
	private ObjectMapper mapper = new ObjectMapper();

	public String insertJadwal(JadwalDto jadwalDto) {
		int increment = 0;
		int flagInsert = 0;
		String result = null;
		String cekJadwal = jadwalRepository.getLastKodeJadwal();
		Jadwal localJadwal = new Jadwal();
		if (cekJadwal == null) {
			increment += 1;
			localJadwal.setKodeJadwal("JDW" + increment);
			jadwalDto.setKodeJadwal(localJadwal.getKodeJadwal());

		} else {
			increment = jadwalRepository.hitungJadwal() + 1;
			localJadwal.setKodeJadwal("DSN" + increment);
			jadwalDto.setKodeJadwal(localJadwal.getKodeJadwal());
		}
		try {
			flagInsert = jadwalRepository.insertDataJadwal(kelasRepository.findById(jadwalDto.getKodeKelas()), matkulRepository.findById(jadwalDto.getKodeMatkul()),
					mahasiswaRepository.findById(jadwalDto.getNim()),
					dosenRepository.findById(jadwalDto.getKodeDosen()), semesterRepository.findById(jadwalDto.getKodeSemester()),
					jurusanRepository.findById(jadwalDto.getKodeJurusan()));


			if (flagInsert == 1) {
				
				byte[]bytes = mapper.writeValueAsBytes(jadwal);
				String str= new String(bytes);
				
				System.err.println("sending message: "+str);
				
				kafkaTemplate.send("sbmdr", str);
				//kafkaConsumer.subscribe(Collections.singletonList("sbmdr"));
				
				result = "data dosen berhasil dimasukkan dengan kode dosen: " + cekJadwal;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public List<Jadwal> getAllJadwal(int limit) {
		List<Jadwal> listJadwal = new ArrayList<>();
		try {
			listJadwal = jadwalRepository.selectAllJadwal(limit);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listJadwal;
	}

	public Jadwal getJadwal(String kodeJadwal) {
		Jadwal jadwal = new Jadwal();
		try {
			jadwal = jadwalRepository.getJadwalByKodeJadwal(kodeJadwal);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return jadwal;
	}

	public String deleteJadwal(String kodeJadwal) {
		int deleteFlag = 0;
		String result = null;
		try {
			deleteFlag = jadwalRepository.deleteDataJadwal(kodeJadwal);
			if (deleteFlag == 1) {
				result = "data dosen dengan kode dosen: " + kodeJadwal + " telah dihapus";
				kafkaTemplate.send("sbmdr", result);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateJadwal(JadwalDto jadwalDto,String kodeJadwal) {
		int updateFlag = 0;
		String result = null;
		try {
			updateFlag = jadwalRepository.updateDataJadwal(kelasRepository.findById(jadwalDto.getKodeKelas()), matkulRepository.findById(jadwalDto.getKodeMatkul()),
					mahasiswaRepository.findById(jadwalDto.getNim()),
					dosenRepository.findById(jadwalDto.getKodeDosen()), semesterRepository.findById(jadwalDto.getKodeSemester()),
					jurusanRepository.findById(jadwalDto.getKodeJurusan()),
					kodeJadwal);
			if (updateFlag == 1) {
				byte[]bytes = mapper.writeValueAsBytes(jadwal);
				String str= new String(bytes);
				
				System.err.println("sending message: "+str);
				
				//kafkaConsumer.subscribe(Collections.singletonList("sbmdr"));
				result = "data dosen telah diperbaharui dengan kode dosen: " + kodeJadwal;
				kafkaTemplate.send("sbmdr", result);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public JadwalRepository getJadwalRepository() {
		return jadwalRepository;
	}

	public void setJadwalRepository(JadwalRepository jadwalRepository) {
		this.jadwalRepository = jadwalRepository;
	}

	public Jadwal getJadwal() {
		return jadwal;
	}

	public void setJadwal(Jadwal jadwal) {
		this.jadwal = jadwal;
	}
	

}
