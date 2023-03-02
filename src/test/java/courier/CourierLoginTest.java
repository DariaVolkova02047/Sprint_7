package courier;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.courier.*;

import static org.hamcrest.CoreMatchers.equalTo;

public class CourierLoginTest {

    private CourierGenerator courierGenerator = new CourierGenerator();
    private Data data;
    private CourierClient courierClient;
    private Courier courier;
    CreateAndLoginCourier createAndLoginCourier;
    int idCourier;

    @Before
    @Step("Precondition for login tests with creation courier")
    public void setUp() {
        courierClient = new CourierClient();
        courier = courierGenerator.getCourierRandom();
        courierClient.createCourier(courier);
        data = Data.from(courier);
        createAndLoginCourier = new CreateAndLoginCourier();
    }

    @Test
    @DisplayName("Login courier with valid data")
    public void courierCanSuccessfullyLogin() {
        ValidatableResponse responseLoginCourier = courierClient.loginCourier(data);
        createAndLoginCourier.loginCourierSuccessfully(responseLoginCourier);
        idCourier = responseLoginCourier.extract().path("id");
    }

    @Test
    @DisplayName("Login courier without login field")
    public void courierLoginUnsuccessfullyWithoutLogin() {
        Data dataWithoutLogin = new Data("", courier.getPassword()); // c null тесты виснут
        ValidatableResponse responseLoginErrorMessage = courierClient.loginCourier(dataWithoutLogin).statusCode(400);
        responseLoginErrorMessage.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Login courier without password field")
    public void courierLoginUnsuccessfullyWithoutPassword() {
        Data dataWithoutLogin = new Data(courier.getLogin(), "");
        ValidatableResponse responsePasswordErrorMessage = courierClient.loginCourier(dataWithoutLogin).statusCode(400);
        responsePasswordErrorMessage.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Login courier with not existing login field")
    public void testErrorMessageForIncorrectLogin() {
        Data dataWithNotExistingLogin = new Data(RandomStringUtils.randomAlphanumeric(6), courier.getPassword());
        ValidatableResponse responseWithWithNotExistingLoginMessage = courierClient.loginCourier(dataWithNotExistingLogin).statusCode(404);
        responseWithWithNotExistingLoginMessage.assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    @Step("Delete test courier")
    public void deleteCourier() {
        if (idCourier != 0) {
            courierClient.deleteCourier(idCourier);
        }
    }
}
