package shop.mtcoding.blog.board;

import lombok.Builder;
import lombok.Data;
import shop.mtcoding.blog.user.User;

public class BoardResponse {


    @Data
    public static class Detail{
        private Integer id;
        private String title;
        private String content;
        private String username;
        private boolean isOwner;

        @Builder
        public Detail(Board board ,User sessionUser) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.username = board.getUser().getUsername();

            if(sessionUser != null){
                if (board.getUser().getId() == sessionUser.getId()){
                    isOwner = true;
                }
            }
        }
    }
}
