package sb.mdr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sb.mdr.model.Dosen;
import sb.mdr.model.MataKuliah;
import sb.mdr.model.Materi;
import sb.mdr.model.Semester;
import sb.mdr.model.Soal;
import sb.mdr.repository.DosenRepository;
import sb.mdr.repository.MataKuliahRepository;
import sb.mdr.repository.MateriRepository;
import sb.mdr.repository.SemesterRepository;
import sb.mdr.repository.SoalRepository;

@Service
public class MateriService {

	@Autowired
	private MateriRepository materiRepository;

	private Materi materi;

	public String insertMateri(Materi localMateri) {
		int increment = 0;
		int flagInsert = 0;
		String result = null;
		String cekMateri = materiRepository.getLastKodeMateri();
		if (cekMateri == null) {
			increment += 1;
			localMateri.setKodeMateri("DSN" + increment);

		} else {
			increment = materiRepository.hitungMateri() + 1;
			localMateri.setKodeMateri("DSN" + increment);
		}
		try {
			flagInsert = materiRepository.insertDataMateri(localMateri.getKodeMateri(), localMateri.getJenisMateri(),
					localMateri.getIsiMateri());

			if (flagInsert == 1) {
				result = "data materi berhasil dimasukkan dengan kode materi: " + cekMateri;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public List<Materi> getAllMateri(int limit) {
		List<Materi> listMateri = new ArrayList<>();
		try {
			listMateri = materiRepository.selectAllMateri(limit);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listMateri;
	}

	public Materi getMateri(String kodeMateri) {
		Materi materi = new Materi();
		try {
			materi = materiRepository.getMateriByKodeMateri(kodeMateri);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return materi;
	}

	public String deleteMateri(String kodeMateri) {
		int deleteFlag = 0;
		String result = null;
		try {
			deleteFlag = materiRepository.deleteDataMateri(kodeMateri);
			if (deleteFlag == 1) {

				result = "data materi dengan kode materi: " + kodeMateri + " telah dihapus";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public String updateMateri(Materi materi, String kodeMateri) {
		int updateFlag = 0;
		String result = null;
		try {
			updateFlag = materiRepository.updateDataMateri(materi.getJenisMateri(), materi.getIsiMateri(), kodeMateri);
			if (updateFlag == 1) {
				result = "data materi telah diperbaharui dengan kode materi: " + kodeMateri;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public MateriRepository getMateriRepository() {
		return materiRepository;
	}

	public void setMateriRepository(MateriRepository materiRepository) {
		this.materiRepository = materiRepository;
	}

	public Materi getMateri() {
		return materi;
	}

	public void setMateri(Materi materi) {
		this.materi = materi;
	}

}
