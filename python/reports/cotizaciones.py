
import pandas as pd

from sqlalchemy import text

from database import engine


# =====================================================
# 1. CONSULTAR LAS COTIZACIONES
# =====================================================

def consultar_cotizaciones():

    consulta = text("""
        SELECT
            c.id,
            c.usuario_id,
            c.pedido_id,
            c.producto_id,
            p.nombre AS nombre_producto,
            c.cantidad,
            c.ancho_cm,
            c.alto_cm,
            c.area_cm2,
            c.precio_camiseta,
            c.precio_estampacion,
            c.precio_total,
            c.fecha
        FROM cotizaciones c
        INNER JOIN productos p
            ON c.producto_id = p.id
        ORDER BY c.fecha ASC
    """)

    with engine.connect() as conexion:
        df = pd.read_sql(consulta, conexion)

    return df


# =====================================================
# 2. CALCULAR INDICADORES GENERALES
# =====================================================

def calcular_indicadores(df):

    if df.empty:
        return {
            "total_cotizado": 0,
            "numero_cotizaciones": 0,
            "unidades_cotizadas": 0,
            "ticket_promedio": 0,
            "area_total_cm2": 0
        }

    total_cotizado = float(df["precio_total"].sum())

    numero_cotizaciones = int(len(df))

    unidades_cotizadas = int(df["cantidad"].sum())

    ticket_promedio = (
        total_cotizado / numero_cotizaciones
        if numero_cotizaciones > 0
        else 0
    )

    area_total_cm2 = float(
        (df["area_cm2"] * df["cantidad"]).sum()
    )

    return {
        "total_cotizado": round(total_cotizado, 2),
        "numero_cotizaciones": numero_cotizaciones,
        "unidades_cotizadas": unidades_cotizadas,
        "ticket_promedio": round(ticket_promedio, 2),
        "area_total_cm2": round(area_total_cm2, 2)
    }


# =====================================================
# 3. AGRUPAR COTIZACIONES POR FECHA
# =====================================================

def agrupar_por_fecha(df):

    if df.empty:
        return []

    df = df.copy()

    df["fecha"] = pd.to_datetime(df["fecha"])

    agrupacion = (
        df.groupby(df["fecha"].dt.date)
        .agg(
            total=("precio_total", "sum"),
            cotizaciones=("id", "count"),
            unidades=("cantidad", "sum")
        )
        .reset_index()
    )

    resultado = []

    for _, fila in agrupacion.iterrows():

        resultado.append({
            "fecha": fila["fecha"].strftime("%Y-%m-%d"),
            "total": round(float(fila["total"]), 2),
            "cotizaciones": int(fila["cotizaciones"]),
            "unidades": int(fila["unidades"])
        })

    return resultado


# =====================================================
# 4. AGRUPAR COTIZACIONES POR PRODUCTO
# =====================================================

def agrupar_por_producto(df):

    if df.empty:
        return []

    agrupacion = (
        df.groupby(["producto_id", "nombre_producto"])
        .agg(
            cotizaciones=("id", "count"),
            unidades=("cantidad", "sum"),
            total=("precio_total", "sum")
        )
        .reset_index()
        .sort_values(by="total", ascending=False)
    )

    resultado = []

    for _, fila in agrupacion.iterrows():

        resultado.append({
            "producto_id": int(fila["producto_id"]),
            "producto": str(fila["nombre_producto"]),
            "cotizaciones": int(fila["cotizaciones"]),
            "unidades": int(fila["unidades"]),
            "total": round(float(fila["total"]), 2)
        })

    return resultado


# =====================================================
# 5. GENERAR EL REPORTE COMPLETO
# =====================================================

def obtener_reporte():

    df = consultar_cotizaciones()

    indicadores = calcular_indicadores(df)

    por_fecha = agrupar_por_fecha(df)

    por_producto = agrupar_por_producto(df)

    return {
        "indicadores": indicadores,
        "por_fecha": por_fecha,
        "por_producto": por_producto
    }


# =====================================================
# 6. PRUEBA DEL MODULO
# =====================================================

if __name__ == "__main__":

    import json

    try:

        reporte = obtener_reporte()

        print(
            json.dumps(
                reporte,
                indent=4,
                ensure_ascii=False
            )
        )

    except Exception as error:

        print("Error al generar el reporte:")
        print(error)
