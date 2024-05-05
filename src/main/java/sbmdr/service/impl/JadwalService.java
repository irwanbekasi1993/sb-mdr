package sbmdr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbmdr.config.KafkaConsumerConfig;
import sbmdr.config.KafkaProducerConfig;
import sbmdr.model.Dosen;
import sbmdr.model.Jadwal;
import sbmdr.model.Jurusan;
import sbmdr.model.Kelas;
import sbmdr.model.Mahasiswa;
import sbmdr.model.MataKuliah;
import sbmdr.model.Semester;
import sbmdr.payload.request.JadwalRequest;
import sbmdr.repository.JadwalRepository;

@Service
public class JadwalService {

@Autowired
private JadwalRepository jadwalRepository;

@Autowired
private DosenService dosenService;

@Autowired
private JurusanService jurusanService;

@Autowired
private KelasService kelasService;

@Autowired
private MataKuliahService matkulService;

@Autowired
private MahasiswaService mahasiswaService;

@Autowired
private SemesterService semesterService;

private int flag;

private KafkaProducerConfig kafkaProducerJadwal;
private KafkaConsumerConfig kafkaConsumerJadwal;

private void kafkaProcessing(String result){
    kafkaProducerJadwal.sendMessage("kafka producer jadwal produce result: "+result);
    kafkaConsumerJadwal.consumeMessage("kafka consumer jadwal consume result: "+result);
}

public List<Jadwal> listAllJadwal(int limit){
    return jadwalRepository.viewAllJadwal(limit);
}

public Jadwal insertJadwal(JadwalRequest jadwalRequest){
    
    if(cekJadwal(jadwalRequest)==null){
        Dosen dosen = dosenService.getDosen(jadwalRequest.getNip());
    Jurusan jurusan = jurusanService.getJurusan(jadwalRequest.getKodeJurusan());
    Kelas kelas = kelasService.getKelas(jadwalRequest.getKodeKelas());
    MataKuliah matkul = matkulService.getMatKul(jadwalRequest.getKodeMatkul());
    Mahasiswa mhs = mahasiswaService.getMahasiswa(jadwalRequest.getNim());
    Semester semester = semesterService.getSemester( jadwalRequest.getKodeSemester());

    Jadwal jadwal = new Jadwal();
    jadwal.setDosen(dosen);
    jadwal.setJurusan(jurusan);
    jadwal.setKelas(kelas);
    jadwal.setMataKuliah(matkul);
    jadwal.setMahasiswa(mhs);
    jadwal.setSemester(semester);

        Jadwal insertJadwal = jadwalRepository.save(jadwal);
        if(insertJadwal!=null){
            kafkaProcessing("jadwal successfully insert with id: "+insertJadwal.getId());
        }else{
            kafkaProcessing("jadwal already exists");
        }
    }
    return null;
}

public int updateJadwal(JadwalRequest jadwalRequest){
    if(cekJadwal(jadwalRequest)!=null){

        Dosen dosen = dosenService.getDosen(jadwalRequest.getNip());
    Jurusan jurusan = jurusanService.getJurusan(jadwalRequest.getKodeJurusan());
    Kelas kelas = kelasService.getKelas(jadwalRequest.getKodeKelas());
    MataKuliah matkul = matkulService.getMatKul(jadwalRequest.getKodeMatkul());
    Mahasiswa mhs = mahasiswaService.getMahasiswa(jadwalRequest.getNim());
    Semester semester = semesterService.getSemester( jadwalRequest.getKodeSemester());

        flag = jadwalRepository.updateJadwal(dosen.getNip(),
         jurusan.getKodeJurusan(),
          kelas.getKodeKelas(), 
          matkul.getKodeMatkul(),
          mhs.getNim(),
          semester.getKodeSemester());

          if(flag==1){
            kafkaProcessing("jadwal updated successfully with id: "+cekJadwal(jadwalRequest).getId());
          }else{
            kafkaProcessing("jadwal not found ");
          }
    }
    return 0;
}

public Jadwal cekJadwal(JadwalRequest jadwalRequest){
    Dosen dosen = dosenService.getDosen(jadwalRequest.getNip());
    Jurusan jurusan = jurusanService.getJurusan(jadwalRequest.getKodeJurusan());
    Kelas kelas = kelasService.getKelas(jadwalRequest.getKodeKelas());
    MataKuliah matkul = matkulService.getMatKul(jadwalRequest.getKodeMatkul());
    Mahasiswa mhs = mahasiswaService.getMahasiswa(jadwalRequest.getNim());
    Semester semester = semesterService.getSemester( jadwalRequest.getKodeSemester());

    return jadwalRepository.searchJadwal(dosen.getNip(),
     jurusan.getKodeJurusan(), 
     kelas.getKodeKelas(), 
     matkul.getKodeMatkul(),
     mhs.getNim(),
     semester.getKodeSemester());
}


public int deleteJadwal(JadwalRequest jadwalRequest){
    if(cekJadwal(jadwalRequest)!=null){
        Dosen dosen = dosenService.getDosen(jadwalRequest.getNip());
    Jurusan jurusan = jurusanService.getJurusan(jadwalRequest.getKodeJurusan());
    Kelas kelas = kelasService.getKelas(jadwalRequest.getKodeKelas());
    MataKuliah matkul = matkulService.getMatKul(jadwalRequest.getKodeMatkul());
    Mahasiswa mhs = mahasiswaService.getMahasiswa(jadwalRequest.getNim());
    Semester semester = semesterService.getSemester( jadwalRequest.getKodeSemester());


        flag= jadwalRepository.deleteJadwal(dosen.getNip(),
         jurusan.getKodeJurusan(),
          kelas.getKodeKelas(), 
          matkul.getKodeMatkul(),
          mhs.getNim(),
          semester.getKodeSemester());

          if(flag==1){
            kafkaProcessing("jadwal deleted successfully");
          }else{
            kafkaProcessing("jadwal not found");
          }
    }
    return 0;
    
}

}
