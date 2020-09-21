package com.github.black.hole.sboot.design.structure.facade;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class FinalPrice {

    ProductPrice productPrice;
    Postage postage;
    Discount discount;

    public FinalPrice() {
        productPrice = new ProductPrice();
        postage = new Postage();
        discount = new Discount();
    }

    int getFinalPrice(String product, String addr, String discountCode) {
        return productPrice.getPrice(product)
                + postage.getPostage(addr)
                - discount.getDiscount(discountCode);
    }
}
