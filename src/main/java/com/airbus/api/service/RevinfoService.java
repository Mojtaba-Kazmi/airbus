package com.airbus.api.service;

import com.airbus.api.model.Revinfo;
import com.airbus.api.repository.RevinfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RevinfoService {

    private final RevinfoRepository revinfoRepository;

    @Autowired
    public RevinfoService(RevinfoRepository revinfoRepository) {
        this.revinfoRepository = revinfoRepository;
    }


    public Revinfo saveRevinfo(Revinfo revinfo) {
        return revinfoRepository.save(revinfo);
    }

   
    public List<Revinfo> getAllRevisions() {
        return revinfoRepository.findAll();
    }

  
    public Optional<Revinfo> getRevinfoById(Integer rev) {
        return revinfoRepository.findById(rev);
    }

    public void deleteRevinfo(Integer rev) {
        revinfoRepository.deleteById(rev);
    }
}