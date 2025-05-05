package lunch_automate.com.example.app.Repository;

import lunch_automate.com.example.app.Dto.LunchRequest;
import lunch_automate.com.example.app.Dto.LunchResponse;
import lunch_automate.com.example.app.Entity.Lunch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LunchRepository extends JpaRepository<Lunch, Long> {
}
