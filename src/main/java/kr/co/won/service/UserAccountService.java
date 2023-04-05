package kr.co.won.service;

import kr.co.won.domain.UserDomain;
import kr.co.won.dto.UserAccountDto;
import kr.co.won.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserRepository userAccountRepository;

    @Transactional(readOnly = true)
    public Optional<UserAccountDto> searchUser(String username) {
        return userAccountRepository.findById(username)
                .map(UserAccountDto::from);
    }

    public UserAccountDto saveUser(String username, String password, String email, String nickname, String memo) {
        return UserAccountDto.from(
                userAccountRepository.save(UserDomain.of(username, password, email, nickname, memo))
        );
    }

}
