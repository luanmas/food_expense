package lunch_automate.com.example.app.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class NotFoundOrderException extends LunchAutomateException {

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pb.setTitle("Pedido não encontrado");
        pb.setDetail("Não foi possível encontrar o Pedido");
        return pb;
    }
}
