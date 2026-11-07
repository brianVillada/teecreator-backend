
from datetime import datetime
from pathlib import Path
from io import BytesIO

import matplotlib
matplotlib.use("Agg")

import matplotlib.pyplot as plt

from reportlab.lib import colors
from reportlab.lib.enums import TA_CENTER
from reportlab.lib.pagesizes import A4
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.lib.units import cm

from reportlab.platypus import (
    SimpleDocTemplate,
    Paragraph,
    Spacer,
    Table,
    TableStyle,
    Image as ReportLabImage
)

from reports.cotizaciones import obtener_reporte


# =====================================================
# FUNCIONES DE FORMATO
# =====================================================

def formato_moneda(valor):
    return f"$ {valor:,.0f}".replace(",", ".")


def formato_numero(valor):
    return f"{valor:,.0f}".replace(",", ".")


# =====================================================
# GRAFICO DE TORTA
# Participacion del valor cotizado por producto
# =====================================================

def generar_grafico_torta(por_producto):

    productos = [
        item["producto"]
        for item in por_producto
    ]

    valores = [
        item["total"]
        for item in por_producto
    ]

    # Colores para camisetas blancas y negras
    colores_grafico = []

    for producto in productos:

        if "blanca" in producto.lower():
            colores_grafico.append("#d1d5db")

        elif "negra" in producto.lower():
            colores_grafico.append("#374151")

        else:
            colores_grafico.append("#2563eb")

    figura, eje = plt.subplots(figsize=(6, 4))

    if valores and sum(valores) > 0:

        sectores, textos, porcentajes = eje.pie(
            valores,
            colors=colores_grafico,
            startangle=90,
            autopct="%1.1f%%",
            pctdistance=0.65,
            wedgeprops={
                "edgecolor": "white",
                "linewidth": 2
            }
        )

        # Personalizar porcentajes
        for i, porcentaje in enumerate(porcentajes):

            porcentaje.set_fontsize(10)
            porcentaje.set_fontweight("bold")

            if colores_grafico[i] == "#374151":
                porcentaje.set_color("white")
            else:
                porcentaje.set_color("#1f2937")

        # Leyenda
        eje.legend(
            sectores,
            productos,
            title="Productos",
            loc="lower center",
            bbox_to_anchor=(0.5, -0.15),
            frameon=False,
            fontsize=8
        )

    else:

        eje.text(
            0.5,
            0.5,
            "Sin datos disponibles",
            horizontalalignment="center",
            verticalalignment="center"
        )

    eje.set_title(
        "Participacion del valor cotizado",
        fontsize=12,
        fontweight="bold"
    )

    figura.tight_layout()

    # Guardar grafico en memoria
    imagen = BytesIO()

    figura.savefig(
        imagen,
        format="png",
        dpi=180,
        bbox_inches="tight"
    )

    plt.close(figura)

    imagen.seek(0)

    return imagen


# =====================================================
# GRAFICO DE BARRAS
# Unidades cotizadas por producto
# =====================================================

def generar_grafico_barras(por_producto):

    productos = [
        item["producto"]
        for item in por_producto
    ]

    unidades = [
        item["unidades"]
        for item in por_producto
    ]

    colores_grafico = []

    for producto in productos:

        if "blanca" in producto.lower():
            colores_grafico.append("#d1d5db")

        elif "negra" in producto.lower():
            colores_grafico.append("#374151")

        else:
            colores_grafico.append("#2563eb")

    figura, eje = plt.subplots(figsize=(6, 4))

    if productos:

        barras = eje.bar(
            productos,
            unidades,
            color=colores_grafico,
            edgecolor="#64748b",
            width=0.55
        )

        # Mostrar cantidades sobre las barras
        eje.bar_label(
            barras,
            padding=4,
            fontweight="bold"
        )

        eje.set_ylim(
            0,
            max(unidades, default=0) * 1.3 + 1
        )

        eje.set_ylabel("Unidades")

        eje.grid(
            axis="y",
            linestyle="--",
            alpha=0.3
        )

        eje.set_axisbelow(True)

        eje.spines["top"].set_visible(False)
        eje.spines["right"].set_visible(False)

        eje.tick_params(
            axis="x",
            labelsize=8
        )

    else:

        eje.text(
            0.5,
            0.5,
            "Sin datos disponibles",
            horizontalalignment="center",
            verticalalignment="center"
        )

    eje.set_title(
        "Unidades cotizadas por producto",
        fontsize=12,
        fontweight="bold"
    )

    figura.tight_layout()

    # Guardar grafico en memoria
    imagen = BytesIO()

    figura.savefig(
        imagen,
        format="png",
        dpi=180,
        bbox_inches="tight"
    )

    plt.close(figura)

    imagen.seek(0)

    return imagen


