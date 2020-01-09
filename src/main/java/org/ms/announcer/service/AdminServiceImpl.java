package org.ms.announcer.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ms.announcer.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Setter;

/**
 * AdminServiceImpl
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Setter(onMethod_ = {@Autowired})
    private AdminRepository arepo;

    @Override
    public List<Map<String,Integer>> getCountsByDay(LocalDate today) {
        List<Map<String,Integer>> list = new ArrayList<>();
        list = arepo.getCountUserbyDayOfThisMonth(today);
        return list;
    }

    
}