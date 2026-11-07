
import sys
from pathlib import Path

from reports.pdf_cotizaciones import generar_pdf_cotizaciones


def main():
    try:
        # Recibir la ruta donde se guardara el PDF.
        if len(sys.argv) < 2:
            raise ValueError(
                "Debes indicar la ruta de salida del PDF."
            )

        ruta_salida = Path(sys.argv[1])

        # Crear el directorio si todavia no existe.
        ruta_salida.parent.mkdir(
            parents=True,
            exist_ok=True
        )

        # Generar el documento PDF.
        generar_pdf_cotizaciones(ruta_salida)

        print(str(ruta_salida.resolve()))

    except Exception as error:
        print(
            f"Error al generar el PDF: {error}",
            file=sys.stderr
        )
        sys.exit(1)


if __name__ == "__main__":
    main()
