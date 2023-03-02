package order;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.order.OrderClient;

import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderListTest {

    @Test
    @DisplayName("Get order list")
    public void getOrderListIsOk() {
        OrderClient orderClient = new OrderClient();
        ValidatableResponse responseOrderList = orderClient.getOrderList();
        responseOrderList.assertThat()
                .statusCode(200)
                .body("orders", notNullValue());
    }

}