package com.OSMaster.interactors.impl;

import com.OSMaster.config.exception.GenericException;
import com.OSMaster.entities.OsEntity;
import com.OSMaster.interactors.ReportInteractor;
import com.OSMaster.repository.OsRepository;
import com.OSMaster.transportlayer.s3.AwsBucket;
import com.OSMaster.util.CsvGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportInteractorImpl implements ReportInteractor {

    private final CsvGenerator csvGenerator;
    private final OsRepository osRepository;
    private final AwsBucket awsBucket;

    @Override
    public String generateReport(int month) {
        String yearMonthPrefix = String.format("relatorio_diario_%d-%02d",
                LocalDate.now().getYear(), month);

        List<String> keys = awsBucket.listFilesByPrefix(yearMonthPrefix);

        if (keys.isEmpty()) {
            throw new GenericException("Nenhum dado encontrado para o mês informado.");
        }

        StringBuilder consolidatedCsv = new StringBuilder();
        boolean headerAdded = false;

        for (String key : keys) {
            String content = awsBucket.getFileContent(key);
            String[] lines = content.split("\\r?\\n");

            if (lines.length > 0) {
                int startLine = headerAdded ? 1 : 0;
                for (int i = startLine; i < lines.length; i++) {
                    if (!lines[i].isBlank()) {
                        consolidatedCsv.append(lines[i]).append("\n");
                    }
                }
                headerAdded = true;
            }
        }

        return consolidatedCsv.toString();
    }

    @Scheduled(cron = "0 59 23 * * *")
    public void dailyReport() {
        var osList = osRepository.findAllByDate();

        var csvContent = csvGenerator.convertToCsv(osList, OsEntity.class);
        String fileName = "relatorio_diario_" + LocalDate.now() + ".csv";

        awsBucket.uploadCsv(csvContent, fileName);
    }
}
