package sbmdr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import sbmdr.model.Dosen;
import sbmdr.model.Jadwal;
import sbmdr.model.Jurusan;
import sbmdr.model.Kelas;
import sbmdr.model.MataKuliah;
import sbmdr.payload.request.JadwalRequest;
import sbmdr.payload.response.MessageResponse;
import sbmdr.service.impl.DosenService;
import sbmdr.service.impl.JadwalService;
import sbmdr.service.impl.JurusanService;
import sbmdr.service.impl.KelasService;
import sbmdr.service.impl.MataKuliahService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping("/sbmdr/test/v1/jadwal")
public class JadwalController {

@Autowired
private JadwalService jadwalService;



@GetMapping("/list")
public ResponseEntity<?> listAllJadwal(@PathVariable("limit") int limit) {
    List<Jadwal> listJadwal = jadwalService.listAllJadwal(limit);
    if(listJadwal.isEmpty()){
        return ResponseEntity.badRequest().body(new MessageResponse("jadwal not found"));
    }
    return ResponseEntity.ok(listJadwal);
}

@PostMapping("/insert")
public ResponseEntity<?> insertJadwal(@RequestBody JadwalRequest jadwal) {
    //TODO: process POST request

    Jadwal searchJadwal = jadwalService.cekJadwal(jadwal);
    if(searchJadwal==null){
        Jadwal insertJadwal = jadwalService.insertJadwal(jadwal);
        return ResponseEntity.ok(insertJadwal);
    }
    return ResponseEntity.badRequest().body(new MessageResponse("jadwal already exists"));
    
}

@PutMapping("/update")
public ResponseEntity<?> updateJadwal(@RequestBody JadwalRequest jadwal) {
    //TODO: process PUT request
    Jadwal searchJadwal = jadwalService.cekJadwal(jadwal);
    if(searchJadwal==null){
        return ResponseEntity.badRequest().body(new MessageResponse("jadwal not found"));
    }
        jadwalService.updateJadwal(jadwal);
        return ResponseEntity.ok(new MessageResponse("jadwal updated successfully"));
    
}

@GetMapping("/{nip}/{kodeJurusan}/{kodeKelas}/{kodeMatkul}/{nim}/{kodeSemester}")
public ResponseEntity<?> cekJadwal(@PathVariable("nip") String nip, 
@PathVariable("kodeJurusan")String kodeJurusan,
@PathVariable("kodeKelas")String kodeKelas,
 @PathVariable("kodeMatkul")String kodeMatkul,
 @PathVariable("nim")String nim,
 @PathVariable("kodeSemester")String kodeSemester) {
    JadwalRequest jadwal = new JadwalRequest();
   
    jadwal.setNip(nip);
    jadwal.setKodeJurusan(kodeJurusan);
    jadwal.setKodeKelas(kodeKelas);
    jadwal.setKodeMatkul(kodeMatkul);
    jadwal.setNim(nim);
    jadwal.setKodeSemester(kodeSemester);

    Jadwal searchJadwal = jadwalService.cekJadwal(jadwal);
    if(searchJadwal!=null ){

        return ResponseEntity.ok(jadwal);
    }
    return ResponseEntity.badRequest().body(new MessageResponse("jadwal not found"));
}

@DeleteMapping("/delete/{nip}/{kodeJurusan}/{kodeKelas}/{kodeMatkul}/{nim}/{kodesSemester}")
public ResponseEntity<?> deleteJadwal(@PathVariable("nip")String nip,
@PathVariable("kodeJurusan")String kodeJurusan,
@PathVariable("kodeKelas")String kodeKelas,
@PathVariable("kodeMatkul")String kodeMatkul,
@PathVariable("nim")String nim,
@PathVariable("kodeSemester")String kodeSemester){
    JadwalRequest jadwal = new JadwalRequest();
    
    jadwal.setNip(nip);
    jadwal.setKodeJurusan(kodeJurusan);
    jadwal.setKodeKelas(kodeKelas);
    jadwal.setKodeMatkul(kodeMatkul);
    jadwal.setNim(nim);
    jadwal.setKodeSemester(kodeSemester);

    Jadwal searchJadwal = jadwalService.cekJadwal(jadwal);
    if(searchJadwal!=null ){

        jadwalService.deleteJadwal(jadwal);
        return ResponseEntity.ok().body(new MessageResponse("jadwal deleted successfully"));
    }
    return ResponseEntity.badRequest().body(new MessageResponse("jadwal not found"));
}

}
