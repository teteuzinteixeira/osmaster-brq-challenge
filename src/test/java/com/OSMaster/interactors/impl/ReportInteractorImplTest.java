package com.OSMaster.interactors.impl;

import com.OSMaster.config.exception.GenericException;
import com.OSMaster.repository.OsRepository;
import com.OSMaster.transportlayer.s3.AwsBucket;
import com.OSMaster.util.CsvGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportInteractorImplTest {

    @Mock private CsvGenerator csvGenerator;
    @Mock private OsRepository osRepository;
    @Mock private AwsBucket awsBucket;

    @InjectMocks private ReportInteractorImpl reportInteractor;

    @Test
    void shouldGenerateReportConsolidatingFiles() {
        int month = 5;
        String prefix = String.format("relatorio_diario_%d-05", LocalDate.now().getYear());

        List<String> mockKeys = List.of("file1.csv", "file2.csv");
        String content1 = "ID;TITULO\n1;OS Teste 1";
        String content2 = "ID;TITULO\n2;OS Teste 2";

        when(awsBucket.listFilesByPrefix(prefix)).thenReturn(mockKeys);
        when(awsBucket.getFileContent("file1.csv")).thenReturn(content1);
        when(awsBucket.getFileContent("file2.csv")).thenReturn(content2);

        String result = reportInteractor.generateReport(month);

        assertTrue(result.contains("ID;TITULO"));
        assertTrue(result.contains("1;OS Teste 1"));
        assertTrue(result.contains("2;OS Teste 2"));
        long headerCount = result.lines().filter(line -> line.equals("ID;TITULO")).count();
        assertEquals(1, headerCount);
    }

    @Test
    void shouldThrowExceptionWhenNoFilesFound() {
        int month = 10;
        when(awsBucket.listFilesByPrefix(anyString())).thenReturn(Collections.emptyList());

        assertThrows(GenericException.class, () -> reportInteractor.generateReport(month));
    }
}