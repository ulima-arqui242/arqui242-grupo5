@echo off

:: Ejecutar la colección de Postman
newman run "C:\Users\alfre\Documents\ARQUI DE SOFT\Coleccion.json" -e "C:\Users\alfre\Documents\ARQUI DE SOFT\Entorno.json" --reporters cli > "C:\Users\alfre\Documents\ARQUI DE SOFT\resultado_pruebas.log"
