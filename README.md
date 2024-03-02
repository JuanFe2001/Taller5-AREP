## Escuela Colombiana de Ingeniería
### Arquitecturas Empresariales – AREP
# LAB5-AREP
#### TALLER 5: TALLER DE DE MODULARIZACIÓN CON VIRTUALIZACIÓN E INTRODUCCIÓN A DOCKER

El taller consiste en crear una aplicación web pequeña usando el micro-framework de Spark java (http://sparkjava.com/). Una vez tengamos esta aplicación procederemos a construir un container para docker para la aplicación y los desplegaremos y configuraremos en nuestra máquina local. Luego, cerremos un repositorio en DockerHub y subiremos la imagen al repositorio. Finalmente, crearemos una máquina virtual de en AWS, instalaremos Docker , y desplegaremos el contenedor que acabamos de crear.

##Elementos Necesarios
* Kit de desarrollo de Java (JDK) versión 11 o posterior
* Herramienta de construcción Maven
* Docker Desktop

## Pasos Para Ejecucion
**Primero creamos la imagen con el comando**
 docker build --tag dockersparkprimer .
 ![image](https://github.com/JuanFe2001/Taller5-AREP/assets/123691538/226fcf82-7df4-4598-a1f1-a695d4e1fa4e)
**Luego creamos el contenedor con el siguiente comando**
docker run -d -p 34000:46000 --name firstdockercontainer dockersparkprimer
![image](https://github.com/JuanFe2001/Taller5-AREP/assets/123691538/e43a4e39-627b-4806-9bc8-c978ec8fe9f1)
*Luego de esto probamos en el broswer con la siguiente url para verificar si si esta funcionando y corrriendo correctamente*
http://localhost:34000/calculadora
![image](https://github.com/JuanFe2001/Taller5-AREP/assets/123691538/32986eb2-98e6-4877-a1ea-3947098c4294)

