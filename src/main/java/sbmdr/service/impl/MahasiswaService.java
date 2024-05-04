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
import sbmdr.model.Mahasiswa;
import sbmdr.model.redis.RedisMahasiswa;
import sbmdr.repository.DosenRepository;
import sbmdr.repository.MahasiswaRepository;
import sbmdr.repository.RoleRepository;
import sbmdr.repository.UserRepository;
import sbmdr.repository.redis.RedisMahasiswaRepository;

@Service
public class MahasiswaService {

	@Autowired
	private MahasiswaRepository mahasiswaRepository;

	
	private RedisMahasiswa redisMahasiswa;
	
	@Autowired
	private RedisMahasiswaRepository redisMahasiswaRepo;
	
	// @Autowired
	// private KafkaTemplate<String,String> kafkaTemplate;
	
	private ObjectMapper objectMapper;

	private String result;
	
	public List<Mahasiswa> getAllMahasiswa(int limit) {
		List<Mahasiswa> listMahasiswa = new ArrayList<>();
		try {
			listMahasiswa = mahasiswaRepository.selectAllMahasiswa(limit);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listMahasiswa;
	}
	
	public Mahasiswa getMahasiswa(String nim) {
		Mahasiswa mahasiswa = new Mahasiswa();
		try {
			mahasiswa= mahasiswaRepository.getMahasiswaByNim(nim);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return mahasiswa;
	}

	public String deleteMahasiswa(String nim) {
		try {
			Mahasiswa cekMhs = getMahasiswa(nim);
			if(cekMhs!=null){
				 mahasiswaRepository.deleteDataMahasiswa(nim);
				redisMahasiswaRepo.delete(redisMahasiswaRepo.findByNim(nim));
				result="data mahasiswa dengan nim: "+nim+" telah dihapus";
			
			}else{
				result="data mahasiswa not found";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateMahasiswa(Mahasiswa mahasiswa,String nim) {
		try {
			Mahasiswa cekMhs = getMahasiswa(nim);
			if(cekMhs!=null){
				mahasiswaRepository.updateDataMahasiswa(mahasiswa.getNamaMahasiswa(),mahasiswa.getJenisKelaminMahasiswa(),
					mahasiswa.getAlamatMahasiswa(),mahasiswa.getNohp(), mahasiswa.getEmail(),mahasiswa.getStatusMahasiswa(), nim);
				
				redisMahasiswa.setNim(nim);
				redisMahasiswa.setNamaMahasiswa(mahasiswa.getNamaMahasiswa());
				redisMahasiswa.setJenisKelaminMahasiswa(mahasiswa.getJenisKelaminMahasiswa());
				redisMahasiswa.setAlamatMahasiswa(mahasiswa.getAlamatMahasiswa());
				redisMahasiswa.setEmail(mahasiswa.getEmail());
				redisMahasiswa.setNohp(mahasiswa.getNohp());
				redisMahasiswa.setStatusMahasiswa(mahasiswa.getStatusMahasiswa());
				redisMahasiswaRepo.save(redisMahasiswa);
				
				mahasiswa.setNim(nim);
				byte[]bytes = objectMapper.writeValueAsBytes(mahasiswa);
				String str = new String(bytes);
				// kafkaTemplate.send("sbmdr",str);
				
				System.err.println("sending message: "+str);
				
				
				result="data mahasiswa telah diperbaharui dengan nim: "+nim;
			
			}else{
				result = "data mahasiswa not found";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public MahasiswaRepository getMahasiswaRepository() {
		return mahasiswaRepository;
	}

	public void setMahasiswaRepository(MahasiswaRepository mahasiswaRepository) {
		this.mahasiswaRepository = mahasiswaRepository;
	}




}
