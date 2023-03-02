import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client {

    private static final String PATH = "api/v1/courier";

    public static void getIncorrectPasswordResponse(Courier sasha) {

    return ;}

    @Step("Courier create")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(PATH)
                .then();

    }

    @Step("Courier delete")
    public static ValidatableResponse delete(int courierId) {
        return given()
                .spec(getSpec())
                .when()
                .delete(PATH + courierId)
                .then();

    }

}
