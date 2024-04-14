package shop.mtcoding.blog.reply;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.util.ApiUtil;
import shop.mtcoding.blog.user.SessionUser;
import shop.mtcoding.blog.user.User;
import shop.mtcoding.blog.user.UserService;

@RequiredArgsConstructor
@RestController
public class ReplyController {
    private final ReplyService replyService;
    private final HttpSession session;
    private final UserService userService;

    @PostMapping("/api/reply")
    public ResponseEntity<?> save(@RequestBody ReplyRequest.SaveDTO reqDTO){
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        User user  = userService.findById(sessionUser.getId());
        
        ReplyResponse.Save respDTO = replyService.write(reqDTO, user);

        return ResponseEntity.ok()
                .body(new ApiUtil<>(respDTO));
    }

    @DeleteMapping("/api/reply/{replyid}")
    public ResponseEntity<?> delete(@PathVariable Integer replyid){
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        replyService.delete(sessionUser.getId(),replyid);

        return ResponseEntity.ok()
                .body(new ApiUtil<>(null));
    }
}
