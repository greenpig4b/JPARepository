package shop.mtcoding.blog.user;

import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.errors.exception.Exception400;
import shop.mtcoding.blog._core.errors.exception.Exception401;
import shop.mtcoding.blog._core.util.ApiUtil;


@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final HttpSession session;

    //회원가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody UserRequest.JoinDTO reqDTO, Errors erros) {
        User sessionUser = userService.join(reqDTO);
        session.setAttribute("sessionUser",sessionUser);

       return ResponseEntity.ok()
               .body(new ApiUtil<>(null));
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginDTO reqDTO) {

        String jwt = userService.login(reqDTO);

        return  ResponseEntity.ok()
                .header("Authorization", "Bearer " + jwt)
                .body(new ApiUtil<>(null));
    }

    // 수정요청 폼
    @GetMapping("/api/users")
    public ResponseEntity<?> updateForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User respDTO = userService.updateForm(sessionUser.getId());

        return ResponseEntity.ok()
                .body(new ApiUtil<>(respDTO));
    }

    @PutMapping("/api/update")
    public ResponseEntity<?> update(@RequestBody UserRequest.UpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        UserResponse.userUpdate respDTO = userService.update(sessionUser.getId(), reqDTO);

       return ResponseEntity.ok()
               .body(new ApiUtil<>(respDTO));
    }

}
