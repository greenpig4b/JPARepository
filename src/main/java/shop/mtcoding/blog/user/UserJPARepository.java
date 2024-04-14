package shop.mtcoding.blog.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJPARepository extends JpaRepository<User,Integer> {


    //유저 확인

    @Query("select u from User u where u.username = :username and u.password = :password")
    Optional<User> findByIdAndPassword(@Param("username") String username,@Param("password") String password);
}
