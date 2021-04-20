## UNQFlix 

UNQFlix es una plataforma de streaming de películas y series. La misma cuenta con
una aplicación de escritorio (desktop) para poder administrar el contenido de la aplicación,
una API RESTFul que da soporte y brinda la funcionalidad necesaria para la aplicación WEB
y una página web con las siguientes características:

* Posibilidad de _loguearse_ y registrar un usuario.
* Luego de que el usuario se encuentre _logueado_ puede ver los banners,
  sus contenidos recientemente vistos y los favoritos.
* En cualquier momento se puede hacer una búsqueda.
* Pueden verse los detalles de una película/serie/temporada.
* Se puede reproducir el link de una película/capitulo.
* Puede verse el catalogo completo de contenido.

### Información de Modelo

#### Película

- Estado (habilitada /deshabilitada)
- Descripción
- Poster (URL a la imágen)
- Título
- Video (URL al video)
- Tiempo de duración
- Lista de actores
- Lista de directores
- Lista de categorías (una categoría es un string)
- Contenido relacionado

#### Serie

- Estado (habilitada / deshabilitada)
- Descripción
- Poster (URL a la imágen)
- Título
- Temporadas
- Lista de categorías (una categoría es un string)
- Contenido relacionado

#### Temporada

- Descripción
- Nombre de la temporada (o número)
- Capítulos
- Poster (URL de la imágen)

#### Capítulo

- Descripción
- Nombre del capítulo
- Tiempo de duración
- Video (URL)
- Miniatura del capítulo (url de la imágen)

#### Usuario

- Nombre
- Correo electrónico (único, no editable)
- Contraseña