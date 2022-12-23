package ru.arty_bikini.crm.web;


// Добавить endpoint /api/history/get-history HistoryController

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.arty_bikini.crm.data.SessionEntity;
import ru.arty_bikini.crm.data.other.HistoryEntity;
import ru.arty_bikini.crm.data.work.WorkTypeEntity;
import ru.arty_bikini.crm.dto.PageDTO;
import ru.arty_bikini.crm.dto.file.FileDTO;
import ru.arty_bikini.crm.dto.other.HistoryDTO;
import ru.arty_bikini.crm.dto.packet.auth.GetHistoryResponse;
import ru.arty_bikini.crm.dto.work.WorkTypeDTO;
import ru.arty_bikini.crm.jpa.HistoryRepository;
import ru.arty_bikini.crm.jpa.SessionRepository;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryController {
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private HistoryRepository historyRepository;
    
    @Autowired
    private SessionRepository sessionRepository;
    
    @PostMapping("/get-history")//получить всех history
    @ResponseBody
    public GetHistoryResponse getHistory(@RequestParam String key, @RequestParam int page) {
        //проверка на key
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new GetHistoryResponse(false, "нет сессии", null, null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canViewHistory == true) {
    
            Page<HistoryEntity> all = historyRepository.findAll(Pageable.ofSize(25).withPage(page));
    
            List<HistoryEntity> historyList = all.getContent();
            List<HistoryDTO> historyDTOList = objectMapper.convertValue(historyList, new TypeReference<List<HistoryDTO>>() {});
    
            PageDTO<HistoryDTO> pageDTO = new PageDTO<HistoryDTO>(
                    historyDTOList,
                    all.getNumber(),
                    all.getSize(),
                    all.getTotalElements(),
                    all.getTotalPages()
            );
    
    
            return new GetHistoryResponse(true, "передали", null, pageDTO);
        }
        return new GetHistoryResponse(false, "нет сессии", null, null);
    }
}
