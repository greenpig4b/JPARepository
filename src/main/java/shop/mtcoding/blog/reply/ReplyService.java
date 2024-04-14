package shop.mtcoding.blog.reply;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyJPARepository replyJPARepos;

    public List<Reply> findByAll(Integer boardId){

        List<Reply> replyList = replyJPARepos.findAllByBoardId(boardId);

        return replyList;
    }
}
