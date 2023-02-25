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

    @Before("Get response for incorrect password")
    public Response getIncorrectPasswordResponse(Object body) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .post("/api/v1/loginCourier");
        .then();
    }

    @After("Get response for incorrect password")
    public Response getIncorrectPasswordResponse(Object body) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .post("/api/v1/login");
        .then();
    }

    @Test
    @DisplayName("check Courier Login Response Body Test")
    public void testErrorMessageForIncorrectPassword(){
        CourierLogin CourierLogin = new CourierLogin();
        Response incorrectPasswordResponse = CourierLogin.getIncorrectPasswordResponse(new
                AuthCourier(CourierLogin.login,"sasha WrongPassword Sasha"));
        incorrectPasswordResponse.statusCode(404).and.assertThat().body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("check Courier Login Bad Password Response Body Test")
    public void testErrorMessageForIncorrectPassword(){
        CourierLogin CourierLogin = new CourierLogin();
        Response incorrectPasswordResponse = CourierLogin.getIncorrectPasswordResponse(new
                AuthCourier(CourierLogin.login,"*** WrongPassword sasha"));
        incorrectPasswordResponse.statusCode(404).and.assertThat().body("message", is("Учетная запись не найдена"));
    }
}
