package org.redischool.fall2018project.usecases.shoppingcart;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ShoppingCartControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void clearShoppingCart() {
        this.restTemplate.delete("http://localhost:" + port + "shoppingcart/");
    }

    @Test
    void testTotalOfEmptyShoppingCartIsZero() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/shoppingcart/total",
                String.class)).contains("0.0");
    }

    @Test
    void testOneAProductToTheShoppingCart() {
        ShoppingCartDto expectedShoppingCart = new ShoppingCartDto();
        List<ItemDto> expectedItems = new ArrayList<>();
        ItemDto banana = new ItemDto("Banana", 10.0, 1);
        expectedItems.add(banana);
        expectedShoppingCart.setItems(expectedItems);

        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/shoppingcart/product",
                banana, ShoppingCartDto.class))
                .isEqualToComparingFieldByField(expectedShoppingCart);
    }

    @Test
    void testSetProductQuantity() {
        ItemDto wrongNumberOfBananas = new ItemDto("Banana", 10.0, 10);
        this.restTemplate.postForEntity("http://localhost:" + port +
                "shoppingcart/product", wrongNumberOfBananas, ShoppingCartDto.class);

        ItemDto orange = new ItemDto("Orange", 10.0, 1);
        this.restTemplate.postForEntity("http://localhost:" + port +
                "shoppingcart/product", orange, ShoppingCartDto.class);

        ShoppingCartDto expectedShoppingCart = new ShoppingCartDto();
        List<ItemDto> expectedItems = new ArrayList<>();
        ItemDto banana = new ItemDto("Banana", 10.0, 2);
        expectedItems.add(banana);
        expectedItems.add(orange);
        expectedShoppingCart.setItems(expectedItems);

        this.restTemplate.put("http://localhost:" + port + "shoppingcart/product", banana);

        ResponseEntity<ShoppingCartDto> response = this.restTemplate.getForEntity("http://localhost:" + port +
                "shoppingcart/", ShoppingCartDto.class);

        assertThat(response.getBody().getItems()).containsExactlyInAnyOrderElementsOf(expectedShoppingCart.getItems());
    }

    @Test
    void testAddTwoProductsOfTheSameTypeToTheShoppingCart() {
        ShoppingCartDto expectedShoppingCart = new ShoppingCartDto();
        List<ItemDto> expectedItems = new ArrayList<>();
        ItemDto banana = new ItemDto("Banana", 10.0, 1);
        ItemDto banana1 = new ItemDto("Banana", 10.0, 1);
        expectedItems.add(new ItemDto("Banana", 10.0, 2));
        expectedShoppingCart.setItems(expectedItems);

        this.restTemplate.postForEntity("http://localhost:" + port +
                "shoppingcart/product", banana, ShoppingCartDto.class);
        ResponseEntity<ShoppingCartDto> response = this.restTemplate.postForEntity("http://localhost:" + port +
                "shoppingcart/product", banana1, ShoppingCartDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(response.getBody()).isEqualToComparingFieldByField(expectedShoppingCart);
    }

    @Test
    void testAddTwoProductsToTheShoppingCart() {
        ShoppingCartDto expectedShoppingCart = new ShoppingCartDto();
        List<ItemDto> expectedItems = new ArrayList<>();
        ItemDto banana = new ItemDto("Banana", 10.0, 1);
        ItemDto orange = new ItemDto("Orange", 5.0, 2);
        expectedItems.add(banana);
        expectedItems.add(orange);
        expectedShoppingCart.setItems(expectedItems);

        this.restTemplate.postForEntity("http://localhost:" + port +
                "shoppingcart/product", banana, ShoppingCartDto.class);
        ResponseEntity<ShoppingCartDto> response = this.restTemplate.postForEntity("http://localhost:" + port +
                "shoppingcart/product", orange, ShoppingCartDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(response.getBody()).isEqualToComparingFieldByField(expectedShoppingCart);
    }

    @Test
    void testShouldToHasAEmptyShoppingCart() {
        ResponseEntity<String> entity = this.restTemplate.getForEntity("http://localhost:" + port +
                "shoppingcart/", String.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isEqualTo("{\"items\":[]}");
    }

    @Test
    void testShoppingCartTotalShouldBeSumOfMultipleItesm() {
        ShoppingCartDto expectedShoppingCart = new ShoppingCartDto();
        ItemDto banana = new ItemDto("Banana", 10.0, 1);
        ItemDto banana1 = new ItemDto("Banana", 10.0, 1);

        this.restTemplate.postForEntity("http://localhost:" + port +
                "shoppingcart/product", banana, ShoppingCartDto.class);
        ResponseEntity<ShoppingCartDto> response = this.restTemplate.postForEntity("http://localhost:" + port +
                "shoppingcart/product", banana1, ShoppingCartDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/shoppingcart/total",
                String.class)).contains("20.0");
    }

}