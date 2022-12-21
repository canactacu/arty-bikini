package ru.arty_bikini.crm.web;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.arty_bikini.crm.Utils;
import ru.arty_bikini.crm.data.SessionEntity;
import ru.arty_bikini.crm.data.file.FileEntity;
import ru.arty_bikini.crm.data.file.OrderFileEntity;
import ru.arty_bikini.crm.data.orders.OrderEntity;
import ru.arty_bikini.crm.dto.PageDTO;
import ru.arty_bikini.crm.dto.file.FileDTO;
import ru.arty_bikini.crm.dto.file.OrderFileDTO;
import ru.arty_bikini.crm.dto.packet.file.*;
import ru.arty_bikini.crm.jpa.FileRepository;
import ru.arty_bikini.crm.jpa.OrderFileRepository;
import ru.arty_bikini.crm.jpa.OrderRepository;
import ru.arty_bikini.crm.jpa.SessionRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

//Добавить endpoint /api/file/upload-file //загружаем файл на сервер
//Добавить endpoint /api/file/get-report  Возвращает общее число файлов, занятое ими место
// Добавить endpoint /api/file/add-order-file
// Добавить endpoint /api/file/edit-order-file
////  Добавить endpoint /api/file/delete-order-file
// /api/file/get-order-file возвращает список картинок



@RestController
@RequestMapping("/api/file")
public class FileController {
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private SessionRepository sessionRepository;
    
    @Autowired
    private FileRepository fileRepository;
    
    @Autowired
    private OrderFileRepository orderFileRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @PostMapping("/get-file")//возвращает список картинок
    @ResponseBody
    public GetFileResponse getFile(@RequestParam String key, @RequestParam int page) {
    
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new GetFileResponse(false, "нет сессии", null, null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canEditOrder == true) {
            Page<FileEntity> all = fileRepository.findAll(Pageable.ofSize(25).withPage(page));
            
            List<FileEntity> fileEntityList = all.getContent();
            List<FileDTO> fileDTOList = objectMapper.convertValue(fileEntityList, new TypeReference<List<FileDTO>>() {});
            for (int i = 0; i < fileEntityList.size(); i++) {
                List<OrderFileEntity> orderFileEntityList = orderFileRepository.getByFile(fileEntityList.get(i));
                List<OrderFileDTO> orderFileDTOList = objectMapper.convertValue(orderFileEntityList, new TypeReference<List<OrderFileDTO>>() {});
                fileDTOList.get(i).setOrders(orderFileDTOList);
            }
            
            PageDTO<FileDTO> pageDTO = new PageDTO<FileDTO>(
                    fileDTOList,
                    all.getNumber(),
                    all.getSize(),
                    all.getTotalElements(),
                    all.getTotalPages()
            );
    
    
            return new GetFileResponse(true, "передали", null, pageDTO);
        }
        return new GetFileResponse(false, "нет сессии", null, null);
    
    }
    
    @PostMapping("/delete-order-file")
    @ResponseBody
    public DeleteOrderFileResponse deleteOrderFile(@RequestParam String key, @RequestBody DeleteOrderFileRequest body){
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new DeleteOrderFileResponse(false, "нет сессии", null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canEditOrder == true) {
            
            OrderFileEntity orderFileEntity = orderFileRepository.getById(body.getOrderFile().getId());
            if (orderFileEntity==null) {
                return new DeleteOrderFileResponse(false, "в бд такого нет", null);
            }
    
            orderFileRepository.delete(orderFileEntity);
            
            return new DeleteOrderFileResponse(true,"удалили", null);
        }
        return new DeleteOrderFileResponse(false, "нет сессии", null);
        
    }
    
