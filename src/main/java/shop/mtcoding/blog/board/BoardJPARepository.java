package shop.mtcoding.blog.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardJPARepository extends JpaRepository<Board,Integer> {

    @Query("select b from Board b ORDER BY b.id desc")
    List<Board> findAll();
    @Query("select b from Board b join fetch User u on b.user.id = u.id where b.id = :boardId")
    Board findByBoardId(@Param("boardId") Integer boardId);


}
