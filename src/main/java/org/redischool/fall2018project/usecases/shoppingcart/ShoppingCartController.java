package org.redischool.fall2018project.usecases.shoppingcart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public final class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @RequestMapping(path = "/shoppingcart", method = RequestMethod.POST)
    public ShoppingCartDto createShoppingCart() {
        return shoppingCartService.createShoppingCart().toShoppingCartDto();
    }

    @RequestMapping("/shoppingcart/{id}/total")
    public String total(@PathVariable("id") String id) {
        return String.valueOf(shoppingCartService.getCurrentShoppingCart(id).total());
    }

    @RequestMapping("/shoppingcart/{id}/product")
    public ItemDto addProductToCart(@PathVariable("id") String id,
                                    @RequestParam String name,
                                    @RequestParam double price,
                                    @RequestParam(value = "quantity", defaultValue = "1") int quantity) {
        Product product = new Product(name, price);

        shoppingCartService.addToCurrentShoppingCart(product, quantity, id);

        return product.toProductDto();
    }

    @RequestMapping("/shoppingcart/{id}")
    public ShoppingCartDto getShoppingCart(@PathVariable("id") String id) {
        return shoppingCartService.getCurrentShoppingCart(id).toShoppingCartDto();
    }

}
