import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isA;

public class courierLoginTest {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        courier = CourierGenerator.getDefault();
        courierClient = new CourierClient();
    }

    @After
    public void cleanUp() {
        courierClient.delete(id);
    }

    @Test
    @DisplayName("check Error Message For Incorrect Password")
    public void testErrorMessageForIncorrectPassword(){
        CourierClient courierClient = new CourierClient();
        Response incorrectPasswordResponse = courierClient.getIncorrectPasswordResponse(new
                AuthCourier(currentCourier.login,"123qweASD"));
        incorrectPasswordResponse.statusCode(404).and.assertThat().body("message", is("Неверный пароль"));
    }

    @Test
    @DisplayName("Check error message for incorrect login")
    public void testErrorMessageForIncorrectLogin(){
        CourierLogin courierLogin = new CourierLogin("sashasasha1", "sasha");
        Response incorrectLoginResponse = courierClient.getIncorrectLoginResponse(new
                AuthCourier(CurrentCourier.login,"sasha"));
        incorrectPasswordResponse.statusCode(404).and.assertThat().body("message", is("Учетная запись не найдена"));
    }
}
