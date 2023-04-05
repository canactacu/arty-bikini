package ru.arty_bikini.crm.servise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.arty_bikini.crm.Utils;
import ru.arty_bikini.crm.data.orders.google.DataGoogleEntity;
import ru.arty_bikini.crm.data.orders.google.OrderDataTypeEntity;
import ru.arty_bikini.crm.dto.enums.ColumnImportTarget;
import ru.arty_bikini.crm.google.GoogleDocumentImporter;
import ru.arty_bikini.crm.jpa.DataGoogleRepository;
import ru.arty_bikini.crm.jpa.OrderDataTypeRepository;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

import static ru.arty_bikini.crm.Utils.toDate;
import static ru.arty_bikini.crm.Utils.toTime;

@Service
public class GoogleService {

    @Autowired
    private GoogleDocumentImporter importer;

    @Autowired
    private OrderDataTypeRepository orderDataTypeRepository;

    @Autowired
    private DataGoogleRepository dataGoogleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${ab-crm.google.fileId}")
    private String fileId;

    //импорт всего файла
    public String doImport() {

        try {
            String file = importer.importSheet(fileId);

            CSVParser parser = CSVParser.parse(file, CSVFormat.EXCEL);

           // List<String> headers = parser.getHeaderNames();//заголовки из гугла
            List<String> headers = addHeader(parser);
            if (headers.size()==0){
            }


            List<OrderDataTypeEntity> columnsListBad = checkColumns(headers);//проверили все ли колонки на месте,

            if (columnsListBad.size() !=0){
                // если колонки не на месте выдаем ошибку и передаем все не совпадающие колонки,
            }
            addDataToBD(parser);//колонки на месте заполняем бд


        } catch (GeneralSecurityException e) {

            String errorCode = Utils.generatePassword();
            System.out.println("Код ошибки: " + errorCode);
            e.printStackTrace();

            return "ошибка при обращении. Скажи Наде: " + errorCode;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

       return "";
    }

    private List<String> addHeader(CSVParser parser){
        List<String> headers = new ArrayList<>();

        for (CSVRecord line : parser){
            for (int i = 0; i < line.size(); i++){
                String loc = line.get(i);
                headers.add(loc);
            }
            return headers;
        }
        return null;
    }

    //заполняем бд
    private void addDataToBD(CSVParser parser) {

        List<OrderDataTypeEntity> allColumns = orderDataTypeRepository.findAll();

        int j = 0;//строка
        for (CSVRecord line : parser) {//выдает новую строчку
            //проверка заполняли ли эту строку(ищем в бд по времени заполнения)
            String time = line.get(0);//значение времени заполнения из гугл

            String[] part = time.split(" ");

            String dot = part[part.length - 1].substring(1, 2);

            if (dot.equals(":")) {
                time = part[0] + " 0" + part[1];
            }
            //если не заполняли, то заполняем
            DataGoogleEntity dataGoogle = new DataGoogleEntity();//создаем новый

            dataGoogle.setId(0);
            dataGoogle.setConnected(false);
            dataGoogle.setDateGoogle(toTime(time));

            Map<Integer,String> accumulator = new HashMap<>();
            for (int i = 0; i < line.size(); i++) {

                String loc = line.get(i);//col=значение ячейки,i= столбец
                OrderDataTypeEntity column = null;
                for (OrderDataTypeEntity curr : allColumns) {
                    if (curr.getGoogleColumn() == i) {
                        column = curr;
                    }
                }

                if (column.getTarget() == ColumnImportTarget.FIO) {
                    dataGoogle.setName(loc);
                }

                if (column.getTarget() == ColumnImportTarget.TELEPHONE) {
                    dataGoogle.setTelephon(loc);
                }
                
                if (column.getTarget() == ColumnImportTarget.NEEDED_DATE) {
                    dataGoogle.setNeededDate(Utils.toDate(loc));
                }
    
                if (column.getTarget() == ColumnImportTarget.COMPETITION) {
                    dataGoogle.setCompetition( Utils.toDate(loc));
                }

                if (!loc.equals("")) {
                    accumulator.put(column.getId(), loc);
                }
            }

            //переделать из строки во время
            DataGoogleEntity row = dataGoogleRepository.getByDateGoogle(toTime(time));//проверяем по времени
            if (row != null) {            //если заполняли идем дальше

                if (Objects.equals(row.getName(), dataGoogle.getName())) {
                    continue;
                }

            }

            try {
                String json = objectMapper.writeValueAsString(accumulator);
                dataGoogle.setJson(json);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
               // return "строка не собралась " + time;
            }

            dataGoogleRepository.save(dataGoogle);

            j++;//строка
        }


    }

    //проверили все ли колонки на месте,
    private List<OrderDataTypeEntity> checkColumns(List<String> headers) {//проверяем столбцы из гугла на наличие новые и переименованных
        List<OrderDataTypeEntity> orderDataTypeEntityList = new ArrayList<>();// список испорченных колонок

        //берем  1 имя сравниваем с именем гугол имя в репазитории
        for (int i = 0; i < headers.size(); i++) {
            headers.get(i);//элемент из гугол
            OrderDataTypeEntity orderDataTypeEntity = orderDataTypeRepository.getByGoogleColumn(i);//ищет в бд обьекь колонкаЭ по имени гугол имя
            if(orderDataTypeEntity == null){//новый столбец
                OrderDataTypeEntity orderDataTypeEntityNew = new OrderDataTypeEntity();

                orderDataTypeEntityNew.setId(0);
                orderDataTypeEntityNew.setName(headers.get(i));

                orderDataTypeEntityNew.setGoogleColumn(i);
                orderDataTypeEntityNew.setGoogleName(headers.get(i));

                orderDataTypeRepository.save(orderDataTypeEntityNew);
                orderDataTypeEntityList.add(orderDataTypeEntityNew);


                continue;
            }
            //если столбец совпал
            if (orderDataTypeEntity.getGoogleName().equals(headers.get(i))){
                continue;
            }
            orderDataTypeEntity.setGoogleName(headers.get(i));
            orderDataTypeRepository.save(orderDataTypeEntity);
            orderDataTypeEntityList.add(orderDataTypeEntity);
        }
        return orderDataTypeEntityList;//вернем список испорченных колонок
    }



}

