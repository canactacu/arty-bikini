package ru.arty_bikini.crm.servise;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.arty_bikini.crm.data.orders.OrderDataTypeEntity;
import ru.arty_bikini.crm.google.GoogleDocumentImporter;
import ru.arty_bikini.crm.jpa.OrderDataTypeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleService {

    @Autowired
    private GoogleDocumentImporter importer;

    @Autowired
    private OrderDataTypeRepository orderDataTypeRepository;

    public void doImport() {

        String file = importer.importSheet("1SrQzAascY-9AFVzUR6U-bDoROXR1sU1-oWhgIemM6M4");

        CSVParser parser = CSVParser.parse(file, CSVFormat.RFC4180);

        List<String> headers = parser.getHeaderNames();//заголовки

        int j = 0;
        for (CSVRecord line : parser) {//выдает новую строчку

            for (int i = 0; i < line.size(); i++) {
                String col = line.get(i);//col=значение ячейки,i= столбец
            }
            String timeColumn = line.get(0);

            j++;//строка
        }


        checkColumns(headers);
    }

    private void checkColumns(List<String> headers) {
        List<OrderDataTypeEntity> orderDataTypeEntityList = new ArrayList<>();
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
    }

}

