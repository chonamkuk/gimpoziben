package kr.co.kimpoziben.domain.entity;

import java.io.Serializable;
import java.util.Objects;

public class ProdSizeId implements Serializable {
    private Long seqProduct;
    private Long seqSize;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdSizeId that = (ProdSizeId) o;
        return Objects.equals(seqProduct, that.seqProduct) &&
                Objects.equals(seqSize, that.seqSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seqProduct, seqSize);
    }
}
