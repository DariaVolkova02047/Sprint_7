import io.qameta.allure.Issue;
import org.apache.http.HttpStatus;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client {

    private static final String PATH = "api/v1/courier";

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
    public ValidatableResponse delete(int courierId) {
        return given()
                .spec(getSpec())
                .when()
                .delete(PATH + courierId)
                .then();

    }
}
