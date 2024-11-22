# 📖 **Pokedex App**

¡Bienvenido a la Pokedex App! Una aplicación interactiva para los fanáticos de Pokémon que permite explorar, gestionar y descubrir todo sobre tus Pokémon favoritos. 🚀✨

## 📝 **Descripción**

La **Pokedex App** es una herramienta para consultar información detallada de todos los Pokémon, incluyendo habilidades, estadísticas y cadenas de evolución. Además, los usuarios pueden personalizar su experiencia configurando sus Pokémon favoritos y eligiendo un avatar Pokémon desde la app.

Toda la información sobre los Pokémon es obtenida de [PokeAPI](https://pokeapi.co/), una API pública gratuita que provee datos sobre Pokémon. 🎉

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
- Ayuda interactiva para guiar al usuario en la creación de un **password seguro**.

---

## 🎨 **Diseño UI**

### 🔒 **Pantallas de Login y Creación de Usuario**
- Inspiradas en el siguiente diseño:
  *(TODO: Enlace o captura del diseño)*

### 🐾 **Pantallas de Detalles de Pokémon y Home Screen**
- Inspiradas en:
  *(TODO: Enlace o captura del diseño)*

### 👤 **Pantalla de Perfil**
- Inspirada en un diseño creado en Figma por el grupo:
  *(TODO: Enlace o captura del diseño)*

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
