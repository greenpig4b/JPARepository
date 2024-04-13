package shop.mtcoding.blog.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserJPARepository userJPARepo;

    //회원가입
    @Transactional
    public User join(UserRequest.JoinDTO reqDTO){

        return userJPARepo.save(reqDTO.toEntity());
    }

}
