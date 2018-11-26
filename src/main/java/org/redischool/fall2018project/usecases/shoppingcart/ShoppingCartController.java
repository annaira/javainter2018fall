package org.redischool.fall2018project.usecases.shoppingcart;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public final class ShoppingCartController {

    private final ShoppingCartRepository shoppingCartRepository = new LocalFileShoppingCartRepository();
    private final ShoppingCartService shoppingCartService = new ShoppingCartService(shoppingCartRepository);

    @RequestMapping(path = "/shoppingcart/total", method = RequestMethod.GET)
    public String getCartTotal(@RequestHeader HttpHeaders headers) {
        List<String> userHeaders = headers.get("User");
        if (userHeaders != null && userHeaders.size() == 1) {
            return String.valueOf(shoppingCartService.getShoppingCart(userHeaders.get(0)).getTotal());
        }
        return String.valueOf(shoppingCartService.getCurrentShoppingCart().total());
    }

    @RequestMapping(path = "/shoppingcart/product", method = RequestMethod.POST)
    public ShoppingCartDto addProductToCart(@RequestHeader HttpHeaders headers, @RequestBody ItemDto itemDto) {
        List<String> userHeaders = headers.get("User");
        Product product = new Product(itemDto.getName(), itemDto.getPrice());

        if (userHeaders != null && userHeaders.size() == 1) {
            String user = userHeaders.get(0);
            shoppingCartService.addToCurrentShoppingCart(user, product, itemDto.getQuantity());
            return ShoppingCartDto.of(shoppingCartService.getShoppingCart(user));
        }

        shoppingCartService.addToCurrentShoppingCart(product, itemDto.getQuantity());
        return ShoppingCartDto.of(shoppingCartService.getCurrentShoppingCart());
    }

    @RequestMapping(path = "/shoppingcart/product", method = RequestMethod.PUT)
    public void modifyProductQuantity(@RequestHeader HttpHeaders headers, @RequestBody ItemDto itemDto) {
        List<String> userHeaders = headers.get("User");
        Product product = new Product(itemDto.getName(), itemDto.getPrice());

        if (userHeaders != null && userHeaders.size() == 1) {
            String user = userHeaders.get(0);
            shoppingCartService.setQuantityOfProductInCurrentShoppingCart(user, product, itemDto.getQuantity());
        }

        shoppingCartService.setQuantityOfProductInCurrentShoppingCart(product, itemDto.getQuantity());
    }

    @RequestMapping(path = "/shoppingcart/", method = RequestMethod.GET)
    public ShoppingCartDto getShoppingCart(@RequestHeader HttpHeaders headers) {
        List<String> userHeaders = headers.get("User");
        if (userHeaders != null && userHeaders.size() == 1) {
            String user = userHeaders.get(0);
            return ShoppingCartDto.of(shoppingCartService.getShoppingCart(user));
        }

        throw new IllegalArgumentException();
        //return ShoppingCartDto.of(shoppingCartService.getCurrentShoppingCart());
    }


    // Delete /shoppingcart/
    // headers List ["User": "Edward"]

    @RequestMapping(path = "/shoppingcart/", method = RequestMethod.DELETE)
    public void deleteShoppingCart(@RequestHeader HttpHeaders headers) {
        List<String> userHeader = headers.get("User"); //returns ["Edward"] or null
        if(userHeader != null && userHeader.size() ==1) {
            String user = userHeader.get(0);
            shoppingCartService.clear(user);

        } else {

            shoppingCartService.clear();
        }

    }

}
