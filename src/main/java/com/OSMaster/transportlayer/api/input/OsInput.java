package com.OSMaster.transportlayer.api.input;

import com.OSMaster.interactors.CompleteOsInteractor;
import com.OSMaster.interactors.CreateOsInteractor;
import com.OSMaster.interactors.ReportInteractor;
import com.OSMaster.transportlayer.dto.request.CreateOsRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/os")
@RequiredArgsConstructor
public class OsInput {

    private final CreateOsInteractor createOsInteractor;
    private final CompleteOsInteractor completeOsInteractor;
    private final ReportInteractor reportInteractor;

    @PostMapping("/create")
    public ResponseEntity<?> createOs(@Valid @RequestBody CreateOsRequest request) {
        var savedOs = createOsInteractor.createOs(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("A ordem de serviço foi criada com sucesso! Número da OS: " + savedOs.getOsNumber());
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<?> completeOs(@PathVariable Long id) {
        completeOsInteractor.completeOs(id);
        return ResponseEntity.status(HttpStatus.OK).body("A ordem de serviço "+ id +" foi completada com sucesso!");
    }

    @GetMapping("/report/monthly/{month}")
    public ResponseEntity<byte[]> downloadMonthlyReport(@PathVariable int month) {
        String csvContent = reportInteractor.generateReport(month);

        byte[] csvBytes = csvContent.getBytes(StandardCharsets.UTF_8);
        String fileName = "relatorio_mensal_" + month + ".csv";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .header(HttpHeaders.CONTENT_TYPE, "text/csv")
                .body(csvBytes);
    }
}
