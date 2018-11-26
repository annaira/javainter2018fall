package org.redischool.fall2018project.usecases.shoppingcart;

public class FakeShoppingCartRepository implements ShoppingCartRepository {
    private ShoppingCart shoppingCart = new ShoppingCart();

    @Override
    public void persistShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public ShoppingCart fetchShoppingCart() {
        return shoppingCart;
    }

    @Override
    public ShoppingCart fetchShoppingCart(String user) {
        return null;
    }
}
