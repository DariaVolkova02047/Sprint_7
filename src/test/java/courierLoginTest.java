import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CourierTest {

    private Courier randomCourier;
    private CourierClient courierClient;
    private CourierAssertions check;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        check = new CourierAssertions();
        randomCourier = CourierGenerator.getRandom();
    }

    @After
    public void cleanUp() {
        if (courierId > 0) {
            courierClient.delete(courierId);
        }
    }

    //Courier tests
    @Test
    public void courierCanBeCreated() {
        ValidatableResponse createResponse = courierClient.create(randomCourier);
        check.createdSuccessfully(createResponse);

        courierId = courierClient.login(CourierCredentials.from(randomCourier)).extract().path("id");
    }

    @Test
    public void identicalCourierCanNotBeCreated() {
        courierClient.create(randomCourier);
        ValidatableResponse createResponse = courierClient.create(randomCourier);
        check.creationConflicted(createResponse);

        courierId = courierClient.login(CourierCredentials.from(randomCourier)).extract().path("id");
    }

    @Test
    public void courierWithoutLoginCanNotBeCreated() {
        randomCourier.setLogin(null);
        ValidatableResponse createResponse = courierClient.create(randomCourier);
        check.creationFailed(createResponse);
    }

    @Test
    public void courierWithoutPasswordCanNotBeCreated() {
        randomCourier.setPassword(null);
        ValidatableResponse createResponse = courierClient.create(randomCourier);
        check.creationFailed(createResponse);
    }

    @Test
    public void courierWithoutLastNameCanNotBeCreated() {
        randomCourier.setLastName(null);
        ValidatableResponse createResponse = courierClient.create(randomCourier);
        check.creationFailed(createResponse);
    }

    @Test
    public void courierWithBusyLoginCanNotBeCreated() {
        randomCourier.setLogin("IdenticalLogin");
        courierClient.create(randomCourier);
        Courier secondCourier = CourierGenerator.getRandom();
        secondCourier.setLogin("IdenticalLogin");
        ValidatableResponse createResponse = courierClient.create(secondCourier);
        check.creationConflicted(createResponse);

        courierId = courierClient.login(CourierCredentials.from(randomCourier)).extract().path("id");
    }

    //Login tests
    @Test
    public void courierCanLoggedIn() {
        courierClient.create(randomCourier);
        CourierCredentials credentials = CourierCredentials.from(randomCourier);
        ValidatableResponse loginResponse = courierClient.login(credentials);
        check.loggedInSuccessfully(loginResponse);
        courierId = loginResponse.extract().path("id");
    }

    @Test
    public void loginWithoutLoginFails() {
        courierClient.create(randomCourier);
        courierId = courierClient.login(CourierCredentials.from(randomCourier)).extract().path("id");
        CourierCredentials credentials = CourierCredentials.from(randomCourier);
        credentials.setLogin(null);
        ValidatableResponse loginResponse = courierClient.login(credentials);
        check.loggedInWithoutRequiredFieldFailed(loginResponse);
    }

    @Test
    public void loginWithoutPasswordFails() {
        courierClient.create(randomCourier);
        courierId = courierClient.login(CourierCredentials.from(randomCourier)).extract().path("id");
        CourierCredentials credentials = CourierCredentials.from(randomCourier);
        credentials.setPassword(null);
        ValidatableResponse loginResponse = courierClient.login(credentials);
        check.loggedInWithoutRequiredFieldFailed(loginResponse);
    }

    @Test
    public void loginWithInvalidLoginFails() {
        courierClient.create(randomCourier);
        courierId = courierClient.login(CourierCredentials.from(randomCourier)).extract().path("id");
        CourierCredentials credentials = CourierCredentials.from(randomCourier);
        credentials.setLogin("InvalidLogin");
        ValidatableResponse loginResponse = courierClient.login(credentials);
        check.loggedInWithInvalidFieldFailed(loginResponse);
    }

    @Test
    public void loginWithInvalidPasswordFails() {
        courierClient.create(randomCourier);
        courierId = courierClient.login(CourierCredentials.from(randomCourier)).extract().path("id");
        CourierCredentials credentials = CourierCredentials.from(randomCourier);
        credentials.setPassword("InvalidPassword");
        ValidatableResponse loginResponse = courierClient.login(credentials);
        check.loggedInWithInvalidFieldFailed(loginResponse);
    }

    @Test
    public void loginNonexistentCourierFails() {
        CourierCredentials credentials = CourierCredentials.from(randomCourier);
        ValidatableResponse loginResponse = courierClient.login(credentials);
        check.loggedInWithInvalidFieldFailed(loginResponse);
    }
}
