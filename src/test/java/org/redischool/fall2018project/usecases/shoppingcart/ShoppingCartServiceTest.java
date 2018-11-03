package org.redischool.fall2018project.usecases.shoppingcart;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShoppingCartServiceTest {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ShoppingCartService subject;

    @Test
    void cartShouldBeInitiallyEmpty() {
        ShoppingCart shoppingCart = shoppingCartRepository.insert(new ShoppingCart());

        ShoppingCart result = subject.getCurrentShoppingCart(shoppingCart.getId());

        assertEquals(List.of(), result.items());
    }

    @Test
    void cartShouldIncludeOneAddedItem() {
        ShoppingCart shoppingCart = shoppingCartRepository.insert(new ShoppingCart());
        Product product = new Product("Product", 10.0);
        int quantity = 3;

        subject.addToCurrentShoppingCart(product, quantity, shoppingCart.getId());
        ShoppingCart result = subject.getCurrentShoppingCart(shoppingCart.getId());

        assertEquals(List.of(new ShoppingCart.Item(product, quantity)), result.items());
    }

    @Test
    void cartShouldConsolidateItemsWithTheSameProduct() {
        ShoppingCart shoppingCart = shoppingCartRepository.insert(new ShoppingCart());
        Product product = new Product("Product", 10.0);

        subject.addToCurrentShoppingCart(product, 1, shoppingCart.getId());
        subject.addToCurrentShoppingCart(product, 2, shoppingCart.getId());
        ShoppingCart result = subject.getCurrentShoppingCart(shoppingCart.getId());

        assertEquals(List.of(new ShoppingCart.Item(product, 3)), result.items());
    }

    @Test
    void cartShouldAllowAddingTwoDifferentProducts() {
        ShoppingCart shoppingCart = shoppingCartRepository.insert(new ShoppingCart());
        Product product1 = new Product("Apple", 10.0);
        Product product2 = new Product("Orange", 10.0);

        subject.addToCurrentShoppingCart(product1, 1, shoppingCart.getId());
        subject.addToCurrentShoppingCart(product2, 1, shoppingCart.getId());
        ShoppingCart result = subject.getCurrentShoppingCart(shoppingCart.getId());

        assertEquals(List.of(new ShoppingCart.Item(product1, 1), new ShoppingCart.Item(product2, 1)), result.items());
    }

    @Test
    void serviceShouldRetrieveShoppingCartFromRepository() {
        ShoppingCart shoppingCart = shoppingCartRepository.insert(new ShoppingCart());
        Product product = new Product("Product", 10.0);
        shoppingCartRepository.save(shoppingCart.add(product, 1));

        ShoppingCart result = subject.getCurrentShoppingCart(shoppingCart.getId());

        assertEquals(List.of(new ShoppingCart.Item(product, 1)), result.items());
    }

    @Test
    void serviceShouldComputeTotalOfEmptyCartAsZero() {
        ShoppingCart shoppingCart = shoppingCartRepository.insert(new ShoppingCart());
        ShoppingCart result = subject.getCurrentShoppingCart(shoppingCart.getId());
        assertEquals(0.0, result.total());
    }

    @Test
    void serviceShouldComputeTotalOfCartWithOneItem() {
        ShoppingCart shoppingCart = shoppingCartRepository.insert(new ShoppingCart());
        Product product = new Product("Product", 10.0);

        subject.addToCurrentShoppingCart(product, 1, shoppingCart.getId());
        ShoppingCart result = subject.getCurrentShoppingCart(shoppingCart.getId());

        assertEquals(10.0, result.total());
    }

    @Test
    void serviceShouldComputeTotalOfCartWith2Items() {
        ShoppingCart shoppingCart = shoppingCartRepository.insert(new ShoppingCart());
        Product product1 = new Product("Apple", 15.7);
        Product product2 = new Product("banana", 10.00);

        subject.addToCurrentShoppingCart(product1, 1, shoppingCart.getId());
        subject.addToCurrentShoppingCart(product2, 1, shoppingCart.getId());
        ShoppingCart result = subject.getCurrentShoppingCart(shoppingCart.getId());

        assertEquals(25.7, result.total());
    }

}
