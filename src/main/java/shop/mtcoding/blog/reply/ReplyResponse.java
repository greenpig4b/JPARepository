package shop.mtcoding.blog.reply;

import lombok.Data;
import shop.mtcoding.blog.board.Board;

public class ReplyResponse {

    @Data
    public static class Save{
        private Integer boardId;
        private Integer replyId;
        private String comment;

        public Save(Reply reply,Integer boardId) {
            this.boardId = boardId;
            this.replyId = reply.getId();
            this.comment = reply.getComment();
        }
    }


}
