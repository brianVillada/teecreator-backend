
package com.example.teecreatorbackend.controller;

import com.example.teecreatorbackend.service.impl.PythonReportService;

import tools.jackson.databind.JsonNode;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "http://localhost:5173")
public class ReporteController {

    private final PythonReportService pythonReportService;

    public ReporteController(
            PythonReportService pythonReportService
    ) {
        this.pythonReportService = pythonReportService;
    }

    // Endpoint para consultar los datos del reporte.
    @GetMapping("/cotizaciones")
    public ResponseEntity<JsonNode> obtenerReporteCotizaciones() {

        JsonNode reporte =
                pythonReportService.generarReporteCotizaciones();

        return ResponseEntity.ok(reporte);
    }

    // Endpoint para descargar el reporte PDF.
    @GetMapping(
            value = "/cotizaciones/pdf",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<byte[]> descargarReporteCotizaciones() {

        byte[] pdf =
                pythonReportService.generarPdfCotizaciones();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"reporte_cotizaciones.pdf\""
                )
                .contentLength(pdf.length)
                .body(pdf);
    }
}
