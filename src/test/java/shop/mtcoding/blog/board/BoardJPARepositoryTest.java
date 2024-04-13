package shop.mtcoding.blog.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class BoardJPARepositoryTest {

    @Autowired
    BoardJPARepository boardJPARepo;


    @Test
    public void findAll(){
        //given

        //when
        List<Board> boardList = boardJPARepo.findAll();
        //then
        System.out.println("결과값 ================" + boardList.size());
        Assertions.assertThat(boardList.get(0).getContent()).isEqualTo("내용4");
    }

}
