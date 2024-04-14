package shop.mtcoding.blog.board;

import lombok.Builder;
import lombok.Data;
import shop.mtcoding.blog.reply.Reply;
import shop.mtcoding.blog.user.User;

import java.util.List;
import java.util.stream.Collectors;

public class BoardResponse {


    @Data
    public static class Detail{
        private Integer id;
        private String title;
        private String content;
        private String username;
        private boolean isOwner;
        private List<ReplyDTO> replys;

        @Builder
        public Detail(Board board ,User sessionUser, List<Reply> replys) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.username = board.getUser().getUsername();

            if(sessionUser != null){
                if (board.getUser().getId() == sessionUser.getId()){
                    isOwner = true;
                }
            }

            this.replys = replys.stream()
                    .map(replyDTO -> new ReplyDTO(replyDTO,sessionUser))
                    .collect(Collectors.toList());

        }
        @Data
        public static class ReplyDTO{
            private String replyUser;
            private String replyComent;
            private boolean replyOwner;

            public ReplyDTO(Reply reply, User sessionUser) {
                this.replyUser = reply.getUser().getUsername();
                this.replyComent = reply.getComment();
                if (sessionUser != null){
                    if (reply.getUser().getUsername() == sessionUser.getUsername()){
                        replyOwner = true;
                    }
                }
            }
        }
    }
}
