package shop.mtcoding.blog.user;

import lombok.Builder;
import lombok.Data;

public class UserResponse {
    @Data
    public static class userUpdate{
        private String password;
        private String email;
        @Builder
        public userUpdate(User user) {
            this.password = user.getPassword();
            this.email = user.getEmail();
        }
    }
}
