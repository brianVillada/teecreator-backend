
import json
import sys

from reports.cotizaciones import obtener_reporte


# =====================================================
# 1. PUNTO DE ENTRADA DEL SISTEMA DE REPORTES
# =====================================================

def main():

    try:

        # Obtener los datos procesados por el modulo
        reporte = obtener_reporte()

        # Convertir el resultado a JSON
        resultado_json = json.dumps(
            reporte,
            ensure_ascii=False,
            allow_nan=False
        )

        # Devolver exclusivamente el JSON por stdout
        print(resultado_json)

    except Exception as error:

        # Los errores se envian por stderr
        print(
            f"Error al generar el reporte: {error}",
            file=sys.stderr
        )

        sys.exit(1)


# =====================================================
# 2. EJECUTAR EL PROGRAMA
# =====================================================

if __name__ == "__main__":
    main()
