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

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable Integer id,BoardRequest.UpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.update(id,sessionUser.getId(),reqDTO);

        return "redirect:/board/"+id;
    }

    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable Integer id, HttpServletRequest request) {
        Board board = boardService.updateForm(id);
        request.setAttribute("board", board);

        return "board/update-form";
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.delete(id,sessionUser.getId());

        return "redirect:/";
    }

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        List<Board> boardList = boardService.findAll();
        request.setAttribute("boardList", boardList);

        return "index";
    }

    @GetMapping("/board/save-form")
    public String saveForm() {

        return "board/save-form";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        BoardResponse.Detail respDTO = boardService.detail(id,sessionUser);
        System.out.println(respDTO);
        request.setAttribute("board", respDTO);

        return "board/detail";
    }
}
