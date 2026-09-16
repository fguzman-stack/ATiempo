# Contribuir a A Tiempo

1. Describe la mejora o el error en un issue, con versión de Android y pasos.
2. Trabaja en una rama con cambios enfocados y respeta los patrones de Compose.
3. Conserva los bloqueos de recompensas: 50 completaciones para temas e historial;
   100 para Galáctico. No introduzcas desbloqueos de prueba en el código distribuido.
4. Mantén las traducciones de los siete idiomas al cambiar textos visibles.
5. Ejecuta `./gradlew :app:testDebugUnitTest :app:lintDebug :app:assembleDebug`.
   En Windows utiliza `gradlew.bat`.
6. Incluye cómo verificaste el cambio y capturas sin información personal cuando
   afecte a la interfaz.

No subas claves de firma, `.env`, `local.properties`, configuraciones de servicios,
datos del dispositivo o salidas de compilación. Las nuevas dependencias y recursos
deben tener su origen y licencia documentados.

Las contribuciones de código se reciben bajo MIT. Los recursos externos conservan
sus licencias; aportar un archivo no implica que podamos relicenciarlo.
