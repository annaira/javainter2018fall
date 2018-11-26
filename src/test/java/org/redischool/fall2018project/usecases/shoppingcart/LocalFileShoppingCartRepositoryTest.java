package org.redischool.fall2018project.usecases.shoppingcart;

import static org.assertj.core.api.Java6Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;

class LocalFileShoppingCartRepositoryTest {

    @AfterEach
    void removeTestFiles() {
        new File("shoppingCart.json").delete();
    }

    @Test
    void testPersistShoppingCart() {

        ShoppingCart expected = new ShoppingCart();
        expected.add(new Product("Banana", 10.0), 2);

        LocalFileShoppingCartRepository subject1 = new LocalFileShoppingCartRepository();
        subject1.persistShoppingCart(expected);

        LocalFileShoppingCartRepository subject2 = new LocalFileShoppingCartRepository();
        ShoppingCart result = subject2.fetchShoppingCart();

        assertThat(result).isEqualTo(expected);
    }

}