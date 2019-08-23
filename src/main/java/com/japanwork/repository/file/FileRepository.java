package com.japanwork.repository.file;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.japanwork.model.File;

public interface FileRepository extends JpaRepository<File, UUID>{

}
