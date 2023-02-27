
import io.restassured.responce.Responce;

import static io.restassured.RestAssured.given;

public class CourierClient  extends Client {

    private static final String PATH = "api/v1/courier";

    @Step("Courier create")
    public Responce create(Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(PATH)
                .then();

    }

    @Step("Courier delete")
    public Responce delete(int courierId) {
        return given()
                .spec(getSpec())
                .when()
                .delete(PATH + courierId)
                .then();

    }
}
