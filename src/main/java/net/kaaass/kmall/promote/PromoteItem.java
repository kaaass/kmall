package net.kaaass.kmall.promote;

import lombok.Data;
import net.kaaass.kmall.dto.ProductDto;

@Data
public class PromoteItem {

    private ProductDto product;

    private float price;

    private int count;
}
