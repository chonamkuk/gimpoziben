package kr.co.kimpoziben.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class FileId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "file_id")
    private Long idFile;

    @Column(name = "file_sn")
    private int snFile;
}
