# Preparar una publicación pública

## Fuentes limpias

Ejecuta `python scripts/package_public.py` tras compilar. El script usa una lista
explícita de directorios y archivos permitidos, comprueba patrones de credenciales
y genera el ZIP, el APK y sus SHA-256 en `dist/`.

**Publica desde el ZIP limpio en un repositorio nuevo.** El árbol local de trabajo
original conserva historial privado y puede contener archivos locales ignorados.
Añadirlos a `.gitignore` no limpia los commits históricos. No publiques ese
historial ni comprimas la carpeta de trabajo completa.

El ZIP no contiene `.git`, `.idea`, `.env`, `local.properties`, claves de firma,
configuraciones de servicios, almacenes de anuncios ni APK anteriores. Revisa el
contenido antes de subirlo. El script es una comprobación adicional y no un
detector universal de secretos.

## Lanzamiento de prueba

Adjunta `a-tiempo-1.0-preview.apk`, `a-tiempo-source.zip` y `SHA256SUMS.txt` a una
release marcada como prerelease. Copia las notas de `CHANGELOG.md`. Las imágenes
del README usan rutas relativas y viajan dentro de las fuentes.

La preview utiliza una firma de desarrollo. Para distribuir actualizaciones de
producción, genera tu propia clave privada de release y conserva esa misma clave.
Exporta las cuatro variables descritas en `.env.example`; no publiques sus valores.
No se publican claves privadas en los artefactos.

## Procedencia de recursos

MIT está autorizado para el código. Completa los enlaces individuales de las
animaciones de LottieFiles y la autorización de distribución del logo en
`THIRD_PARTY_NOTICES.md`. La procedencia genérica de una plataforma no verifica
la licencia concreta de cada descarga.
