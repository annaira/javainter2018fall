package org.redischool.fall2018project.usecases.shoppingcart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCart getCurrentShoppingCart(String id) {
        return shoppingCartRepository.findById(id).get();
    }

    public void addToCurrentShoppingCart(Product product, int quantity, String id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id).get();
        shoppingCart.add(product, quantity);
        shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart createShoppingCart() {
        return shoppingCartRepository.save(new ShoppingCart());
    }
}
