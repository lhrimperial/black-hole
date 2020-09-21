package com.github.black.hole.sboot.design.structure.facade;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public enum ProductSalesman {
    /** 产品 */
    instance;
    Stock stock = new Stock();
    FinalPrice finalPrice = new FinalPrice();

    Object buySomething(String product, String addr, String discountCode) {
        if (!stock.hasStock(product)) {
            return "库存不足";
        }
        int price = finalPrice.getFinalPrice(product, addr, discountCode);
        return "订单信息:" + product + "-" + addr + "-" + discountCode + "-" + price;
    }
}
