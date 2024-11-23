# 📖 **Pokedex App**

¡Bienvenido a la Pokedex App! Una aplicación interactiva para los fanáticos de Pokémon que permite
explorar, gestionar y descubrir todo sobre tus Pokémon favoritos. 🚀✨

## 📝 **Descripción**

La **Pokedex App** es una herramienta para consultar información detallada de todos los Pokémon,
incluyendo habilidades, estadísticas y cadenas de evolución. Además, los usuarios pueden
personalizar su experiencia configurando sus Pokémon favoritos y eligiendo un avatar Pokémon desde
la app.

Toda la información sobre los Pokémon es obtenida de [PokeAPI](https://pokeapi.co/), una API pública
gratuita que provee datos sobre Pokémon. 🎉

---

## ⚙️ **Funcionalidades Obligatorias**

### 🔑 **Flow de Autenticación**

- **Login de Usuario**: Permite a los usuarios autenticarse.
- **Creación de Usuario**: Los nuevos usuarios pueden registrarse fácilmente en la aplicación.

### 🏠 **Pantalla de Home**

- Se muestran los **primeros 20 Pokémon**, priorizando los favoritos al principio de la lista.

### 📜 **Pantalla de Todos los Pokémon**

- Un listado infinito de **todos los Pokémon** disponibles en PokeAPI.

### 🕵️ **Pantalla de Detalles del Pokémon**

- Muestra información detallada sobre:
    - **Habilidades y detalles**
    - **Estadísticas**
    - **Cadena de evolución**
- Acceso desde:
    - **Lista infinita de Pokémon**
    - **Pantalla de Home**
    - **Cadena de evolución**
- Funciones adicionales:
    - **Setear un Pokémon como favorito**.
    - **Setear un Pokémon como avatar de usuario**.

### 👤 **Pantalla de Perfil**

- Muestra:
    - **Información del usuario**.
    - **Avatar configurado**.
- Opción de realizar **sign-out**.

### 🛠️ **Arquitectura**

- Uso de **ViewModels** para la gestión de estado.
- Implementación del **Repository Pattern** para acceder a datos de Firebase y APIs.

---

## 🎁 **Funcionalidades Extra**

### 🔗 **Integraciones**

- **Firebase Auth**: Manejo de la autenticación de usuarios.
- **Firestore**: Gestión de favoritos y datos del usuario en la nube.

### ✅ **Validación Interactiva en Formularios**

- Validación interactiva en pantallas de login y creación de usuarios.
- Ayuda interactiva para guiar al usuario en la creación de un **password seguro**.

### 🔊 **Reproducción de los sonidos del pokemon en el detalle**

- Inclusión de botones que permiten al usuario reproducir el sonido legacy y el latest del pokemon.

### 🔎 **Implementar una pantalla de Search para buscar Pokemons**

- Inclusión de boton en Home que redirecciona a una pantalla de search.
- Pantalla de search con filtro para buscar por nombre.

### 🧩 Inyección de Dependencias

- Se configuró Hilt como el framework de inyección de dependencias para la aplicación.
- Se inyectaron las dependencias en todos los ViewModels utilizando Hilt.

### ☀️️ 🌑 Modo light y dark

- Se implementaron los colores y estilos para que la aplicación pueda cambiar entre light y dark
  mode.

---

## 🎨 **Diseño UI**

### 🔒 **Flow de Autenticación**

- Las pantallas de Login y Creación de Usuario se inspiran en el siguiente diseño de Dribbble:  
  [Login & Register Mobile App](https://dribbble.com/shots/15889044-Login-Register-Mobile-App)

### 🐾 **Pantallas de Detalles de Pokémon y Home Screen**

- Estas pantallas toman inspiración de este diseño en Dribbble:  
  [Pokedex App](https://dribbble.com/shots/6540871-Pokedex-App/attachments/6540871-Pokedex-App?mode=media)

### 👤 **Pantalla de Perfil**

- La pantalla de Perfil, así como algunos detalles adicionales de otras pantallas, están basados en
  este diseño en Figma:  
  [Pokedex - Desarrollo Android](https://www.figma.com/design/c1wb3eCjzQjX3FDmavvQf3/Pokedex---Desarrollo-Android?node-id=1-4&t=gmoKyC9WWx5rAbU4-1)

---

## 🚀 **Cómo Ejecutar la App**

1. Clona este repositorio:
   ```bash
   git clone https://github.com/tuusuario/pokedex-app.git
   cd pokedex-app
   ```
2. El repositorio incluye la configuración de firebse en `google-services.json`.
   Entendemos que no se debe guardar este tipo de archivos con claves dentro de un repositorio,
   se guardó simplemente para facilitar la ejecución del proyecto y que la corrección del mismo
   fuera más fácil.
3. Construye y ejecuta la app desde Android Studio
