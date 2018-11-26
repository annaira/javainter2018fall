package org.redischool.fall2018project.usecases.shoppingcart;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class LocalFileShoppingCartRepository implements ShoppingCartRepository {

    private final String BASE_CART = "shoppingCart";
    private final String REPOSITORY_FOLDER = "./carts/";

    @Override
    public void persistShoppingCart(ShoppingCart shoppingCart) {
        writeShoppingCartToFile(shoppingCart);
    }

    @Override
    public ShoppingCart fetchShoppingCart() {
        return readShoppingCartFromFile();
    }

    @Override
    public ShoppingCart fetchShoppingCart(String user) {
        return readShoppingCartFromFile(user);
    }

    @Override
    public void persistShoppingCart(String user, ShoppingCart shoppingCart) {
        writeShoppingCartToFile(user, shoppingCart);
    }

    private ShoppingCart readShoppingCartFromFile(String user) {
        try {
            ShoppingCartDto shoppingCartDto = new ObjectMapper().readValue(new File(REPOSITORY_FOLDER + user + ".json"), ShoppingCartDto.class);
            return shoppingCartDto.toShoppingCart();
        } catch (IOException e) {
            return new ShoppingCart();
        }
    }

    private ShoppingCart readShoppingCartFromFile() {
        return readShoppingCartFromFile(BASE_CART);
    }

    private void writeShoppingCartToFile(String user, ShoppingCart shoppingCart) {
        try {
            ShoppingCartDto shoppingCartDto = ShoppingCartDto.of(shoppingCart);
            new ObjectMapper().writeValue(new File(REPOSITORY_FOLDER + user + ".json"), shoppingCartDto);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void writeShoppingCartToFile(ShoppingCart shoppingCart) {
        writeShoppingCartToFile(BASE_CART, shoppingCart);
    }
}
