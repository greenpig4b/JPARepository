package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mtcoding.blog._core.errors.exception.Exception401;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog._core.util.JwtUtil;
import shop.mtcoding.blog.board.Board;

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
    public String login(UserRequest.LoginDTO reqDTO){
        // 1 아이디체크
        User sessionUser = userJPARepo.findByIdAndPassword(reqDTO.getUsername(),reqDTO.getPassword())
                .orElseThrow(() -> new Exception404("아이디 및 비밀번호가 일치하지않습니다."));

        String jwt = JwtUtil.create(sessionUser);

        return jwt;
    }

    //회원수정 폼
    public User updateForm(Integer userId){
        User sessionUser =  userJPARepo.findById(userId)
                .orElseThrow(() -> new Exception404("해당정보를 찾을 수 없습니다."));


        return sessionUser;
    }

    //회원수정
    public UserResponse.userUpdate update(Integer userId ,UserRequest.UpdateDTO reqDTO){
        // 1. 조회
        User user  = userJPARepo.findById(userId)
                .orElseThrow(() -> new Exception401("로그인을 하셔야합니다"));
        // 2. 수정
        user.updateUser(reqDTO);

        return new UserResponse.userUpdate(user);
    }

    public User findById(Integer sessionUser){
        User user = userJPARepo.findById(sessionUser)
                .orElseThrow(() -> new Exception404("해당 유저가없습니다"));

        return user;
    }
}
