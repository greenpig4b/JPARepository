package shop.mtcoding.blog.board;

import lombok.Builder;
import lombok.Data;
import shop.mtcoding.blog.reply.Reply;
import shop.mtcoding.blog.user.User;

import java.util.List;
import java.util.stream.Collectors;

public class BoardResponse {

    //상세보기
    @Data
    public static class Detail{
        private Integer id;
        private String title;
        private String content;
        private String username;
        private String boardOwner = "글쓴이아님";
        private List<ReplyDTO> replys;

        @Builder
        public Detail(Board board ,User sessionUser, List<Reply> replys) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.username = board.getUser().getUsername();

            if(sessionUser != null){
                if (board.getUser().getId() == sessionUser.getId()){
                    boardOwner = "글쓴이맞음";
                }
            }

            this.replys = replys.stream()
                    .map(replyDTO -> new ReplyDTO(replyDTO,sessionUser))
                    .collect(Collectors.toList());

        }
        @Data
        public static class ReplyDTO{
            private Integer id;
            private String replyUser;
            private String replyComent;
            private String replyOwner = "댓글쓴사람아님";

            public ReplyDTO(Reply reply, User sessionUser) {
                this.id = reply.getId();
                this.replyUser = reply.getUser().getUsername();
                this.replyComent = reply.getComment();
                if (sessionUser != null){
                    if (reply.getUser().getUsername() == sessionUser.getUsername()){
                        replyOwner = "댓글쓴사람 맞음";
                    }
                }
            }
        }
    }


    @Data
    public static class Save{
        private String title;
        private String content;
        @Builder
        public Save(Board board) {
            this.title = board.getTitle();
            this.content = board.getContent();
        }
    }

    @Data
    public static class Update{
        private String title;
        private String content;

        public Update(Board board) {
            this.title = board.getTitle();
            this.content = board.getContent();
        }
    }

    @Data
    public static class UpdateForm{
        private String title;
        private String content;

        public UpdateForm(Board board) {
            this.title = board.getTitle();
            this.content = board.getContent();
        }
    }

    @Data
    public static class Main{
        private Integer id;
        private String title;

        public Main(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
        }
    }
}
