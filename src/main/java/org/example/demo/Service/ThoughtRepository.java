package org.example.demo.Service;

import org.example.demo.Model.Thought;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThoughtRepository extends JpaRepository<Thought,Long> {
    List<Thought> getThoughtsByUser_Id(Long id);
}
