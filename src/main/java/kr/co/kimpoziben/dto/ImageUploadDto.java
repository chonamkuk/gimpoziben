package kr.co.kimpoziben.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ImageUploadDto {
    private String image;
    private String imageName;
    private Long imageSize;
}
