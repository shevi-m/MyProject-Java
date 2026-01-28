package org.example.demo.Service;

//import com.example.demo.Model.Response;
import org.example.demo.Model.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<Response,Long> {
}
