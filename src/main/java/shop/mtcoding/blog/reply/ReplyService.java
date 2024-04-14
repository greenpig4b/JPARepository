package shop.mtcoding.blog.reply;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception403;
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
        Reply reply = reqDTO.toEntity(board,sessionUser);

        replyJPARepo.save(reply);
    }

    //댓글삭제
    @Transactional
    public void delete(Integer userId,Integer boardId,Integer replyId){
        // 인증
        Board board = boardJPARepo.findById(boardId)
                .orElseThrow(() -> new Exception404("해당 게시글을 찾을 수 없습니다."));

        Reply reply = replyJPARepo.findByReplyId(board.getId(),replyId);

        System.out.println("댓글유저 id"+reply.getUser().getId());
        System.out.println("세션유저 id"+userId);

        // 권한
        if(reply.getUser().getId() != userId){
            throw new Exception403("삭제할 권한이 없습니다");
        }

        // 삭제
        replyJPARepo.deleteById(replyId);
    }
}
