import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.CoreMatchers.is;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;


public class CourierLoginTest {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";
    private int id;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        Courier courier = CourierGenerator.getDefault();
        CourierClient courierClient = new CourierClient();
    }

    @After
    public void cleanUp() {
        CourierClient.delete(id);
    }
    @Test
    @DisplayName("Check error message for incorrect password")
    public void testErrorMessageForIncorrectPassword(){
        CourierClient CourierClient = new CourierClient();
        Response incorrectPasswordResponse = CourierClient.password(new
                Courier(Courier.password,"123qweASD"));
        incorrectPasswordResponse.then().assertThat().statusCode(404).body("message", is("Неверный пароль"));
    }
    @Test
    @DisplayName("Check error message for incorrect login")
    public void testErrorMessageForIncorrectLogin(){
        CourierLogin courierLogin = new CourierLogin("sashasasha1", "sasha");
        Response incorrectLoginResponse = CourierClient.create(new
                Courier(Courier.login,"sasha"));
        incorrectLoginResponse.then().assertThat().statusCode(404).body("message", is("Учетная запись не найдена"));
    }
}
