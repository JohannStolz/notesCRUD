package org.boot.bootCRUD.dao;


import org.boot.bootCRUD.model.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface NoteRepo extends PagingAndSortingRepository<Note, Long> {
    List<Note> findByIsDone(Boolean isDone);

    Note findById(long id);

    Page<Note> findAll(Pageable pageable);

    Page<Note> findAllByIsDone(Pageable pageable, Boolean isDone);

}
