# Recursos y dependencias

La licencia MIT de este proyecto cubre el código original. Las bibliotecas y los
recursos de terceros conservan sus propias licencias.

## Animaciones

El responsable del proyecto identifica **LottieFiles** como fuente de las cinco
animaciones incluidas en `app/src/main/assets/`:

- `compromiso_privacidad/Privacidad.json`
- `dashboard_vacio/lista.json`
- `diario/libro_diario.json`
- `inicio/relax-inicio.json`
- `tu_ritmo/Streak Fire.json`

Se incluye la [Lottie Simple License](docs/licenses/LOTTIE-SIMPLE-LICENSE.txt),
aplicable a las animaciones públicas distribuidas bajo esa licencia. No se
relicencian estas animaciones como MIT. Falta registrar el enlace individual y
autor de cada descarga para verificar que todas pertenecen a esa colección
pública y no a una colección premium con condiciones diferentes.

## Identidad visual y audio

El logo es una creación original aportada al proyecto. Su autoría no se publica
con datos personales. La licencia del código no concede por sí misma derechos
sobre la marca ni establece una licencia independiente del logo.

El archivo `app/src/main/res/raw/tononotificacion.mp3` es un recurso heredado sin
fuente documentada. La app utiliza el sonido predeterminado de Android; este
archivo se excluye de la distribución abierta y de la compilación.

## Bibliotecas principales

- AndroidX, Jetpack Compose, Room y Material Icons: Apache-2.0.
- Kotlin y kotlinx.coroutines: Apache-2.0.
- Lottie Android: Apache-2.0.
- JUnit: EPL-1.0; Robolectric: MIT (herramientas de pruebas).
- Gradle: Apache-2.0 (herramienta de compilación).

Las versiones se declaran en `gradle/libs.versions.toml`. Los avisos incorporados
por cada dependencia siguen siendo aplicables.
