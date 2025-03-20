package com.airbus.api.controller;

import com.airbus.api.model.Revinfo;
import com.airbus.api.service.RevinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/revisions")
public class RevinfoController {

    private final RevinfoService revinfoService;

    @Autowired
    public RevinfoController(RevinfoService revinfoService) {
        this.revinfoService = revinfoService;
    }

    @PostMapping
    public Revinfo createRevinfo(@RequestBody Revinfo revinfo) {
        return revinfoService.saveRevinfo(revinfo);
    }
    
    @GetMapping
    public List<Revinfo> getAllRevisions() {
        return revinfoService.getAllRevisions();
    }

    @GetMapping("/{rev}")
    public Optional<Revinfo> getRevinfoById(@PathVariable Integer rev) {
        return revinfoService.getRevinfoById(rev);
    }

   
    @DeleteMapping("/{rev}")
    public void deleteRevinfo(@PathVariable Integer rev) {
        revinfoService.deleteRevinfo(rev);
    }
}