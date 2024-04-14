package shop.mtcoding.blog.reply;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.board.Board;
import shop.mtcoding.blog.board.BoardJPARepository;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyJPARepository replyJPARepo;
    private final BoardJPARepository boardJPARepo;

    //댓글쓰기
    @Transactional
    public void write(ReplyRequest.SaveDTO reqDTO, User sessionUser){
        // 인증
        Board board = boardJPARepo.findById(reqDTO.getBoardId())
                .orElseThrow(() -> new Exception404("해당 게시글을 찾을 수 없습니다."));

        // 작성
        replyJPARepo.save(reqDTO.toEntity(board,sessionUser));
    }
}
