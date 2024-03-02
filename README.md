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

##Pruebas
*Estas serian las pruebas para ver que funciona correctamente*
## Prueba 1 (Seno):

![image](https://github.com/JuanFe2001/Taller5-AREP/assets/123691538/7a7ebe7f-7e20-443f-864c-80e0f9d23fa2)

## Prueba 2 (Coseno):

![image](https://github.com/JuanFe2001/Taller5-AREP/assets/123691538/fb9625c4-ba71-49b8-a4e2-c268f134ae7a)

## Prueba 3 (Palindromo):

![image](https://github.com/JuanFe2001/Taller5-AREP/assets/123691538/d451fa6a-9cdf-441e-a14f-644cf31fe023)

## Prueba 4 (Magnitud del vector):

![image](https://github.com/JuanFe2001/Taller5-AREP/assets/123691538/e7c39745-0d53-4599-a7f9-970a3c98df05)


## Creacion de imagen web con Docker Hub
 - *Creamos la imagen en nuestro repositorio*
   ![image](https://github.com/JuanFe2001/Taller5-AREP/assets/123691538/57bfb667-61d1-4d11-be70-50766f74373d)

- *Luego iniciamos sesion en nuestra cuenta de Docker Hub*
  ![image](https://github.com/JuanFe2001/Taller5-AREP/assets/123691538/ce857e53-69af-4cd5-9d84-9112bbe33d59)

  - *Por ultimo subimos la imagen a nuestro repositorio*
    ![image](https://github.com/JuanFe2001/Taller5-AREP/assets/123691538/e8bc45f9-b768-4ddf-8f95-41aaa73d7651)


**Link del repositorio de Docker Hub**: https://hub.docker.com/repository/docker/juanfe1901/taller5/tags?page=1&ordering=last_updated

*con esto puede hacer el pull de la imagen y probar la pagina en su maquina*

    




