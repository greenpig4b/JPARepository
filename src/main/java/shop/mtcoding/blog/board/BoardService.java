package shop.mtcoding.blog.board;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mtcoding.blog._core.errors.exception.Exception401;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.reply.Reply;
import shop.mtcoding.blog.reply.ReplyJPARepository;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardJPARepository boardJPARepo;
    private final ReplyJPARepository replyJPARepo;

    //글쓰기
    @Transactional
    public BoardResponse.Save write(BoardRequest.SaveDTO reqDTO, User sessionUser){
        Board board = boardJPARepo.save(reqDTO.toEntity(sessionUser));

        return new BoardResponse.Save(board);
    }

    //글수정
    @Transactional
    public void update(Integer id ,Integer sessionUserId,BoardRequest.UpdateDTO reqDTO){

        // 1. 조회
        Board board = boardJPARepo.findById(id)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다"));
        // 2. 권한처리
        if (board.getUser().getId() != sessionUserId){
            throw new Exception403("게시글을 수정할 권한이 없습니다.");
        }
        // 3. 수정
        board.updateBoard(reqDTO);

    }

    // 게시글 뿌리기
    public List<Board> findAll(){
        List<Board> boardList = boardJPARepo.findAll();

        return boardList;
    }

    //게시글 삭제
    public void delete(Integer boardId, Integer sessionUserId){
        // 1 인증
        if(sessionUserId == null){
            throw new Exception401("로그인이 필요한 서비스입니다");
        }
        // 2 권한
        Board board = boardJPARepo.findById(boardId)
                .orElseThrow(() -> new Exception404("해당 게시글을 찾을 수 없습니다"));

        if (sessionUserId != board.getUser().getId()){
            throw  new Exception403("삭제할 권한이 없습니다");
        }
        //3 삭제
        boardJPARepo.deleteById(boardId);
    }

    // 게시글 상세보기
    public BoardResponse.Detail detail(Integer boardId,User sessionUser){
        // 1 권한
        Board board = boardJPARepo.findByBoardId(boardId);
        List<Reply> replies = replyJPARepo.findAllByBoardId(boardId);

        return new BoardResponse.Detail(board,sessionUser,replies);

    }

    // 게시글 업데이트폼
    public Board updateForm(Integer boardId){
        Board board = boardJPARepo.findByBoardId(boardId);

        return  board;
    }

}
