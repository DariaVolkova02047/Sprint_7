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
    private Courier courier;
    private courierLogin courierLogin;


        @Before
        public void setUp() {
            Courier courier = new Courier();
            courierLogin = new courierLogin(); }

        @Step("Courier Create Login")
        public Response CourierCreateLogin(Object body) {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        Courier courier = new Courier("nikitanikita", "nikita", "Nikita");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");

        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }
            @After
            public void tearDown() {
                courierLogin.delete(courier);
            }

            @Step("courierAccount")
            public Response courierAccount(Object body) {
                return given()
        Response responseLogin = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierLogin)
                .when()
                .post("/api/v1/courier/login");

        responseLogin.then().assertThat().body("id", isA(Integer.class))
                .and()
                .statusCode(200); }

        String IdString = responseLogin.body().asString();
        Gson gson = new Gson();
        CourierDelete id = gson.fromJson(IdString, CourierDelete.class);

                @Step("courierDelete")
                public Response courierDelete(Object body) {
        Response responseDelete = given()
                .header("Content-type", "application/json")
                .when()
                .delete(String.format("/api/v1/courier/%s", id.getId()));

        responseDelete.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(200);

    }
                @Test
                @DisplayName("Check error message for incorrect login")
                public void testErrorMessageForIncorrectLogin(){
                    CourierClient courierClient = new CourierClient();
                    Response incorrectPasswordRsponse = courierClient.getIncorrectPasswordRsponse(new
                            AuthCourier(currentCourier.login,"nikitanikita"));
                    incorrectPasswordRsponse.statusCode(404).and.assertThat().body("message", is("Учетная запись не найдена"));
                }


}
