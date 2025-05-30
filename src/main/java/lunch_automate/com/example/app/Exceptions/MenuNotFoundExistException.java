package lunch_automate.com.example.app.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class MenuNotFoundExistException extends LunchAutomateException {
    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        pb.setTitle("Item do menu não foi encontrado");

        return pb;
    }
}
