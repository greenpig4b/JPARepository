package shop.mtcoding.blog.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardJPARepository extends JpaRepository<Board,Integer> {

    @Query("select b from Board b ORDER BY b.id desc")
    List<Board> findAll();
}
