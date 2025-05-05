package lunch_automate.com.example.app.Controller;

import lunch_automate.com.example.app.Exceptions.LunchAutomateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(SecurityException.class)
    public ProblemDetail handleSecurityException(LunchAutomateException ex) {
        return ex.toProblemDetail();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var fieldErrors = ex.getFieldErrors()
                .stream()
                .map(f -> new InvalidParams(f.getField(), f.getDefaultMessage()))
                .toList();
        var pb =  ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        pb.setTitle("Sua requisição não está com os parâmetros válidos.");
        pb.setProperty("invalid-params", fieldErrors);

        return pb;
    }

    private record InvalidParams(String name, String reason) {}
}
