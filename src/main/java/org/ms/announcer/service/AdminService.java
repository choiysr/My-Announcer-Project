package org.ms.announcer.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * AdminService
 */
public interface AdminService {

    public List<Map<String,Integer>> getCountsByDay(LocalDate today); 

    
}