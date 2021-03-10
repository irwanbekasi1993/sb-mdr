package sb.mdr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sb.mdr.model.Dosen;
import sb.mdr.model.Mahasiswa;
import sb.mdr.repository.DosenRepository;
import sb.mdr.repository.MahasiswaRepository;

@Service
public class MahasiswaService {

	@Autowired
	private MahasiswaRepository mahasiswaRepository;

	private Mahasiswa mahasiswa;

	public String insertMahasiswa(Mahasiswa localMahasiswa) {
		int increment = 0;
		int flagInsert = 0;
		String result=null;
		String cekMahasiswa = mahasiswaRepository.getLastNim();
		if(cekMahasiswa==null) {
			increment += 1;
			localMahasiswa.setNim("DSN" + increment);

		}else {
			increment=mahasiswaRepository.hitungMahasiswa()+1;
			localMahasiswa.setNim("DSN" + increment);
		}
		try {
			flagInsert = mahasiswaRepository.insertDataMahasiswa(localMahasiswa.getNim(), localMahasiswa.getNamaMahasiswa(),
					localMahasiswa.getJenisKelaminMahasiswa(), 
					localMahasiswa.getAlamatMahasiswa(), localMahasiswa.getEmail(), localMahasiswa.getNohp(),
					localMahasiswa.getStatusMahasiswa());

			if(flagInsert==1) {
				result="data mahasiswa berhasil dimasukkan dengan nim: "+cekMahasiswa;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

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
		int deleteFlag = 0;
		String result=null;
		try {
			deleteFlag = mahasiswaRepository.deleteDataMahasiswa(nim);
			if(deleteFlag==1) {
				
				result="data mahasiswa dengan nim: "+nim+" telah dihapus";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateMahasiswa(Mahasiswa mahasiswa,String nim) {
		int updateFlag = 0;
		String result=null;
		try {
			updateFlag = mahasiswaRepository.updateDataMahasiswa(mahasiswa.getNamaMahasiswa(),mahasiswa.getJenisKelaminMahasiswa(),
					mahasiswa.getAlamatMahasiswa(),mahasiswa.getNohp(), mahasiswa.getEmail(),mahasiswa.getStatusMahasiswa(), nim);
			if(updateFlag==1) {
				result="data mahasiswa telah diperbaharui dengan nim: "+nim;
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

	public Mahasiswa getMahasiswa() {
		return mahasiswa;
	}

	public void setMahasiswa(Mahasiswa mahasiswa) {
		this.mahasiswa = mahasiswa;
	}



}
