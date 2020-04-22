package wolox.training.services;

import io.swagger.annotations.ApiOperation;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import wolox.training.errors.user.UserHttpErrors;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

@Service
public class UserService {

    @ApiOperation(value = "Given two dates , find by birthday, and return the user list or an exception", response = User.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public List<User> foundUserByBetweenBirthday(
        LocalDate date1, LocalDate date2, UserRepository userRepository){
      List<User> list = userRepository.findByBirthdayBetween(date1, date2);
      if (list.isEmpty()){
        new UserHttpErrors("Users not found", HttpStatus.NOT_FOUND);
      }
      return list;
    }

    @ApiOperation(value = "Given a sequence, find by contains name, and return the user list or an exception", response = User.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public List<User> foundUserByContainsName(String sequence, UserRepository userRepository){
      List<User> list = userRepository.findByNameContaining(sequence);
      if (list.isEmpty()){
        new UserHttpErrors("Users not found", HttpStatus.NOT_FOUND);
      }
      return list;
    }
}
