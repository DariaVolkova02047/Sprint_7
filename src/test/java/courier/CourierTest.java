package courier;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.courier.*;

public class CourierTest {
    private CourierGenerator courierGenerator = new CourierGenerator();
    private CourierClient courierClient;
    private Courier courier;
    private CreateAndLoginCourier createAndLoginCourier;;
    int idCourier;

    @Before
    @Step("Precondition step for creating courier tests")
    public void setUp() {
        courierClient = new CourierClient();
        courier = courierGenerator.getCourierRandom();
        createAndLoginCourier = new CreateAndLoginCourier();
    }

    @Test
    @DisplayName("Create new courier")
    public void courierCanBeCreatedWith201CodeMessageOk() {
        ValidatableResponse responseCreateCourier = courierClient.createCourier(courier);
        createAndLoginCourier.createCourierSuccessfully(responseCreateCourier);
        Data data = Data.from(courier);
        ValidatableResponse responseLoginCourier = courierClient.loginCourier(data);
        idCourier = responseLoginCourier.extract().path("id");
    }

    @Test
    @DisplayName("Create courier without login field")
    public void courierCanNotBeCreatedWithoutLoginField() {
        courier.setLogin(null);
        ValidatableResponse responseNullLogin = courierClient.createCourier(courier);
        createAndLoginCourier.createCourierFailedField(responseNullLogin);
    }

    @Test
    @DisplayName("Create courier without password field")
    public void courierCanNotBeCreatedWithoutPasswordField() {
        courier.setPassword(null);
        ValidatableResponse responseNullPassword = courierClient.createCourier(courier);
        createAndLoginCourier.createCourierFailedField(responseNullPassword);
    }

    @Test
    @DisplayName("Create courier without login and password fields")
    public void courierCanNotBeCreatedWithoutLoginAndPasswordFields() {
        courier.setLogin(null);
        courier.setPassword(null);
        ValidatableResponse responseNullFields = courierClient.createCourier(courier);
        createAndLoginCourier.createCourierFailedField(responseNullFields);
    }

    @Test
    @DisplayName("Create courier with existing data")
    public void courierCanNotBeCreatedWithTheSameData() {
        courierClient.createCourier(courier);
        ValidatableResponse responseCreateCourier = courierClient.createCourier(courier);
        createAndLoginCourier.createCourierWithTheSameData(responseCreateCourier);
    }

    @After
    @Step("Delete test courier")
    public void deleteCourier() {
        if (idCourier != 0) {
            courierClient.deleteCourier(idCourier);
        }
    }
}

