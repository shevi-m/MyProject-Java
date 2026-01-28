package org.example.demo.Service;


import org.example.demo.Model.Age;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AgeRepository extends JpaRepository<Age,Long> {
}
