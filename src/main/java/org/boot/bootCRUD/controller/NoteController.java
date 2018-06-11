package org.boot.bootCRUD.controller;


import org.boot.bootCRUD.dao.NoteRepo;
import org.boot.bootCRUD.note.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


@Controller// This means that this class is a Controller
public class NoteController {
    @Autowired
    private NoteRepo noteRepo;
    private static int countForPages = 0;
    private static int quantityOfNotesPerPageKeeper = 0;
    private static int quantityOfPages = 0;
    private static String filterKeeper = "";
    private static String sortKeeper = "";


    @GetMapping("/")
    public String start(Model model) {
        getDbInfo(model);
        return "start";
    }

    @GetMapping("/main")
    public String main(Model model) {   //
        Iterable<Note> notes = noteRepo.findAll();
        getDbInfo(model);
        model.addAttribute("notes", notes);
        return "main";
    }

    @PostMapping("/main")
    public String add(@RequestParam
                              (value = "text", defaultValue = "&lt;none&gt") String text,
                      @RequestParam
                              (value = "comment", defaultValue = "&lt;none&gt") String comment,
                      Model model) {
        Note note = new Note(text, comment);
        noteRepo.save(note);
        getDbInfo(model);
        model.addAttribute("newNote", note);
        return "main";
    }

