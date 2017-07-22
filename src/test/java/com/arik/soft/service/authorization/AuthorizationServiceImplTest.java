package com.arik.soft.service.authorization;

import com.arik.soft.config.AuthorizationConfig;
import com.arik.soft.config.RepositoryConfig;
import com.arik.soft.domain.User;
import com.arik.soft.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@DataJpaTest

@Import({RepositoryConfig.class, AuthorizationConfig.class})
public class AuthorizationServiceImplTest {

    @Inject
    private UserRepository userRepository;

    @Inject
    private AuthorizationService authorizationService;


    @Before
    public void doSetup() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setAddress("LDN");
        user.setEmail("john.doe@gmail.com");
        user.setPhoneNo("9959586585");
        user.setUserName("johndoe");
        user.setPassword("a344bb828e89b74f371dafdac635525fa729f04a");

        userRepository.save(user);
    }

    @Test
    public void testHashPassword() {
        String hashedPassword = authorizationService.generateHashedPassword("mypassword");
        assertThat(hashedPassword, is(notNullValue()));
    }

    @Test
    public void testLogin() {
        boolean isAuthorized = authorizationService.login("abc", "mypassword");
        assertThat(isAuthorized, is(false));

        isAuthorized = authorizationService.login("johndoe", "testpassword");
        assertThat(isAuthorized, is(false));

        isAuthorized = authorizationService.login("johndoe", "mypassword");
        assertThat(isAuthorized, is(true));
    }
}
