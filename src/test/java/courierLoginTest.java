import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;


public class CourierLoginTest {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";
    private int id;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        Courier courier = CourierGenerator.getDefault();
        CourierClient CourierClient = new CourierClient();
    }

    @After
    public void cleanUp() {
        CourierClient.delete(id);
    }
    @Test
    @DisplayName("check Error Message For Incorrect Password")
    public void testErrorMessageForIncorrectPassword(){
        CourierClient courierClient = new CourierClient();
        Response incorrectPasswordResponse = CourierClient.getIncorrectPasswordResponse(new
                Courier(Courier.login,"123qweASD"));
        incorrectPasswordResponse.statusCode(404).and.assertThat().body("message", is("Неверный пароль"));
    }
    @Test
    @DisplayName("Check error message for incorrect login")
    public void testErrorMessageForIncorrectLogin(){
        CourierLogin courierLogin = new CourierLogin("sashasasha1", "sasha");
        Response incorrectLoginResponse = CourierClient.getIncorrectPasswordResponse(new
                Courier(Courier.login,"sasha"));
        incorrectLoginResponse.statusCode(404).and.assertThat().body("message", is("Учетная запись не найдена"));
    }
}
