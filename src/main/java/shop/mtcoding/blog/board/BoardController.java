package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog._core.util.ApiUtil;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor // final이 붙은 친구들의 생성자를 만들어줘
@RestController // new BoardController(IoC에서 BoardRepository를 찾아서 주입) -> IoC 컨테이너 등록
public class BoardController {
    private final BoardService boardService;
    private final HttpSession session;

    @PostMapping("/api/board")
    public ResponseEntity<?> write(@RequestBody BoardRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        BoardResponse.Save respDTO =  boardService.write(reqDTO,sessionUser);

        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }

    @PutMapping("/api/{id}/board")
    public ResponseEntity<?> update(@PathVariable Integer id,@RequestBody BoardRequest.UpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        BoardResponse.Update respDTO = boardService.update(id,sessionUser.getId(),reqDTO);

        return ResponseEntity.ok().body(new ApiUtil<>(respDTO));
    }

    @GetMapping("/api/{id}/boards")
    public ResponseEntity<?> updateForm(@PathVariable Integer id) {

        BoardResponse.UpdateForm respDTO = boardService.updateForm(id);

        return ResponseEntity.ok()
                .body(new ApiUtil<>(respDTO));
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        boardService.delete(id,sessionUser.getId());

        return ResponseEntity.ok()
                .body(new ApiUtil<>(null));
    }

    @GetMapping("/")
    public ResponseEntity<?> index() {
        List<BoardResponse.Main> respDTO = boardService.findAll();

        return ResponseEntity.ok()
                .body(new ApiUtil<>(respDTO));
    }

    @GetMapping("/api/boards/{id}")
    public ResponseEntity<?> detail(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        BoardResponse.Detail respDTO = boardService.detail(id,sessionUser);

       return  ResponseEntity.ok()
               .body(new ApiUtil<>(respDTO));
    }
}
