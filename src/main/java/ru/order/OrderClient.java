package ru.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.Client;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    public static final String ORDER_PATH = "/api/v1/orders"; // создание заказа
    public static final String CANCEL_ORDER_PATH = "/api/v1/orders/cancel"; //отмена созданного заказха

    @Step("Создание нового заказа")
    public ValidatableResponse createNewOrder(Order order) {
        return given().log().all()
                .spec(getSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Запрос на получение списка заказов")
    public ValidatableResponse getOrderList() {
        return given().log().all()
                .spec(getSpec())
                .when()
                .get(ORDER_PATH)
                .then();
    }

    @Step("Удалить существующий заказ по треку")
    public ValidatableResponse cancelOrder(int track) {
        return given().log().all()
                .spec(getSpec())
                .body(track)
                .when()
                .put(CANCEL_ORDER_PATH)
                .then();
    }
}
