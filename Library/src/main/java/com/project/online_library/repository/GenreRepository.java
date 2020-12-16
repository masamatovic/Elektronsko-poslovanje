package com.project.online_library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.online_library.model.Genre;

public interface GenreRepository extends JpaRepository<Genre,Long>{

}