    @PostMapping("/upload-file")//загружаем файл на сервер
    @ResponseBody
    public UploadFileResponse uploadFile(@RequestParam String key, @RequestParam MultipartFile file) {
        
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new UploadFileResponse(false, "нет сессии", null, null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canEditOrder == true) {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setId(0);
            FileEntity saveFile = fileRepository.save(fileEntity);
            saveFile.getId();
            
            String nameFile = String.valueOf(saveFile.getId());
            
            String partNameFile = file.getOriginalFilename();
            partNameFile = partNameFile.substring(partNameFile.lastIndexOf("."));//получили расширение без точки
            
            nameFile = nameFile+ partNameFile;// получили: id.расширение
            File oneFile;

            try {
                //создаем путь к файлу
                oneFile = new File("./file/" + nameFile);
                File folder = oneFile.getParentFile();
    
                //создаем необходимые папки
                folder.mkdirs();
    
                //создаем файл
                oneFile.createNewFile();
        
                //открыли на запись файл someFile
                FileOutputStream output = new FileOutputStream(oneFile);
        
                try (output) {
    
                    InputStream stream = file.getInputStream();
                    try (stream) {
    
                        byte[] data = new byte[1024 * 1024];
                        //будем считывать пачками по 1 мб
    
                        while (true) {
        
                            int size = stream.read(data);//из stream пишем в data. размер
        
                            if (size < 0) {//если данных не осталось, метод stream выдаст -1
                                break;
                            }
        
                            output.write(data, 0, size);
                        }
    
                        output.flush();//записываем на диск
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                return new UploadFileResponse(false, "ошибка сохранения файла", null, null);
            }
    
            saveFile.setLocation("/file/" + nameFile);
            saveFile.setSize(oneFile.length());//размер файла
            saveFile.setUploadAt(LocalDateTime.now());//дата загрузки
            saveFile.setUploadBy(session.getUser());//кто загрузил
            saveFile.setSha256(Utils.SHA256(oneFile));//посчитать хеш файла
    
    
            saveFile = fileRepository.save(saveFile);
            FileDTO fileDTO = objectMapper.convertValue(saveFile, FileDTO.class);
    
            return new UploadFileResponse(true,"список передали", null, fileDTO);
        }
        return new UploadFileResponse(false, "нет сессии", null, null);
    
    }
    
    @PostMapping("/get-report")//Возвращает общее число файлов, занятое ими место
    @ResponseBody
    public GetReportResponse getReport(@RequestParam String key) {
        
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new GetReportResponse(false, "нет сессии", null, 0, 0);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canEditOrder == true) {
           
            FileRepository.AllFileReport all = fileRepository.getAll();
            int count = all.getCount();
            long totalSize = all.getTotalSize();
    
            return new GetReportResponse(true,"передали", null, count, totalSize);
        }
        return new GetReportResponse(false, "нет сессии", null, 0, 0);
        
    }
    
    @PostMapping("/add-order-file")//добавить
    @ResponseBody
    public AddOrderFileResponse addOrderFile(@RequestParam String key, @RequestBody AddOrderFileRequest body){
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new AddOrderFileResponse(false, "нет сессии", null, null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canEditOrder == true) {
    
            OrderFileEntity orderFileEntity = orderFileRepository.getById(body.getOrderFile().getId());
            if (body.getOrderFile().getId()!=0) {
                return new AddOrderFileResponse(false, "orderFileEntity!=null", null, null);
            }
            orderFileEntity = new OrderFileEntity();
            orderFileEntity.setId(0);
            orderFileEntity.setPriority(body.getOrderFile().getPriority());
            orderFileEntity.setComment(body.getOrderFile().getComment());
    
            if (body.getOrderFile().getFile()!=null) {
                FileEntity fileEntity = fileRepository.getById(body.getOrderFile().getFile().getId());
                if (fileEntity == null) {
                    return new AddOrderFileResponse(false, "fileEntity == null", null, null);
                }
                orderFileEntity.setFile(fileEntity);
            }
            if (body.getOrderFile().getCategory()!= null) {
                orderFileEntity.setCategory(body.getOrderFile().getCategory());
            }
            if (body.getOrderFile().getOrder()!=null) {
                OrderEntity orderEntity = orderRepository.getById(body.getOrderFile().getOrder().getId());
                if (orderEntity==null) {
                    return new AddOrderFileResponse(false, "orderEntity == null", null, null);
                }
                orderFileEntity.setOrder(orderEntity);
            }
    
            OrderFileEntity save = orderFileRepository.save(orderFileEntity);
    
            OrderFileDTO orderFileDTO = objectMapper.convertValue(save, OrderFileDTO.class);
    
            return new AddOrderFileResponse(true,"передали", null, orderFileDTO);
        }
        return new AddOrderFileResponse(false, "нет сессии", null, null);
    
    }
    
    @PostMapping("/edit-order-file")
    @ResponseBody
    public EditOrderFileResponse editOrderFile(@RequestParam String key, @RequestBody EditOrderFileRequest body){
        SessionEntity session = sessionRepository.getByKey(key);
        if (session == null) {
            return new EditOrderFileResponse(false, "нет сессии", null, null);
        }
        //проверка на права доступа
        if (session.getUser().getGroup().canEditOrder == true) {
            
            OrderFileEntity orderFileEntity = orderFileRepository.getById(body.getOrderFile().getId());
            if (body.getOrderFile().getId()==0) {
                return new EditOrderFileResponse(false, "orderFileEntity==null", null, null);
            }
            orderFileEntity.setPriority(body.getOrderFile().getPriority());
            orderFileEntity.setComment(body.getOrderFile().getComment());
            
            if (body.getOrderFile().getFile()!=null) {
                FileEntity fileEntity = fileRepository.getById(body.getOrderFile().getFile().getId());
                if (fileEntity == null) {
                    return new EditOrderFileResponse(false, "fileEntity == null", null, null);
                }
                orderFileEntity.setFile(fileEntity);
            }
            if (body.getOrderFile().getCategory()!= null) {
                orderFileEntity.setCategory(body.getOrderFile().getCategory());
            }
            if (body.getOrderFile().getOrder()!=null) {
                OrderEntity orderEntity = orderRepository.getById(body.getOrderFile().getOrder().getId());
                if (orderEntity==null) {
                    return new EditOrderFileResponse(false, "orderEntity == null", null, null);
                }
                orderFileEntity.setOrder(orderEntity);
            }
            
            OrderFileEntity save = orderFileRepository.save(orderFileEntity);
            
            OrderFileDTO orderFileDTO = objectMapper.convertValue(save, OrderFileDTO.class);
            
            return new EditOrderFileResponse(true,"передали", null, orderFileDTO);
        }
        return new EditOrderFileResponse(false, "нет сессии", null, null);
        
    }
}
