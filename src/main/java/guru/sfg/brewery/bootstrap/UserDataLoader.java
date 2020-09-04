package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Slf4j
@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (authorityRepository.count() == 0) {
            loadUserAndAuthorities();
        }
        ;

    }

    private void loadUserAndAuthorities() {
        Authority admin = Authority.builder()
                .role("ADMIN")
                .build();
        authorityRepository.save(admin);
        Authority user1 = Authority.builder()
                .role("USER")
                .build();
        authorityRepository.save(user1);
        Authority customer = Authority.builder()
                .role("CUSTOMER")
                .build();
        authorityRepository.save(customer);

        User useradmin = User.builder()
                .username("spring")
                .password(passwordEncoder.encode("spring"))
                .authority(admin)
                .build();
        userRepository.save(useradmin);
        User user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user"))
                .authority(user1)
                .build();
        userRepository.save(user);
        User scott = User.builder()
                .username("scott")
                .password(passwordEncoder.encode("tiger"))
                .authority(customer)
                .build();
        userRepository.save(scott);

        log.debug("User and authorities loaded" + userRepository.count());

    }

}
