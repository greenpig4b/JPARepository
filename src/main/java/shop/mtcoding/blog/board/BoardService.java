package shop.mtcoding.blog.board;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardJPARepository boardJPARepo;


    //글쓰기
    @Transactional
    public Board write(BoardRequest.SaveDTO reqDTO, User sessionUser){

        return boardJPARepo.save(reqDTO.toEntity(sessionUser));
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
}
