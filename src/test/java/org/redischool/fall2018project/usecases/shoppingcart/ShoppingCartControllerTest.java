package org.redischool.fall2018project.usecases.shoppingcart;

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

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ShoppingCartControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void testCreateEmptyShoppingCart() {
        String id = this.restTemplate.postForObject(getUrl(), null, ShoppingCartDto.class).getId();

        ShoppingCartDto shoppingCartDto = this.restTemplate.getForObject(getUrl() + "/" + id, ShoppingCartDto.class);

        assertThat(shoppingCartDto.getItems().size()).isEqualTo(0);
        System.out.println(shoppingCartDto);
    }

    @Test
    void testTotalOfEmptyShoppingCartIsZero() {
        String id = this.restTemplate.postForObject(getUrl(), null, ShoppingCartDto.class).getId();

        assertThat(this.restTemplate.getForObject(getUrl() + "/" + id + "/total", String.class)).contains("0.0");
    }

    @Test
    void testOneAProductToTheShoppingCart() {
        String id = this.restTemplate.postForObject(getUrl(), null, ShoppingCartDto.class).getId();

        assertThat(this.restTemplate.getForObject(getUrl() + "/" + id + "/product?name=Banana&price=10.0",
                ItemDto.class)).isEqualToComparingFieldByField(new ItemDto("Banana", 10.0, 0));
    }

    @Test
    void testAddTwoProductsToTheShoppingCart() {
        String id = this.restTemplate.postForObject(getUrl(), null, ShoppingCartDto.class).getId();

        ResponseEntity<String> forEntity = this.restTemplate.getForEntity(getUrl() + "/" + id + "/product?name=Banana&quantity=2&price=10.0", String.class);
        assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(forEntity.getBody());

        ItemDto object = this.restTemplate.getForObject(getUrl() + "/" + id + "/product?name=Banana&quantity=2&price=10.0", ItemDto.class);
        assertThat(object).isEqualToComparingFieldByField(new ItemDto("Banana", 10.0, 0));
    }

    @Test
    void testShouldToHasAEmptyShoppingCart() {
        String id = this.restTemplate.postForObject(getUrl(), null, ShoppingCartDto.class).getId();

        ResponseEntity<String> forEntity = this.restTemplate.getForEntity(getUrl() + "/" + id, String.class);

        assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(forEntity.getBody());

    }

    private String getUrl() {
        return "http://localhost:" + port + "/shoppingcart";
    }
}