    @PostMapping("/edit")
    public String add(@RequestParam
                              (value = "id") int id,
                      @RequestParam
                              (value = "text", defaultValue = "&lt;none&gt") String text,
                      @RequestParam
                              (value = "isDone") boolean isDone,
                      @RequestParam
                              (value = "date") String date,
                      @RequestParam
                              (value = "comment", defaultValue = "&lt;none&gt") String comment,
                      Model model) {
        long idLong = id;
        Note note = noteRepo.findById(idLong);
        if (note != null) {
            note.setText(text);
            note.setIsDone(isDone);
            Date dateForDb = null;
            try {
                dateForDb = new SimpleDateFormat("dd-MM-yyyy").parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            note.setDate(dateForDb);
            note.setComment(comment);
            noteRepo.save(note);
        }
        getDbInfo(model);
        model.addAttribute("editNote", note);
        return "getNoteForEdit";
    }

 /* @PostMapping("/remove/getNoteForEdit")
    public String getNoteForEditFromRem(@RequestParam int id, Model model
    ) {
         long idLong = id;
        Note note = noteRepo.findById(idLong);
        getDbInfo(model);
        model.addAttribute("notes", note);
        return "getNoteForEdit";
    }    */ 
    

    @PostMapping(value={"/getNoteForEdit", "/*/getNoteForEdit"} )
    public String getNoteForEdit(@RequestParam int id, Model model
    ) {
        // Iterable<Note> notes = noteRepo.findAll();

        long idLong = id;
        Note note = noteRepo.findById(idLong);
        getDbInfo(model);
        model.addAttribute("notes", note);
        return "getNoteForEdit";
    }

    @GetMapping("/getNoteForEdit/{id}")
    public String update(@PathVariable("id") int id, Model model) {
        Note note = noteRepo.findById(id);
        model.addAttribute("notes", note);
        getDbInfo(model);
        return "getNoteForEdit";
    }

    @GetMapping("/remove/{id}")
    public String delete(@PathVariable("id") int id, Model model) {
        Note note = noteRepo.findById(id);
        if (note != null) {
            noteRepo.delete(note);
        }
        /*Iterable<Note> notes = noteRepo.findAll();
        model.addAttribute("notes", notes);
        getDbInfo(model);  */
        getPaginateModel(countForPages, quantityOfNotesPerPageKeeper, filterKeeper, sortKeeper, model);

        getDbInfo(model);
        return "paginate";
    }

    @PostMapping("/paginator")
    public String paginate(@RequestParam int quantityOfNotesPerPage,
                           @RequestParam(required = false, defaultValue = "") String filter,
                           @RequestParam(required = false, defaultValue = "") String sort,
                           Model model) {
        if (quantityOfNotesPerPage > 0) {
            filterKeeper = filter;
            sortKeeper = sort;
            countForPages = 0;
            quantityOfNotesPerPageKeeper = quantityOfNotesPerPage;
            getPaginateModel(countForPages, quantityOfNotesPerPage, filter, sort, model);
        } else {
            Iterable<Note> notes = noteRepo.findAll();
            model.addAttribute("notes", notes);
        }
        model.addAttribute("filter", filter);
        model.addAttribute("sort", sort);
        getDbInfo(model);
        return "paginate";
    }

    private Model getPaginateModel(int countForPage, int quantityOfNotesPerPage, String filter, String sort, Model model) {
        Iterable<Note> notes;
        int quantityOfNotes;
        Pageable sizeOfPage;
        quantityOfNotesPerPage = quantityOfNotesPerPage > 0 ? quantityOfNotesPerPage : getQuantityOfAllNotes();
        switch (sort) {
            case "up": {
                sizeOfPage = PageRequest.of(countForPage, quantityOfNotesPerPage, Direction.ASC, "date");
                break;
            }
            case "down": {
                sizeOfPage = PageRequest.of(countForPage, quantityOfNotesPerPage, Direction.DESC, "date");
                break;
            }
            default: {
                sizeOfPage = PageRequest.of(countForPage, quantityOfNotesPerPage, Direction.ASC, "id");
            }
        }
        Page<Note> notePage;
        switch (filter) {
            case "true": {
                notePage = noteRepo.findAllByIsDone(sizeOfPage, true); // FIRST NEEDED&&&&&
                quantityOfNotes = getQuantityOfNotesIsDone();
                quantityOfPages = getQuantityOfPages(quantityOfNotes, quantityOfNotesPerPage);
                break;
            }
            case "false": {
                notePage = noteRepo.findAllByIsDone(sizeOfPage, false);
                quantityOfNotes = getQuantityOfAllNotes() - getQuantityOfNotesIsDone();
                quantityOfPages = getQuantityOfPages(quantityOfNotes, quantityOfNotesPerPage);
                break;
            }
            default: {
                notePage = noteRepo.findAll(sizeOfPage);
                quantityOfNotes = getQuantityOfAllNotes();
                quantityOfPages = getQuantityOfPages(quantityOfNotes, quantityOfNotesPerPage);
            }
        }
        notes = notePage.getContent();
        model.addAttribute("filter", filter);
        model.addAttribute("sort", sort);
        model.addAttribute("quantityOfNotes", quantityOfNotes);
        model.addAttribute("quantityOfNotesPerPage", quantityOfNotesPerPage);
        model.addAttribute("quantityOfPages", quantityOfPages);
        model.addAttribute("notes", notes);
        model.addAttribute("countForPages", countForPage + 1);
        return model;
    }


    @GetMapping("/{direction}")
    public String nextPaginate(@PathVariable("direction") String direction,
                               Model model) {
        if (direction.startsWith("next")) {
            if (countForPages < quantityOfPages - 1) {
                countForPages++;
                getPaginateModel(countForPages, quantityOfNotesPerPageKeeper, filterKeeper, sortKeeper, model);
            } else {
                getPaginateModel(countForPages, quantityOfNotesPerPageKeeper, filterKeeper, sortKeeper, model);
            }
        }
        if (direction.startsWith("prev")) {
            if (countForPages > 0) {
                countForPages--;
                getPaginateModel(countForPages, quantityOfNotesPerPageKeeper, filterKeeper, sortKeeper, model);
            } else {
                getPaginateModel(countForPages, quantityOfNotesPerPageKeeper, filterKeeper, sortKeeper, model);
            }
        }
        getDbInfo(model);
        return "paginate";
    }

    @PostMapping(value={"/page", "/*/page"})
    public String page(@RequestParam(value = "numberOfPage") int numberOfPage, Model model) {
        if (numberOfPage > 0 && numberOfPage <= quantityOfPages) {
            countForPages = numberOfPage - 1;
            getPaginateModel(numberOfPage - 1, quantityOfNotesPerPageKeeper, filterKeeper, sortKeeper, model);
        }
        getDbInfo(model);
        return "paginate";
    }

    private Model getDbInfo(Model model) {
        int quantityOfNotes = getQuantityOfAllNotes();
        int quantityOfNotesIsDone = getQuantityOfNotesIsDone();
        int quantityOfNotesNotDone = quantityOfNotes - quantityOfNotesIsDone;
        model.addAttribute("quantity", quantityOfNotes);
        model.addAttribute("quantityOfNotesIsDone", quantityOfNotesIsDone);
        model.addAttribute("quantityOfNotesNotDone", quantityOfNotesNotDone);
        return model;
    }


    private int getQuantityOfPages(int quantityOfNotes, int quantityOfNotesPerPage) {
        return (quantityOfNotes % quantityOfNotesPerPage == 0) ?
                quantityOfNotes / quantityOfNotesPerPage :
                quantityOfNotes / quantityOfNotesPerPage + 1;
    }

    private int getQuantityOfAllNotes() {
        Iterable<Note> notes = noteRepo.findAll();
        ArrayList<Note> measuringArrayList = (ArrayList<Note>) notes;
        return measuringArrayList.size();
    }

    private int getQuantityOfNotesIsDone() {
        Iterable<Note> notesIsDone = noteRepo.findByIsDone(true);
        ArrayList<Note> measuringArrayList = (ArrayList<Note>) notesIsDone;
        return measuringArrayList.size();
    }
}
/*
if (filter != null && !filter.equals("")) {
            notes = getIsDoneOrNotDone(filter);
        }
         private Iterable<Note> getIsDoneOrNotDone(String filter) {
        Iterable<Note> notes;
        if (filter.equals("true")) {
            notes = noteRepo.findByIsDone(true);
        } else {
            notes = noteRepo.findByIsDone(false);
        }
        return notes;
    }
 */
