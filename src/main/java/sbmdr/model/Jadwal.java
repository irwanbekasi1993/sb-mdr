package sbmdr.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name="jadwal")
public class Jadwal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="kodeDosen")
    private Dosen dosen;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="nim")
    private Mahasiswa mahasiswa;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="kodeJurusan")
    private Jurusan jurusan;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="kodeKelas")
    private Kelas kelas;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="kodeMatkul")
    private MataKuliah mataKuliah;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="kodeSemester")
    private Semester semester;

    public Jadwal() {
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Jadwal(Long id, Dosen dosen, Jurusan jurusan, Kelas kelas, MataKuliah mataKuliah, Semester semester,Mahasiswa mahasiswa) {
        this.id = id;
        this.dosen = dosen;
        this.jurusan = jurusan;
        this.kelas = kelas;
        this.mataKuliah = mataKuliah;
        this.semester = semester;
        this.mahasiswa=mahasiswa;
    }

    

    public Dosen getDosen() {
        return dosen;
    }

    public void setDosen(Dosen dosen) {
        this.dosen = dosen;
    }

    public Jurusan getJurusan() {
        return jurusan;
    }

    public void setJurusan(Jurusan jurusan) {
        this.jurusan = jurusan;
    }

    public Kelas getKelas() {
        return kelas;
    }

    public void setKelas(Kelas kelas) {
        this.kelas = kelas;
    }

    public MataKuliah getMataKuliah() {
        return mataKuliah;
    }

    public void setMataKuliah(MataKuliah mataKuliah) {
        this.mataKuliah = mataKuliah;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Mahasiswa getMahasiswa() {
        return mahasiswa;
    }

    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
    }
    
    
        

    
}
