package shop.mtcoding.blog.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mtcoding.blog._core.errors.exception.Exception404;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserJPARepository userJPARepo;

    //회원가입
    @Transactional
    public User join(UserRequest.JoinDTO reqDTO){

        return userJPARepo.save(reqDTO.toEntity());
    }

    //회원수정 폼
    public User updateForm(Integer userId){
        User sessionUser =  userJPARepo.findById(userId)
                .orElseThrow(() -> new Exception404("해당정보를 찾을 수 없습니다."));

        return sessionUser;
    }
}
