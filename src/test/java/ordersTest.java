import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.isA;

@RunWith(Parameterized.class)

public class ordersTest {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final int metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final List<String> color;


    public ordersTest(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters(name = "ТестЦветДата: {8}")
    public static Object[][] params() {
        return new Object[][]{
                {"Object1", "Object2", "Object3, 50 apt.", 4, "+7 801 900 76 80", 5, "2023-01-06", "Hello everyone!", List.of("BLACK")},
                {"Object1", "Object2", "Object3, 50 apt.", 4, "+7 801 900 76 80", 5, "2023-01-06", "Hello everyone!", List.of("GREY")},
                {"Object1", "Object2", "Object3, 50 apt.", 4, "+7 801 900 76 80", 5, "2023-01-06", "Hello everyone!", List.of("BLACK", "GREY")},
                {"Object1", "Object2", "Object3, 50 apt.", 4, "+7 801 900 76 80", 5, "2023-01-06", "Hello everyone!", List.of()},
        };
    }


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void checkOrdersResponseBodyTest() {
        Orders orders = new Orders(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment);

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(orders)
                .when()
                .post("/api/v1/orders");

        response.then().assertThat().body("track", isA(Integer.class))
                .and()
                .statusCode(201);

        System.out.println(response.body().asString());

    }
}
