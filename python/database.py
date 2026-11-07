
import os
from pathlib import Path

from dotenv import load_dotenv
from sqlalchemy import create_engine, text
from sqlalchemy.engine import URL


# =====================================================
# 1. CARGAR LAS VARIABLES DEL ARCHIVO .env
# =====================================================

CARPETA_ACTUAL = Path(__file__).resolve().parent

RUTA_ENV = CARPETA_ACTUAL / ".env"

load_dotenv(RUTA_ENV)


# =====================================================
# 2. OBTENER LAS CREDENCIALES DE MARIA DB
# =====================================================

DB_HOST = os.getenv("DB_HOST", "127.0.0.1")
DB_PORT = int(os.getenv("DB_PORT", "3306"))
DB_NAME = os.getenv("DB_NAME", "teecreator")
DB_USER = os.getenv("DB_USER", "root")
DB_PASSWORD = os.getenv("DB_PASSWORD", "")


# =====================================================
# 3. CONFIGURAR LA CONEXION
# =====================================================

DATABASE_URL = URL.create(
    drivername="mysql+pymysql",
    username=DB_USER,
    password=DB_PASSWORD,
    host=DB_HOST,
    port=DB_PORT,
    database=DB_NAME,
    query={"charset": "utf8mb4"}
)

engine = create_engine(
    DATABASE_URL,
    pool_pre_ping=True,
    echo=False
)


# =====================================================
# 4. FUNCION PARA PROBAR LA CONEXION
# =====================================================

def probar_conexion():

    with engine.connect() as conexion:

        resultado = conexion.execute(
            text("SELECT 1")
        )

        return resultado.scalar()


# =====================================================
# 5. EJECUCION DE LA PRUEBA
# =====================================================

if __name__ == "__main__":

    try:

        resultado = probar_conexion()

        if resultado == 1:

            print("Conexion exitosa con MariaDB.")
            print(f"Base de datos: {DB_NAME}")

    except Exception as error:

        print("Error al conectar con MariaDB:")
        print(error)
