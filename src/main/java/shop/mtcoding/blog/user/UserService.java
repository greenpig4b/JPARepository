package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpSession;
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

    //로그인
    @Transactional
    public User login(UserRequest.LoginDTO reqDTO){
        // 1 아이디체크
        User sessionUser = userJPARepo.findByIdAndPassword(reqDTO.getUsername(),reqDTO.getPassword())
                .orElseThrow(() -> new Exception404("아이디 및 비밀번호가 일치하지않습니다."));

        return sessionUser;
    }

    //회원수정 폼
    public User updateForm(Integer userId){
        User sessionUser =  userJPARepo.findById(userId)
                .orElseThrow(() -> new Exception404("해당정보를 찾을 수 없습니다."));

        return sessionUser;
    }
}
