package kr.co.kimpoziben.dto;

import kr.co.kimpoziben.domain.entity.FileEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FileDto {
    private Long idFile;
    private int snFile;
    private String orgNmFile;
    private String srvNmFile;
    private String pathFile;
    private Long sizeFile;
    private String extendsFile;
    private String ynDel;
    private String registerFile;
    private LocalDateTime regdtFile;
    private String modifierFile;
    private LocalDateTime moddtFile;

    public FileEntity toEntity() {
        FileEntity fileEntity = FileEntity.builder()
                .idFile(idFile)
                .snFile(snFile)
                .orgNmFile(orgNmFile)
                .srvNmFile(srvNmFile)
                .pathFile(pathFile)
                .sizeFile(sizeFile)
                .extendsFile(extendsFile)
                .ynDel(ynDel)
                .registerFile(registerFile)
                .regdtFile(regdtFile)
                .modifierFile(modifierFile)
                .moddtFile(moddtFile)
                .build();
        return fileEntity;
    }

    @Builder
    public FileDto(Long idFile, int snFile, String orgNmFile, String srvNmFile
            , String pathFile, Long sizeFile, String extendsFile
            , String ynDel, String registerFile, LocalDateTime regdtFile
            , String modifierFile, LocalDateTime moddtFile) {

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
