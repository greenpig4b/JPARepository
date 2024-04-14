package shop.mtcoding.blog.reply;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog.user.User;

@RequiredArgsConstructor
@Controller
public class ReplyController {
    private final ReplyService replyService;
    private final HttpSession session;
    @PostMapping("/reply/save")
    public String save(ReplyRequest.SaveDTO reqDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");

        replyService.write(reqDTO,sessionUser);
        System.out.println("333333333333333333333");

        return "redirect:/board/"+ reqDTO.getBoardId();
    }
}
