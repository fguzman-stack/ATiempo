# Probar A Tiempo

## Comprobaciones automatizadas

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:lintDebug :app:assembleDebug
.\gradlew.bat :app:connectedDebugAndroidTest
```

La segunda orden requiere un dispositivo autorizado por ADB. Las pruebas de
recompensas cubren los umbrales 0/49/50/99/100, preferencias restauradas y borrado
de datos. Las reglas de completación se prueban ante duplicados, horarios futuros
y días de recurrencia no programados.

## Recorrido manual sugerido

- Completar bienvenida, seleccionar idioma y aceptar permisos.
- Crear una intención ficticia y comprobar su aparición en Inicio y Mi Agenda.
- Probar una notificación en primer plano y con la app cerrada.
- Completar desde la notificación y comprobar que suma una vez.
- Escribir, editar y eliminar una entrada ficticia del diario.
- Abrir Tu Ritmo: la actividad reciente es visible, el historial completo exige 50.
- Abrir Temas: los colores avanzados y Galáctico deben aparecer bloqueados al inicio.
- Reiniciar la app y comprobar que mantiene el progreso y los bloqueos.
- Probar modo oscuro y un segundo idioma.
- Comprobar widgets y restricciones de batería en el dispositivo de destino.

## Capturas

La edición de prueba usa datos separados de la instalación anterior. Con ella
abierta en primer plano y un único dispositivo conectado:

```powershell
python -m pip install Pillow
python scripts/device_preview.py dump
python scripts/device_preview.py capture --name dashboard
```

La captura se recorta para omitir barras del sistema. Revisa siempre las imágenes
antes de distribuirlas. No uses datos reales para ilustrar la documentación.
