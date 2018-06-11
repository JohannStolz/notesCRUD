package org.boot.bootCRUD.dao;
// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

import org.boot.bootCRUD.note.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface NoteRepo extends PagingAndSortingRepository<Note, Long> {
    List<Note> findByIsDone(Boolean isDone);

    Note findById(long id);

    Page<Note> findAll(Pageable pageable);

    List<Note> findAllByDate(Date date, Sort sort);

    Page<Note> findAllByIsDone(Pageable pageable, Boolean isDone);

}
