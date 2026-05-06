package com.OSMaster.transportlayer.api.input;

import com.OSMaster.interactors.CompleteOsInteractor;
import com.OSMaster.interactors.CreateOsInteractor;
import com.OSMaster.interactors.ReportInteractor;
import com.OSMaster.transportlayer.dto.request.CreateOsRequest;
import com.OSMaster.entities.OsEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OsInputTest {

    @Mock private CreateOsInteractor createOsInteractor;
    @Mock private CompleteOsInteractor completeOsInteractor;
    @Mock private ReportInteractor reportInteractor;

    @InjectMocks private OsInput osInput;

    @Test
    void createOs_Success() {
        var request = new CreateOsRequest();
        var osMock = mock(OsEntity.class);
        when(osMock.getOsNumber()).thenReturn(123L);
        when(createOsInteractor.createOs(request)).thenReturn(osMock);

        var response = osInput.createOs(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(createOsInteractor).createOs(request);
    }

    @Test
    void createOs_Error() {
        var request = new CreateOsRequest();
        when(createOsInteractor.createOs(request)).thenThrow(new RuntimeException("Erro"));

        var response = osInput.createOs(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void completeOs_Success() {
        Long id = 1L;
        doNothing().when(completeOsInteractor).completeOs(id);

        var response = osInput.completeOs(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(completeOsInteractor).completeOs(id);
    }

    @Test
    void downloadMonthlyReport_Success() {
        int month = 5;
        String csvContent = "header;data";
        when(reportInteractor.generateReport(month)).thenReturn(csvContent);

        var response = osInput.downloadMonthlyReport(month);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("text/csv", response.getHeaders().getContentType().toString());
        assertEquals(csvContent, new String(response.getBody()));
    }
}