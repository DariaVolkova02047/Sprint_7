import com.google.gson.Gson;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isA;

public class courierTest {

    private Courier courier;
    private CourierClient courierClient;
    private int id;
    private static final String PATH = "api/v1/courier";
    @Before
    public void setUp() {
        courier = CourierGenerator.getDefault();
        courierClient = new CourierClient();
    }

    @After
    public void cleanUp() {
        courierClient.delete(id);
    }

    @Test
    public void checkCourierResponseBodyTest() {
        courier = CourierGenerator.getDefault();

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(PATH);

        response.statusCode(201).and().assertThat().body("ok", equalTo(true))


        CourierLogin courierLogin = new CourierLogin("sashasasha", "sasha");

        Response responseLogin = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierLogin)
                .when()
                .post("/api/v1/courier/login");

        responseLogin.statusCode(200).and().assertThat().body("id", isA(Integer.class))

        String IdString = responseLogin.body().asString();
        Gson gson = new Gson();
        CourierDelete id = gson.fromJson(IdString, CourierDelete.class);


        Response responseDelete = given()
                .header("Content-type", "application/json")
                .when()
                .delete(String.format("/api/v1/courier/%s", id.getId()));

        responseDelete.statusCode(200).and().assertThat().body("ok", equalTo(true))

    }

    @Test
    public void checkCourierDoubleResponseBodyTest() {
        Courier courier = new Courier("sashasasha", "sasha", "Sasha1");

        Response response1 = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(PATH);

        response1.statusCode(201).and().assertThat().body("ok", equalTo(true))

        Response response2 = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(PATH);

        response2.statusCode(409).and().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    public void checkCourierResponseWithoutFieldBodyTest() {
        CourierWithoutPassword courierWithoutPassword = new CourierWithoutPassword("sashasasha", "Sasha");

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierWithoutPassword)
                .when()
                .post(PATH);

        response.statusCode(400).and().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

}
