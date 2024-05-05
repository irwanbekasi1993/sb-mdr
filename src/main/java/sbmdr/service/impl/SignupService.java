package sbmdr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import sbmdr.config.KafkaConsumerConfig;
import sbmdr.config.KafkaProducerConfig;
import sbmdr.model.Dosen;
import sbmdr.model.Mahasiswa;
import sbmdr.model.redis.RedisDosen;
import sbmdr.model.redis.RedisMahasiswa;
import sbmdr.repository.DosenRepository;
import sbmdr.repository.MahasiswaRepository;
import sbmdr.repository.RoleRepository;
import sbmdr.repository.UserRepository;
import sbmdr.repository.redis.RedisDosenRepository;
import sbmdr.repository.redis.RedisMahasiswaRepository;

@Service
public class SignupService {

	@Autowired
	private RoleRepository roleRepository;

    	private ObjectMapper mapper = new ObjectMapper();

        @Autowired
        private DosenRepository dosenRepository;

        @Autowired
        private MahasiswaRepository mahasiswaRepository;

        @Autowired
        private RedisDosenRepository redisDosenRepo;

        @Autowired
        private RedisMahasiswaRepository redisMahasiswaRepo;

		private String result;

		private RedisDosen redisDosen;

		private RedisMahasiswa redisMahasiswa;

		private KafkaProducerConfig kafkaProducerSignUp;
private KafkaConsumerConfig kafkaConsumerSignUp;

private void kafkaProcessing(String result){
    kafkaProducerSignUp.sendMessage("kafka producer sign up produce result: "+result);
    kafkaConsumerSignUp.consumeMessage("kafka consumer sign up consume result: "+result);
}

	public String insertDosen(Dosen localDosen) {
		
		try {
			if(roleRepository.findByName("ROLE_DOSEN").isPresent()){
				Dosen cekDosen = dosenRepository.getDosenByNip(localDosen.getNip());

				if(cekDosen==null){
					dosenRepository.insertDataDosen(localDosen.getNip(), localDosen.getNamaDosen(),
					localDosen.getEmail(),localDosen.getStatusDosen());

			redisDosen.setNip(localDosen.getNip());
			redisDosen.setNamaDosen(localDosen.getNamaDosen());
			redisDosen.setEmail(localDosen.getEmail());
			redisDosenRepo.save(redisDosen);
				
				byte[]bytes = mapper.writeValueAsBytes(localDosen);
				String str= new String(bytes);
				
				System.err.println("sending message: "+str);
				
				// kafkaTemplate.send("sbmdr", str);
				//kafkaConsumer.subscribe(Collections.singletonList("sbmdr"));
				
				result = "data dosen berhasil dimasukkan dengan kode dosen: " + localDosen;
			
				}else{
					result = "data dosen sudah ada";
				}
				kafkaProcessing(result);
                
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}


    	public String insertMahasiswa(Mahasiswa localMahasiswa) {
		
		try {
			if(roleRepository.findByName("ROLE_MAHASISWA").isPresent()){
				Mahasiswa cekMhs = mahasiswaRepository.getMahasiswaByNim(localMahasiswa.getNim());
                if(cekMhs==null){
					mahasiswaRepository.insertDataMahasiswa(localMahasiswa.getNim(), localMahasiswa.getNamaMahasiswa(),
				localMahasiswa.getEmail(),localMahasiswa.getStatusMahasiswa());

			
			redisMahasiswa.setNim(localMahasiswa.getNim());
			redisMahasiswa.setNamaMahasiswa(localMahasiswa.getNamaMahasiswa());
			redisMahasiswa.setEmail(localMahasiswa.getEmail());
			redisMahasiswaRepo.save(redisMahasiswa);
			
			byte[]bytes = mapper.writeValueAsBytes(localMahasiswa);
			String str = new String(bytes);
			// kafkaTemplate.send("sbmdr",str);
			System.err.println("sending message: "+str);
			
			result="data mahasiswa berhasil dimasukkan dengan nim: "+localMahasiswa;
		
				
	}else{
		result="data mahasiswa sudah ada";
	}
	kafkaProcessing(result);
}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
}
		