package kr.co.kimpoziben.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DynamicUpdate
@Table(name = "ij_file")
@IdClass(FileId.class)
public class FileEntity implements Serializable  {
    private static final long serialVersionUID = 1L;

    @Id
    private Long idFile;

    @Id
    private int snFile;

    @Column(length = 300, nullable = false, name = "file_org_nm")
    private String orgNmFile;

    @Column(length = 300, nullable = false, name = "file_srv_nm")
    private String srvNmFile;

    @Column(length = 300, nullable = false, name = "file_path")
    private String pathFile;

    @Column(nullable = false, name = "file_size")
    private Long sizeFile;

    @Column(length = 20, nullable = false, name = "extends")
    private String extendsFile;

    @Column(length = 1, nullable = false, name = "del_yn")
    private String ynDel;

    @CreatedDate
    @Column(length = 20, nullable = false, name = "register")
    private String registerFile;

    @Column(nullable = false, name = "regdt")
    private LocalDateTime regdtFile;

    @Column(length = 20, nullable = false, name = "modifier")
    private String modifierFile;

    @LastModifiedDate
    @Column(nullable = false, name = "moddt")
    private LocalDateTime moddtFile;

    @Builder
    public FileEntity(Long idFile, int snFile, String orgNmFile, String srvNmFile, String pathFile,
                      Long sizeFile, String extendsFile, String ynDel, String registerFile, LocalDateTime regdtFile,
                      String modifierFile, LocalDateTime moddtFile) {
        this.idFile = idFile;
        this.snFile = snFile;
        this.orgNmFile = orgNmFile;
        this.srvNmFile = srvNmFile;
        this.pathFile = pathFile;
        this.sizeFile = sizeFile;
        this.extendsFile = extendsFile;
        this.ynDel = ynDel;
        this.registerFile = registerFile;
        this.regdtFile = regdtFile;
        this.modifierFile = modifierFile;
        this.moddtFile = moddtFile;

    }

}
