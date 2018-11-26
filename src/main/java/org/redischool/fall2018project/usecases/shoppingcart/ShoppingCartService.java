package org.redischool.fall2018project.usecases.shoppingcart;

public final class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public ShoppingCart getShoppingCart(String user) {
        return shoppingCartRepository.fetchShoppingCart(user);
    }

    public ShoppingCart getCurrentShoppingCart() {
        return shoppingCartRepository.fetchShoppingCart();
    }

    public void addToCurrentShoppingCart(String user, Product product, int quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.fetchShoppingCart(user);
        shoppingCart.add(product, quantity);
        shoppingCartRepository.persistShoppingCart(user, shoppingCart);
    }

    public void addToCurrentShoppingCart(Product product, int quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.fetchShoppingCart();
        shoppingCart.add(product, quantity);
        shoppingCartRepository.persistShoppingCart(shoppingCart);
    }

    public void setQuantityOfProductInCurrentShoppingCart(Product product, int quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.fetchShoppingCart();
        shoppingCart.deleteItem(product);
        shoppingCart.add(product, quantity);
        shoppingCartRepository.persistShoppingCart(shoppingCart);
    }

    public void clear() {
        ShoppingCart shoppingCart = shoppingCartRepository.fetchShoppingCart();
        shoppingCart.clear();
        shoppingCartRepository.persistShoppingCart(shoppingCart);
    }

    public void setQuantityOfProductInCurrentShoppingCart(String user, Product product, int quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.fetchShoppingCart(user);
        shoppingCart.deleteItem(product);
        shoppingCart.add(product, quantity);
        shoppingCartRepository.persistShoppingCart(user, shoppingCart);
    }

    public void clear(String user) {
        ShoppingCart shoppingCart = shoppingCartRepository.fetchShoppingCart(user);
        shoppingCart.clear();
        shoppingCartRepository.persistShoppingCart(user, shoppingCart);
    }
}
