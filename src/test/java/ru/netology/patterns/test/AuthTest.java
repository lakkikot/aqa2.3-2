package ru.netology.patterns.test;

import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.patterns.data.DataGenerator;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static ru.netology.patterns.data.DataGenerator.createActiveUser;
import static ru.netology.patterns.data.DataGenerator.createBlockedUser;


// спецификация нужна для того, чтобы переиспользовать настройки в разных запросах
class AuthTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    // вход созданным активным юзером;
    @Test
    public void activeUserLogins() {

        DataGenerator.UserInfo user = createActiveUser();
        $("[data-test-id=login] .input__control").setValue(user.getLogin());
        $("[data-test-id=password] .input__control").setValue(user.getPassword());
        $("[data-test-id=action-login]").click();
        $(Selectors.withText("Личный кабинет")).shouldBe(visible);
    }

    // вход созданным блокированным юзером
    @Test
    public void blockedUserLogins() {

        DataGenerator.UserInfo user = createBlockedUser();
        $("[data-test-id=login] .input__control").setValue(user.getLogin());
        $("[data-test-id=password] .input__control").setValue(user.getPassword());
        $("[data-test-id=action-login]").click();
        $(Selectors.withText("Пользователь заблокирован")).shouldBe(visible);
    }

    // вход с рандомными логином и паролем (без регистрации)
    @Test
    public void notRegisteredUserTryToLogin() {

        $("[data-test-id=login] .input__control").setValue(DataGenerator.getLogin());
        $("[data-test-id=password] .input__control").setValue(DataGenerator.getPassword());
        $("[data-test-id=action-login]").click();
        $(Selectors.withText("Неверно указан логин или пароль")).shouldBe(visible);
    }

    // создаем активного пользователя, вводим некорректный пароль
    @Test
    public void loginWithIncorrectPassword() {

        DataGenerator.UserInfo user = createActiveUser();
        $("[data-test-id=login] .input__control").setValue(user.getLogin());
        $("[data-test-id=password] .input__control").setValue(DataGenerator.getPassword());
        $("[data-test-id=action-login]").click();
        $(Selectors.withText("Неверно указан логин или пароль")).shouldBe(visible);
    }

    // создаем активного пользователя, вводим некорректный логин
    @Test
    public void loginWithIncorrectUsername() {

        DataGenerator.UserInfo user = createActiveUser();
        $("[data-test-id=login] .input__control").setValue(DataGenerator.getLogin());
        $("[data-test-id=password] .input__control").setValue(user.getPassword());
        $("[data-test-id=action-login]").click();
        $(Selectors.withText("Неверно указан логин или пароль")).shouldBe(visible);
    }

    // создаем блокированного пользователя, вводим некорректный пароль
    @Test
    public void loginBlockedWithIncorrectPassword() {

        DataGenerator.UserInfo user = createBlockedUser();
        $("[data-test-id=login] .input__control").setValue(user.getLogin());
        $("[data-test-id=password] .input__control").setValue(DataGenerator.getPassword());
        $("[data-test-id=action-login]").click();
        $(Selectors.withText("Неверно указан логин или пароль")).shouldBe(visible);
    }

    // создаем блокированного пользователя, вводим некорректный логин
    @Test
    public void loginBlockedWithIncorrectUsername() {

        DataGenerator.UserInfo user = createBlockedUser();
        $("[data-test-id=login] .input__control").setValue(DataGenerator.getLogin());
        $("[data-test-id=password] .input__control").setValue(user.getPassword());
        $("[data-test-id=action-login]").click();
        $(Selectors.withText("Неверно указан логин или пароль")).shouldBe(visible);
    }

}