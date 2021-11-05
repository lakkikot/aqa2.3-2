package ru.netology.patterns.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import static io.restassured.RestAssured.given;


import java.util.Locale;

public class DataGenerator {

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void sendRequestToCreateUser(UserInfo user) {
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(user) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    private static final Faker faker = new Faker(new Locale("en"));

    @Value
    public static class UserInfo {
        String login;
        String password;
        String status;
    }

    public static String getLogin() {
        String login = faker.name().username();
        return login;
    }

    public static String getPassword() {
        String password = faker.internet().password();
        return password;
    }

    // активный
    public static UserInfo createActiveUser() {
        UserInfo activeUser = new UserInfo(getLogin(), getPassword(), "active");
        sendRequestToCreateUser(activeUser); // отправь логин, пароль и статус в тело запроса
        return activeUser;
    }

    // заблокированный
    public static UserInfo createBlockedUser() {
        UserInfo blockedUser = new UserInfo(getLogin(), getPassword(), "blocked");
        sendRequestToCreateUser(blockedUser);  // отправь логин, пароль и статус в тело запроса
        return blockedUser;
    }
}
