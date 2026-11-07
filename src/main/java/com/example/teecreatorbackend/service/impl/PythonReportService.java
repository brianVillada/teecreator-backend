
package com.example.teecreatorbackend.service.impl;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

@Service
public class PythonReportService {

    private final JsonMapper jsonMapper;

    @Value("${teecreator.python.directory:python}")
    private String pythonDirectory;

    public PythonReportService(JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    // Generar el reporte JSON para el Dashboard.
    public JsonNode generarReporteCotizaciones() {

        Path directorio = Path.of(
                System.getProperty("user.dir"),
                pythonDirectory
        ).toAbsolutePath().normalize();

        Path ejecutablePython = directorio
                .resolve(".venv")
                .resolve("Scripts")
                .resolve("python.exe");

        Path archivoPrincipal = directorio.resolve("main.py");

        if (!Files.exists(ejecutablePython)) {
            throw new RuntimeException(
                    "No se encontro el ejecutable de Python: "
                            + ejecutablePython
            );
        }

        if (!Files.exists(archivoPrincipal)) {
            throw new RuntimeException(
                    "No se encontro main.py: " + archivoPrincipal
            );
        }

        try {

            ProcessBuilder processBuilder = new ProcessBuilder(
                    ejecutablePython.toString(),
                    archivoPrincipal.toString()
            );

            processBuilder.directory(directorio.toFile());
            processBuilder.redirectErrorStream(true);

            Process proceso = processBuilder.start();

            boolean finalizado = proceso.waitFor(
                    60,
                    TimeUnit.SECONDS
            );

            if (!finalizado) {
                proceso.destroyForcibly();
                throw new RuntimeException(
                        "Python excedio el tiempo maximo de ejecucion."
                );
            }

            String respuesta = new String(
                    proceso.getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8
            ).trim();

            if (proceso.exitValue() != 0) {
                throw new RuntimeException(
                        "Error ejecutando Python: " + respuesta
                );
            }

            return jsonMapper.readTree(respuesta);

        } catch (IOException error) {
            throw new RuntimeException(
                    "No fue posible ejecutar Python: "
                            + error.getMessage(),
                    error
            );
        } catch (InterruptedException error) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(
                    "La ejecucion de Python fue interrumpida.",
                    error
            );
        }
    }

    // Generar el PDF y devolverlo como bytes.
    public byte[] generarPdfCotizaciones() {

        Path directorio = Path.of(
                System.getProperty("user.dir"),
                pythonDirectory
        ).toAbsolutePath().normalize();

        Path ejecutablePython = directorio
                .resolve(".venv")
                .resolve("Scripts")
                .resolve("python.exe");

        Path archivoPdfPrincipal = directorio.resolve("pdf_main.py");

        if (!Files.exists(ejecutablePython)) {
            throw new RuntimeException(
                    "No se encontro el ejecutable de Python: "
                            + ejecutablePython
            );
        }

        if (!Files.exists(archivoPdfPrincipal)) {
            throw new RuntimeException(
                    "No se encontro pdf_main.py: " + archivoPdfPrincipal
            );
        }

        Path archivoTemporal = null;

        try {

            // Crear una ruta temporal para el PDF.
            archivoTemporal = Files.createTempFile(
                    "reporte_cotizaciones_",
                    ".pdf"
            );

            // Ejecutar Python enviandole la ruta del PDF.
            ProcessBuilder processBuilder = new ProcessBuilder(
                    ejecutablePython.toString(),
                    archivoPdfPrincipal.toString(),
                    archivoTemporal.toString()
            );

            processBuilder.directory(directorio.toFile());
            processBuilder.redirectErrorStream(true);

            Process proceso = processBuilder.start();

            boolean finalizado = proceso.waitFor(
                    60,
                    TimeUnit.SECONDS
            );

            if (!finalizado) {
                proceso.destroyForcibly();

                throw new RuntimeException(
                        "Python excedio el tiempo maximo de generacion del PDF."
                );
            }

            String respuesta = new String(
                    proceso.getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8
            ).trim();

            if (proceso.exitValue() != 0) {
                throw new RuntimeException(
                        "Error generando el PDF: " + respuesta
                );
            }

            // Leer el PDF generado.
            if (!Files.exists(archivoTemporal)
                    || Files.size(archivoTemporal) == 0) {

                throw new RuntimeException(
                        "Python no genero un archivo PDF valido."
                );
            }

            return Files.readAllBytes(archivoTemporal);

        } catch (IOException error) {

            throw new RuntimeException(
                    "No fue posible generar o leer el PDF: "
                            + error.getMessage(),
                    error
            );

        } catch (InterruptedException error) {

            Thread.currentThread().interrupt();

            throw new RuntimeException(
                    "La generacion del PDF fue interrumpida.",
                    error
            );

        } finally {

            // Eliminar el archivo temporal despues de leerlo.
            if (archivoTemporal != null) {
                try {
                    Files.deleteIfExists(archivoTemporal);
                } catch (IOException ignored) {
                    // No interrumpir la respuesta por este motivo.
                }
            }
        }
    }
}
