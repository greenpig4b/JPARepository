package shop.mtcoding.blog.reply;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shop.mtcoding.blog.board.Board;

import java.util.List;

@DataJpaTest
public class ReplyJPARepositoryTest {
    @Autowired
    private ReplyJPARepository replyJPARepo;


    @Test
    public void findAllByBoardId_test(){
        //given
        Integer boardId = 4;
        //when
        List<Reply> replyList = replyJPARepo.findAllByBoardId(boardId);
        //then
        System.out.println("결과값 ================" + replyList.size());
        Assertions.assertThat(replyList.get(0).getUser().getUsername()).isEqualTo("cos");
    }

}
