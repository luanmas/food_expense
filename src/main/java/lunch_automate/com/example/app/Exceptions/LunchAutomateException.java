package lunch_automate.com.example.app.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class LunchAutomateException extends RuntimeException {
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        pb.setTitle("Internal Server Error");
        return pb;
    }
}
