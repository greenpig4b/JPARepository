package shop.mtcoding.blog.reply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyJPARepository extends JpaRepository<Reply , Integer> {
    @Query("select r from Reply r join fetch Board b on b.id = r.board.id where r.board.id = :boardId order by r.id DESC ")
    List<Reply> findAllByBoardId(@Param("boardId") Integer boardId);

    @Query("select r from Reply r join fetch Board b on b.id = r.board.id where b.id = :boardId and r.id = :replyId")
    Reply findByReplyId(@Param("boardId") Integer boardId, @Param("replyId") Integer replyId);
}
