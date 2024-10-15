# Usa una imagen base de Node.js
FROM node:14

# Crea el directorio de la aplicación
WORKDIR /app

# Copia los archivos del proyecto
COPY . .

# Instala las dependencias
RUN npm install

# Expone el puerto 3000
EXPOSE 3000

# Define el comando de inicio
CMD ["node", "server.js"]
