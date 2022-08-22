package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void registration_UserValid_ok() {
        user.setLogin("example1");
        user.setPassword("123456");
        user.setAge(18);
        registrationService.register(user);
        StorageDao storageDao = new StorageDaoImpl();
        User actual = storageDao.get(user.getLogin());
        assertNotNull(actual);
    }

    @Test
    void registration_VeryOldUser_notOk() {
        user.setLogin("example9");
        user.setPassword("123456");
        user.setAge(546);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_UserNullLogin_notOk() {
        user.setLogin(null);
        user.setPassword("123456");
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_UserWithEmptyLogin_notOk() {
        user.setLogin("");
        user.setPassword("123456");
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_UserInvalidPassword_notOk() {
        user.setLogin("example2");
        user.setPassword("12345");
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_UserWithNullPassword_notOk() {
        user.setLogin("example3");
        user.setPassword(null);
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_UserWithEmptyPassword_notOk() {
        user.setLogin("example4");
        user.setPassword("");
        user.setAge(20);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_UserInvalidAge_notOk() {
        user.setLogin("example5");
        user.setPassword("123456");
        user.setAge(16);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_UserWithNullAge_notOk() {
        user.setLogin("example6");
        user.setPassword("123456");
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_UserWithSameLogins_notOk() {
        user.setLogin("example7");
        user.setPassword("123456");
        user.setAge(20);
        registrationService.register(user);
        User user = new User();
        user.setLogin("example7");
        user.setPassword("2368994");
        user.setAge(21);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_UserNull_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

}