# =====================================================
# GENERACION DEL DOCUMENTO PDF
# =====================================================

def generar_pdf_cotizaciones(ruta_salida):

    # Obtener informacion desde MariaDB
    reporte = obtener_reporte()

    indicadores = reporte["indicadores"]

    por_fecha = reporte["por_fecha"]

    por_producto = reporte["por_producto"]

    # Crear documento
    documento = SimpleDocTemplate(
        str(ruta_salida),
        pagesize=A4,
        rightMargin=1.5 * cm,
        leftMargin=1.5 * cm,
        topMargin=1.5 * cm,
        bottomMargin=1.5 * cm
    )

    # Estilos
    estilos = getSampleStyleSheet()

    titulo = ParagraphStyle(
        "TituloPersonalizado",
        parent=estilos["Title"],
        fontSize=22,
        textColor=colors.HexColor("#1e293b"),
        alignment=TA_CENTER,
        spaceAfter=8
    )

    subtitulo = ParagraphStyle(
        "SubtituloPersonalizado",
        parent=estilos["Normal"],
        fontSize=10,
        textColor=colors.HexColor("#64748b"),
        alignment=TA_CENTER,
        spaceAfter=20
    )

    encabezado = ParagraphStyle(
        "EncabezadoPersonalizado",
        parent=estilos["Heading2"],
        fontSize=13,
        textColor=colors.HexColor("#1e293b"),
        spaceBefore=18,
        spaceAfter=10
    )

    texto = ParagraphStyle(
        "TextoPersonalizado",
        parent=estilos["Normal"],
        fontSize=9,
        leading=13
    )

    contenido = []

    # -------------------------------------------------
    # ENCABEZADO
    # -------------------------------------------------

    contenido.append(
        Paragraph("TeeCreator", titulo)
    )

    contenido.append(
        Paragraph(
            "Reporte de cotizaciones",
            subtitulo
        )
    )

    fecha_generacion = datetime.now().strftime(
        "%d/%m/%Y %H:%M"
    )

    contenido.append(
        Paragraph(
            f"<b>Fecha de generacion:</b> {fecha_generacion}",
            texto
        )
    )

    contenido.append(Spacer(1, 20))

    # -------------------------------------------------
    # INDICADORES GENERALES
    # -------------------------------------------------

    contenido.append(
        Paragraph(
            "Indicadores generales",
            encabezado
        )
    )

    datos_indicadores = [
        ["Indicador", "Resultado"],

        [
            "Total cotizado",
            formato_moneda(
                indicadores["total_cotizado"]
            )
        ],

        [
            "Numero de cotizaciones",
            formato_numero(
                indicadores["numero_cotizaciones"]
            )
        ],

        [
            "Unidades cotizadas",
            formato_numero(
                indicadores["unidades_cotizadas"]
            )
        ],

        [
            "Ticket promedio",
            formato_moneda(
                indicadores["ticket_promedio"]
            )
        ],

        [
            "Area total de estampado",
            f'{formato_numero(indicadores["area_total_cm2"])} cm2'
        ]
    ]

    tabla_indicadores = Table(
        datos_indicadores,
        colWidths=[
            9 * cm,
            8 * cm
        ]
    )

    tabla_indicadores.setStyle(
        TableStyle([

            ("BACKGROUND", (0, 0), (-1, 0),
             colors.HexColor("#1e40af")),

            ("TEXTCOLOR", (0, 0), (-1, 0),
             colors.white),

            ("FONTNAME", (0, 0), (-1, 0),
             "Helvetica-Bold"),

            ("FONTNAME", (0, 1), (-1, -1),
             "Helvetica"),

            ("FONTSIZE", (0, 0), (-1, -1), 9),

            ("GRID", (0, 0), (-1, -1),
             0.5, colors.HexColor("#cbd5e1")),

            ("BACKGROUND", (0, 1), (-1, -1),
             colors.HexColor("#f8fafc")),

            ("ALIGN", (1, 1), (1, -1), "RIGHT"),

            ("TOPPADDING", (0, 0), (-1, -1), 9),

            ("BOTTOMPADDING", (0, 0), (-1, -1), 9),

            ("LEFTPADDING", (0, 0), (-1, -1), 10),

            ("RIGHTPADDING", (0, 0), (-1, -1), 10)

        ])
    )

    contenido.append(tabla_indicadores)

    # -------------------------------------------------
    # ANALISIS GRAFICO
    # -------------------------------------------------

    contenido.append(
        Paragraph(
            "Analisis grafico",
            encabezado
        )
    )

    grafico_torta = generar_grafico_torta(
        por_producto
    )

    grafico_barras = generar_grafico_barras(
        por_producto
    )

    imagen_torta = ReportLabImage(
        grafico_torta,
        width=8.2 * cm,
        height=5.5 * cm
    )

    imagen_barras = ReportLabImage(
        grafico_barras,
        width=8.2 * cm,
        height=5.5 * cm
    )

    tabla_graficos = Table(
        [[
            imagen_torta,
            imagen_barras
        ]],
        colWidths=[
            8.7 * cm,
            8.7 * cm
        ]
    )

    tabla_graficos.setStyle(
        TableStyle([

            ("VALIGN", (0, 0), (-1, -1), "MIDDLE"),

            ("ALIGN", (0, 0), (-1, -1), "CENTER"),

            ("LEFTPADDING", (0, 0), (-1, -1), 2),

            ("RIGHTPADDING", (0, 0), (-1, -1), 2),

            ("TOPPADDING", (0, 0), (-1, -1), 5),

            ("BOTTOMPADDING", (0, 0), (-1, -1), 5)

        ])
    )

    contenido.append(tabla_graficos)

    contenido.append(Spacer(1, 15))

    # -------------------------------------------------
    # RENDIMIENTO POR PRODUCTO
    # -------------------------------------------------

    contenido.append(
        Paragraph(
            "Rendimiento por producto",
            encabezado
        )
    )

    datos_productos = [[
        "Producto",
        "Cotizaciones",
        "Unidades",
        "Participacion",
        "Total cotizado"
    ]]

    total_cotizado = indicadores["total_cotizado"]

    for producto in por_producto:

        participacion = (
            producto["total"] / total_cotizado * 100
            if total_cotizado > 0 else 0
        )

        datos_productos.append([

            producto["producto"],

            formato_numero(
                producto["cotizaciones"]
            ),

            formato_numero(
                producto["unidades"]
            ),

            f"{participacion:.2f}%",

            formato_moneda(
                producto["total"]
            )

        ])

    tabla_productos = Table(
        datos_productos,
        colWidths=[
            5.2 * cm,
            2.3 * cm,
            1.8 * cm,
            2.3 * cm,
            5.4 * cm
        ],
        repeatRows=1
    )

    tabla_productos.setStyle(
        TableStyle([

            ("BACKGROUND", (0, 0), (-1, 0),
             colors.HexColor("#1e40af")),

            ("TEXTCOLOR", (0, 0), (-1, 0),
             colors.white),

            ("FONTNAME", (0, 0), (-1, 0),
             "Helvetica-Bold"),

            ("FONTSIZE", (0, 0), (-1, -1), 7),

            ("GRID", (0, 0), (-1, -1),
             0.5, colors.HexColor("#cbd5e1")),

            ("BACKGROUND", (0, 1), (-1, -1),
             colors.HexColor("#f8fafc")),

            ("ALIGN", (1, 1), (-1, -1), "RIGHT"),

            ("VALIGN", (0, 0), (-1, -1), "MIDDLE"),

            ("TOPPADDING", (0, 0), (-1, -1), 8),

            ("BOTTOMPADDING", (0, 0), (-1, -1), 8)

        ])
    )

    contenido.append(tabla_productos)

    # -------------------------------------------------
    # HISTORIAL POR FECHA
    # -------------------------------------------------

    contenido.append(
        Paragraph(
            "Historial de cotizaciones",
            encabezado
        )
    )

    datos_fechas = [[
        "Fecha",
        "Cotizaciones",
        "Unidades",
        "Total cotizado"
    ]]

    for item in por_fecha:

        fecha = datetime.strptime(
            item["fecha"],
            "%Y-%m-%d"
        ).strftime("%d/%m/%Y")

        datos_fechas.append([

            fecha,

            formato_numero(
                item["cotizaciones"]
            ),

            formato_numero(
                item["unidades"]
            ),

            formato_moneda(
                item["total"]
            )

        ])

    tabla_fechas = Table(
        datos_fechas,
        colWidths=[
            4 * cm,
            3.5 * cm,
            3 * cm,
            6.5 * cm
        ],
        repeatRows=1
    )

    tabla_fechas.setStyle(
        TableStyle([

            ("BACKGROUND", (0, 0), (-1, 0),
             colors.HexColor("#1e40af")),

            ("TEXTCOLOR", (0, 0), (-1, 0),
             colors.white),

            ("FONTNAME", (0, 0), (-1, 0),
             "Helvetica-Bold"),

            ("FONTSIZE", (0, 0), (-1, -1), 8),

            ("GRID", (0, 0), (-1, -1),
             0.5, colors.HexColor("#cbd5e1")),

            ("BACKGROUND", (0, 1), (-1, -1),
             colors.HexColor("#f8fafc")),

            ("ALIGN", (1, 1), (-1, -1), "RIGHT"),

            ("VALIGN", (0, 0), (-1, -1), "MIDDLE"),

            ("TOPPADDING", (0, 0), (-1, -1), 8),

            ("BOTTOMPADDING", (0, 0), (-1, -1), 8)

        ])
    )

    contenido.append(tabla_fechas)

    # -------------------------------------------------
    # PIE DE PAGINA
    # -------------------------------------------------

    def agregar_pie(canvas, documento):

        canvas.saveState()

        canvas.setFont(
            "Helvetica",
            8
        )

        canvas.setFillColor(
            colors.HexColor("#64748b")
        )

        canvas.drawString(
            1.5 * cm,
            1 * cm,
            "TeeCreator - Reporte de cotizaciones"
        )

        canvas.drawRightString(
            A4[0] - 1.5 * cm,
            1 * cm,
            f"Pagina {documento.page}"
        )

        canvas.restoreState()

    # Generar PDF
    documento.build(
        contenido,
        onFirstPage=agregar_pie,
        onLaterPages=agregar_pie
    )

    return str(ruta_salida)


# =====================================================
# EJECUCION DIRECTA PARA PRUEBAS
# =====================================================

if __name__ == "__main__":

    ruta = (
        Path(__file__).resolve().parent
        / "reporte_cotizaciones.pdf"
    )

    try:

        generar_pdf_cotizaciones(ruta)

        print(
            f"PDF generado correctamente: {ruta}"
        )

    except Exception as error:

        print("Error al generar el PDF:")
        print(error)

        raise
