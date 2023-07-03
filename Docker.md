## Curso de desarrollo de software 

### Introducción a Docker  

Completa todos los pasos, para futuras actividades. En esta actividad analizaremos cómo se ve el proceso moderno de entrega continua (CD) al presentar Docker, la tecnología que cambió 
la industria de la tecnología de la información (TI) y la forma en que se utilizan los servidores.  

#### Requerimientos técnicos  

Para completar esta actividad, debes cumplir con los siguientes requisitos de hardware/software:  

- Al menos 4 gigabytes (GB) de memoria de acceso aleatorio (RAM)
- macOS 10.15+, Windows 10/11 Pro de 64 bits, Ubuntu 20.04+ u otro Linux  

### ¿Qué es Docker?  
 
Docker es un proyecto de código abierto diseñado para ayudar con la implementación de aplicaciones utilizando contenedores de software. 
Este enfoque significa ejecutar aplicaciones junto con el entorno completo (archivos, librerías de códigos, herramientas, etc.). 

Por lo tanto, Docker, similar a la virtualización, permite empaquetar una aplicación en una imagen que se puede ejecutar en todas partes.  

#### Instalación de Docker  

El proceso de instalación de Docker es rápido y sencillo. Actualmente, es compatible con la mayoría de los sistemas operativos Linux y una amplia gama de ellos 
tienen binarios dedicados proporcionados. 

Mac OS y Windows también son compatibles con aplicaciones nativas. Sin embargo, es importante comprender que Docker está basado internamente en el kernel de Linux 
y sus especificaciones, y es por eso que, en el caso de macOS y Windows, utiliza máquinas virtuales (HyperKit para macOS e Hyper-V para Windows) para ejecutar el entorno del motor Docker.  

#### Requisitos previos para Docker  

Los requisitos de Docker Community Edition son específicos para cada sistema operativo, como se describe aquí: 

Mac OS:  
- macOS 10.15 o posterior
- Al menos 4GB de RAM
- VirtualBox anterior a la versión 4.3.30 instalada  

Windows:  
- Windows 10/11 de 64 bits
- El paquete Hyper-V habilitado
- Al menos 4 GB de RAM
Linux:

- Arquitectura de 64 bits
- Núcleo de Linux 3.10 o posterior  

Si tu máquina no cumple con estos requisitos, la solución es usar VirtualBox con el sistema operativo Ubuntu instalado. 
Esta solución, aunque suene complicada, no es necesariamente el peor método, especialmente teniendo en cuenta que el entorno de Docker Engine está virtualizado
de todos modos en el caso de macOS y Windows. Además, Ubuntu es uno de los sistemas con mejor soporte para usar Docker.  

**Información:** He probado todo el laboratorio con el sistema operativo Linux Mint 22.04.  

#### Instalación en una máquina local  

El proceso de instalación de Docker en Linux es sencillo y se describe en detalle en la página oficial: https://docs.docker.com/engine/install/ubuntu/.  

#### Escritorio Docker 

La forma más sencilla de usar Docker en tu entorno local es instalar [Docker Desktop](https://www.docker.com/products/docker-desktop/). 
De esta manera, en solo unos minutos, tendrá un entorno de desarrollo de Docker completo, todo configurado y en funcionamiento. 
Para los usuarios de Windows y macOS, Docker Desktop proporciona una aplicación nativa que oculta todas las dificultades de configuración.

Técnicamente, Docker Engine se instala dentro de una máquina virtual porque Docker requiere el kernel de Linux para funcionar. 
Sin embargo, como usuario, ni siquiera necesitas pensar en esto: instala Docker Desktop y estás listo para comenzar a usar los comandos de docker.  

Además de Docker Engine, Docker Desktop proporciona una serie de funciones adicionales, como se indica a continuación:  

- Una interfaz de usuario (UI) para mostrar imágenes, contenedores y volúmenes
- Un clúster local de Kubernetes
- Actualizaciones automáticas de Docker
- Montaje de volumen con la integración del sistema de archivos local
- (Windows) Compatibilidad con contenedores de Windows
- (Windows) Integración con el subsistema de Windows para Linux (WSL)/WSL versión 2 (WSL2) 

#### Docker para Ubuntu  

Visita https://docs.docker.com/engine/install/ubuntu/ para encontrar una guía sobre cómo instalar Docker en una máquina con Ubuntu.  

En el caso de Linux se ha ejecutado los siguientes comandos: 

``` 
$ sudo apt-get update 
$ sudo apt-get -y install ca-certificates curl gnupg  lsb-release 
$ curl -fsSL https://download.docker.com/linux/ubuntu/gpg 
  | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive- keyring.gpg 

$ echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/ 
  share/keyrings/docker-archive-keyring.gpg] https://download.
  docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee / 
  etc/apt/sources.list.d/docker.list > /dev/null 
$ sudo apt-get update 
$ sudo apt-get -y install docker-ce docker-ce-cli containerd.io
```

Una vez completadas todas las operaciones, se debe instalar Docker. Sin embargo, por el momento, el único usuario que puede usar los comandos de Docker es  el `root`. 
Esto significa que la palabra clave sudo debe preceder a cada comando de Docker.  

Podemos permitir que otros usuarios usen Docker agregándolos al grupo docker, de la siguiente manera: 

```
$ sudo usermod -aG docker <username>  
``` 
 
Después de un cierre de sesión exitoso, todo está configurado. Sin embargo, con el último comando, debemos tomar algunas precauciones para no otorgar permisos de Docker a un usuario 
no deseado y, por lo tanto, crear una vulnerabilidad en el entorno de Docker Engine. Esto es particularmente importante en el caso de la instalación en la máquina del servidor.  

#### Docker para otras distribuciones de Linux  

Docker es compatible con la mayoría de las distribuciones y arquitecturas de Linux. Para obtener más información, consulta la página oficial en https://docs.docker.com/engine/install/.  

#### Probando la instalación de Docker  

Independientemente de la instalación que hayas elegido (macOS, Windows, Ubuntu, Linux u otra), Docker debe estar configurado y listo. 
La mejor manera de probarlo es ejecutar el comando `docker info`. El mensaje de salida debe ser similar al siguiente:  

```
$ docker info 
Containers: 0 
Running: 0 
Paused: 0 
Stopped: 0 
Images: 0 
... 
``` 

### Ejecutando Docker hello-world  

Ingresa el siguiente comando en su consola:  

 
```
$ docker run hello-world 
Unable to find image 'hello-world:latest' locally 
latest: Pulling from library/hello-world 
1b930d010525: Pull complete 
Digest: sha256:2557e3c07ed1e38f26e389462d03ed- 
943586f744621577a99efb77324b0fe535 
Status: Downloaded newer image for hello-world:latest 
Hello from Docker! 
This message shows that your installation appears to be working 
correctly. 
...
``` 

Acabas de ejecutar tu primer contenedor Docker. Examinemos lo que sucedió en este procedimiento de la siguiente manera:  

1. Se ejecutó el cliente de Docker con el comando de ejecución.  
2. El cliente de Docker se puso en contacto con el demonio de Docker y le pidió que creara un contenedor a partir de la imagen llamado `hello-world`.  
3. El demonio Docker verificó si contenía la imagen `hello-world` local y, como no lo hizo, solicitó la imagen `hello-world` del registro remoto de Docker Hub.  
4. El registro de Docker Hub contenía la imagen `hello-world` por lo que se introdujo en el demonio de Docker.  
5. El demonio de Docker creó un nuevo contenedor a partir de la imagen `hello-world` que inició el ejecutable y produjo la salida.  
6. El demonio de Docker transmitió esta salida al cliente de Docker.  
7. El cliente Docker lo envió al Terminal.

Veamos ahora cada componente de Docker que se ilustró en esta sección.

### Componentes de Docker

Docker es en realidad un ecosistema que incluye una serie de componentes. Vamos a describirlos todos, comenzando con una mirada más cercana a la arquitectura cliente-servidor de Docker.

#### Docker cliente y servidor

Veamos el siguiente diagrama, que presenta la arquitectura del motor Docker:

<img src="Imagenes/Arquitectura-docker.png" width="500px" height="400px">

Docker Engine consta de los siguientes tres componentes:

- Un demonio Docker (servidor) ejecutándose en segundo plano
- Un Docker Client que se ejecuta como una herramienta de comando
- Una interfaz de programación de aplicaciones (API) de transferencia de estado representacional (REST) de Docker

Instalar Docker significa instalar todos los componentes para que el demonio Docker se ejecute en un equipo todo el tiempo como un servicio.
En el caso del ejemplo de `hello-world`, usamos el cliente de Docker para interactuar con el demonio de Docker, sin embargo, podríamos hacer exactamente lo mismo usando la API REST.

Además, en el caso del ejemplo de `hello-world` nos conectamos al demonio Docker local. Sin embargo, podríamos usar el mismo cliente para interactuar con el demonio Docker que se ejecuta en una máquina remota.  

Para ejecutar el contenedor Docker en una máquina remota, puedes usar la opción -H:  `docker -H <server_ip>:2375 run hello-world`.  


#### Imágenes y contenedores Docker  

Una imagen es un bloque de construcción sin estado (stateless) en el mundo de Docker. Puedes pensar en una imagen como una colección de todos los archivos necesarios para ejecutar tu aplicación, junto con los pasos sobre cómo ejecutarla. 

Una imagen no tiene estado, por lo que puedes enviarla a través de la red, almacenarla en un registro, nombrarla, versionarla y guardarla como un archivo. 

Las imágenes están en capas, lo que significa que puedes construir una imagen encima de otra imagen.  

 <img src="Imagenes/Capa-imagenes.png" width="280px" height="230px">

Un contenedor es una instancia en ejecución de una imagen. Podemos crear muchos contenedores a partir de una misma imagen si queremos tener muchas instancias de la misma aplicación. 

Dado que los contenedores tienen estado (stateful), esto significa que podemos interactuar con ellos y realizar cambios en sus estados.  

 ### Aplicaciones docker 

Se proporcionan muchas aplicaciones en forma de imágenes de Docker que se pueden descargar de Internet. 

Si conocemos el nombre de la imagen, bastaría con ejecutar de la misma forma que hicimos con el ejemplo de `hello-world`. ¿Cómo podemos encontrar la imagen de la aplicación deseada en Docker Hub?  

Tomemos MongoDB como ejemplo. Estos son los pasos que debemos seguir:  

1. Si queremos encontrarlo en Docker Hub, tenemos dos opciones, de la siguiente manera:
   - Busca en la página Explorar de Docker Hub (https://hub.docker.com/search/).
   - Utiliza el comando `docker search`.  

   En el segundo caso, podemos realizar la siguiente operación: `docker search mongo` 

2. Hay muchas opciones interesantes. ¿Cómo elegimos la mejor imagen? Por lo general, la más atractiva es el que no tiene ningún prefijo, ya que significa que es una imagen oficial de
 Docker Hub y por lo tanto, debe ser estable y tiene manteniemiento. Las imágenes con prefijos no son oficiales, generalmente se mantienen como proyectos de código abierto.
En este caso, la mejor opción parece ser `mongo` por lo que para ejecutar el servidor `MongoDB`, podemos ejecutar el siguiente comando:   `docker run mongo` . 

Eso es todo lo que tenemos que hacer. MongoDB ha comenzado.  

Ejecutar aplicaciones como contenedores Docker es así de simple porque no necesitamos pensar en ninguna dependencia; todos se entregan junto con la imagen. 

Docker puede tratarse como una herramienta útil para ejecutar aplicaciones; sin embargo, el verdadero poder radica en crear tus propias imágenes de Docker que envuelvan los programas con el entorno.  

### Creación de imágenes de Docker  

 En esta sección, veremos cómo crear imágenes de Docker utilizando dos métodos diferentes: 

#### docker commit 

Comencemos con un ejemplo y preparemos una imagen con los kits de herramientas de Git y JDK. Usaremos Ubuntu 20.04 como imagen base. 
No hay necesidad de crearlo la mayoría de las imágenes base están disponibles en el registro de Docker Hub. Procede de la siguiente forma:  


1. Ejecuta un contenedor desde ubuntu:20.04 y conéctelo a tu línea de comando, así: `docker run -i -t ubuntu:20.04 /bin/bash` 

2. Instala el kit de herramientas de Git de la siguiente manera:

   ```
   # apt-get update
   # apt-get install -y git
   ```
3. Comprueba si el kit de herramientas de Git está instalado ejecutando lo siguiente:

   ```
   # which git
   /usr/bin/git 
   ```
4. Sal del contenedor, así:  
    ```
     root@dee2cb192c6c:/# exit 
      ```
5. Verifica qué ha cambiado en el contenedor comparando su identificador (ID)  con la imagen de ubuntu, de la siguiente manera: `docker diff dee2cb192c6c`. 
 
  El comando anterior debe imprimir una lista de todos los archivos modificados en el contenedor.  


6. Commit el contenedor a la imagen, así: `docker commit dee2cb192c6c ubuntu_con_git` 


Acabamos de crear una primera imagen de Docker. Enumeremos todas las imágenes del host Docker para ver si la imagen está presente, de la siguiente manera: 

```
docker images 
```
 
Ahora, si creamos un contenedor a partir de la imagen, tendrás instalada la herramienta Git, como se ilustra en el siguiente fragmento de código:  

```
docker run -i -t ubuntu_con_git /bin/bash 
```
 
### Dockerfile 

Crear de manera manual cada imagen de Docker con el comando `commit` podría ser laborioso, especialmente en el caso de la automatización de compilación y el proceso de CD. 

Afortunadamente, hay un lenguaje incorporado para especificar todas las instrucciones que deben ejecutarse para crear una imagen de Docker.  

Comencemos con un ejemplo prepararando una imagen `ubuntu_con_python` de la siguiente manera:  

1. Crea un nuevo directorio y un archivo llamado **Dockerfile** con el siguiente contenido:  

 ```
 FROM ubuntu:20.04 
 RUN apt-get update && \ 
 apt-get install -y python 
```
2. Ejecuta el siguiente comando para crear una imagen ubuntu_con_python: 3. Verifica que la imagen se haya creado ejecutando el siguiente comando: 

   ```
   docker build -t ubuntu_con_python . 
   ```
3. Comprueba que la imagen se creó ejecutando el siguiente comando:  `docker images`. 

Ahora podemos crear un contenedor a partir de la imagen y verificar que el intérprete de Python existe exactamente de la misma manera que lo hicimos después de ejecutar el comando `docker commit`. 

Ten  en cuenta que la imagen de ubuntu aparece solo una vez, aunque es la imagen base para `ubuntu_con_git` y `ubuntu_con_python`. 


En este ejemplo, usamos las dos primeras instrucciones de Dockerfile, como se describe aquí:  

- `FROM` define una imagen sobre la cual se construirá la nueva imagen
- `RUN` especifica los comandos que se ejecutarán dentro del contenedor. 

Las otras instrucciones ampliamente utilizadas se detallan a continuación:.  

- `COPY/ADD` copia un archivo o un directorio en el sistema de archivos de la imagen.
- `ENTRYPOINT` define qué aplicación debe ejecutarse en el contenedor ejecutable. 

Puedes encontrar una guía completa de todas las instrucciones de Dockerfile en la página oficial de Docker en https://docs.docker.com/engine/reference/builder/. 


### Aplicación Docker completa  

Como ejemplo, prepararemos, paso a paso, un programa simple de Python `hola-mundo`. Los pasos son siempre los mismos, independientemente del entorno o lenguaje de programación que utilicemos.  

#### Escribiendo la aplicación  

Crea un nuevo directorio y dentro de este directorio, crea un archivo `hola.py` con el siguiente contenido:  

```
 print ( "¡Hola mundo desde Python!" ) 
```
 Cierra el archivo. Este es el código fuente de la aplicación.  

#### Preparando el entorno 

El entorno se expresa en el Dockerfile. Necesitamos instrucciones para definir lo siguiente:  

- ¿Qué imagen base debe usarse?
- ¿Cómo instalar el intérprete de Python?
- ¿Cómo incluir `hola.py` en la imagen ?
- ¿Cómo iniciar la aplicación ? 

En el mismo directorio, crea el Dockerfile, así:  

```
 FROM ubuntu:20.04 
 RUN apt-get update && \ 
 apt-get install -y python 
 COPY hola.py . 
 ENTRYPOINT ["python", "hola.py"] 
```

#### Construyendo la imagen  

Ahora, podemos construir la imagen exactamente de la misma manera que lo hicimos antes: `docker build -t hola_mundo_python .` 

#### Ejecutando la aplicación  

Ejecutamos la aplicación ejecutando el contenedor, así: `docker run hola_mundo_python` 

### Variables de entorno  


¿Qué pasa si la ejecución de la aplicación depende de algunas condiciones? Por ejemplo, en el caso del servidor de producción, nos gustaría imprimir `Hola` en los logs, no en la consola, o quizás queramos tener diferentes servicios dependientes durante la fase de prueba y la fase de producción. 

 Una solución sería preparar un Dockerfile separado para cada caso, sin embargo, hay una mejor manera: variables de entorno.  

Para utilizar variables de entorno seguimos los siguientes pasos:  

1. Cambia la secuencia de comandos de Python `hola.py` para usar la variable de entorno de la siguiente manera:  

```
import os 
print ("Hola mundo dice  %s !" % os.environ['NAME']) 
```

2. Construye la imagen, así: `docker build -t hola_mundo_python_nombre  .` 

3. Ejecuta el contenedor pasando la variable de entorno, así:  `docker run -e NAME=Ako  hola_mundo_python_nombre`.

4. Alternativamente, podemos definir un valor de variable de entorno en Dockerfile, como el siguiente:  
 ```
 ENV NAME Ako 
```
5. Ejecuta el contenedor sin especificar la opción `-e` de la siguiente manera:  

 ```
 docker build -t hola_mundo_python_nombre_default .
 docker run hola_mundo_python_nombre_default 
``` 
Las variables de entorno son especialmente útiles cuando necesitamos tener diferentes versiones del contenedor Docker dependiendo de su propósito, por ejemplo, para tener perfiles separados para servidores de producción y pruebas. 


#### Estados del contenedor Docker  

Todas las aplicaciones que hemos ejecutado hasta ahora debían hacer algún trabajo y detenerse. Sin embargo, hay aplicaciones que deben ejecutarse continuamente, como los servicios.  


Para ejecutar un contenedor en segundo plano, podemos usar la opción `-d (--detach)`. Usemos con la imagen de ubuntu, de la siguiente manera:  

```
docker run -d -t ubuntu:20.04 
```

Este comando inició el contenedor de Ubuntu pero no lo adjuntó la consola. Podemos ver que se está ejecutando usando el siguiente comando: `docker ps`. 


Este comando imprime todos los contenedores que se encuentran en estado de ejecución. ¿Qué pasa con nuestros contenedores viejos?. Podemos encontrarlos imprimiendo todos los contenedores, así:  

 ```
 docker ps -a 
```
 
Ten en cuenta que todos los contenedores antiguos están en un estado exit. Hay dos estados más que aún no hemos observado: `paused` y `restarting`.  

Todos los estados y las transiciones entre ellos se presentan en el siguiente diagrama: 

 
<img src="Imagenes/Estado-contenedor.png" width="560px" height="300px">
 

El diagrama anterior  muestra los comandos de Docker que se usan para cambiar el estado del contenedor de Docker de un estado a otro.  

 Por ejemplo, podemos dejar de ejecutar el contenedor de Ubuntu, como se muestra aquí:  

```
docker stop 95f29bfbaadc 
docker ps 
CONTAINER ID IMAGE COMMAND CREATED STATUS PORTS NAMES 
```
 
**Información:** Siempre hemos usado el comando `docker run` para crear e iniciar un contenedor. Sin embargo, es posible simplemente crear un contenedor sin iniciarlo (con `docker create`).  

 
### Redes docker  

La mayoría de las aplicaciones en estos días no se ejecutan de forma aislada; necesitan comunicarse con otros sistemas a través de la red. 
Si queremos ejecutar un sitio web, un servicio web, una base de datos o un servidor de caché dentro de un contenedor Docker, primero debemos comprender cómo ejecutar un servicio y exponer su puerto a otras aplicaciones.  

#### Servicios en uso  

Comencemos con un ejemplo simple y ejecutemos un servidor Tomcat directamente desde Docker Hub, de la siguiente manera: `docker run -d tomcat` 

Tomcat es un servidor de aplicaciones web a cuya interfaz de usuario se puede acceder por el puerto 8080. 
Por lo tanto, si instalas Tomcat podrías navegar en http://localhost:8080. En este caso, sin embargo, Tomcat se ejecuta dentro del contenedor Docker.  

Podemos ver que se está ejecutando, de la siguiente manera:  `docker ps`

Dado que se ejecuta como un demonio (con la opción -d), no vemos los logs en la consola. No obstante, podemos acceder a él ejecutando el siguiente código:  `docker logs d51ad8634fac` 

Si no hay errores, deberíamos ver muchos logs, lo que indica que Tomcat se ha iniciado y es accesible a través del puerto 8080. 

Podemos intentar ir a http:// localhost:8080, pero no podremos conectarnos. Esto se debe a que Tomcat se inició dentro del contenedor y estamos tratando de alcanzarlo desde el exterior.  

En otras palabras, podemos alcanzarlo solo si nos conectamos con el comando a la consola en el contenedor y lo verificamos allí. 

¿Cómo hacemos que ejecutar Tomcat sea accesible desde el exterior?  


Necesitamos iniciar el contenedor, especificando la asignación de puertos con el indicador `-p (--publish)` de la siguiente manera:  

```
-p, --publish <host_port>:<container_port> 
``` 

Entonces, primero detengamos el contenedor en ejecución y comenzamos uno nuevo, así:  

```
docker stop d51ad8634fac 
docker run -d -p 8080:8080 tomcat 
```
Después de esperar unos segundos, Tomcat debería haberse iniciado y deberíamos poder abrir su página: http://localhost:8080.  

**Nota:** Docker también nos permite publicar en la interfaz de red del host específico con `-p  <ip>:<host_port>:<container_port>` . 

#### Redes de contenedores  

Nos hemos conectado a la aplicación que se está ejecutando dentro del contenedor. De hecho, la conexión es bidireccional porque  ejecutamos los comandos `apt-get install` desde adentro y los paquetes se descargaron de Internet. ¿Cómo es esto posible?  

Si revisas las interfaces de red en tu máquina, puedes ver que una de las interfaces se llama `docker0`, como se ilustra aquí:  

```
ifconfig docker0 
```

La interfaz `docker0` es creada por el demonio Docker para conectarse con el contenedor Docker. 
Ahora, podemos ver qué interfaces se crean dentro del contenedor Tomcat Docker creado con el comando `docker inspect`, de la siguiente manera:  

```
docker inspect 03d1e6dc4d9e 
```
 
Esto imprime toda la información sobre la configuración del contenedor en formato de notación de objetos JavaScript (JSON). 

Entre otras cosas, podemos encontrar la parte relacionada con la configuración de la red, como se ilustra en el siguiente fragmento de código:  

```
"NetworkSettings": { 
  "Bridge": "",
  "Ports": {
    "8080/tcp": [
    {
     "HostIp": "0.0.0.0",
     "HostPort": "8080" 
       } 
      ] 
   }, 
  "Gateway": "172.17.0.1", 
  "IPAddress": "172.17.0.2", 
   "IPPrefixLen": 16, 
} 
```
Podemos observar que el contenedor Docker tiene una dirección IP de 172.17.0.2 y se comunica con el host Docker con una dirección IP de 172.17.0.1. 
Esto significa que en el ejemplo anterior, podríamos acceder al servidor Tomcat incluso sin el reenvío de puertos, usando http://172.17.0.2:8080. 

Sin embargo, en la mayoría de los casos, ejecutamos el contenedor Docker en una máquina servidor y queremos exponerlo afuera, por lo que debemos usar la opción `-p`.  


Ten en cuenta que, de forma predeterminada, los contenedores no abren ninguna ruta desde sistemas externos. 
Podemos cambiar este comportamiento predeterminado jugando con el indicador `--network` y configurándolo de la siguiente manera:  


- `bridge (predeterminado)`: red a través del puente Docker predeterminado
- `none`: sin red
- `container`: red unida con el otro contenedor (especificado)
- `host`: pila de red
- `NETWORK:` red creada por el usuario (usando el comando `docker network create`) 


Las diferentes redes se pueden enumerar y administrar mediante el comando `docker network` de la siguiente manera: `docker network ls` 

Si especificamos `none` como red, no podremos conectarnos al contenedor y viceversa. El contenedor no tiene acceso de red al mundo exterior. 

La opción más popular es la predeterminada `(bridge)` porque nos permite definir explícitamente qué puertos deben publicarse, seguros y accesible.  

 
#### Exponiendo los puertos de los contenedores  

Si profundizamos en la imagen de Tomcat en GitHub (https://github.com/docker-library/tomcat), podemos ver la siguiente línea en el Dockerfile: 

```
EXPOSE 8080 
```

Esta instrucción de Dockerfile estipula que el puerto 8080 debe estar expuesto desde el contenedor. Sin embargo, como ya hemos visto, esto no significa que el puerto se publique automáticamente. La instrucción EXPOSE solo informa a los usuarios qué puertos deben publicar.  

#### Asignación automática de puertos  

Intentemos ejecutar el segundo contenedor Tomcat sin detener el primero, de la siguiente manera:  `docker run -d -p 8080:8080 tomcat` 

Esto produce un error. En tales casos, tenemos que encargarnos de la unicidad de los puertos por nuestra cuenta o dejar que Docker asigne los puertos automáticamente usando una de las siguientes versiones del comando `publish`:  

- `-p <container_port>`: publica el puerto del contenedor en el puerto del host no utilizado  

- `-p (--publish-all)`: publica todos los puertos expuestos por el contenedor en los puertos de host no utilizados, de la siguiente manera:  

  ```
  docker run -d -P tomcat
  docker port 078e9d12a1c8
  8080/tcp -> 0.0.0.0:32772
  ``` 

Podemos ver que la segunda instancia de Tomcat se ha publicado en el puerto 32772, por lo que se puede navegar en http://localhost:32772.  

### Volúmenes de Docker  

Los volúmenes de Docker permiten la persistencia y el uso compartido de los datos de un contenedor. 

 <img src="Imagenes/Volumen-docker.png" width="520px" height="200px">

Los volúmenes también separan claramente el procesamiento de los datos. Comencemos con el siguiente ejemplo:  


1. Especifica un volumen con la opción `-v <host_path>:<container_path>` y luego conecta al contenedor  de la siguiente manera:  

 ```
  docker run -i -t -v ~/docker_ubuntu:/directorio_host 
    ubuntu:20.04 /bin/bash 
```

2. Crea un archivo vacío en `directorio_host` en el contenedor, así:  `# touch /directorio_host/archivo.txt` 

3. Comprueba si el archivo se creó en el sistema de archivos del host Docker ejecutando el siguiente comando:  

  ```
   # exit
   exit
  ls ~/docker_ubuntu/
   archivo..txt 
  ```

4. Detén el contenedor y ejecuta uno nuevo para ver si el archivo todavía estará allí, de la siguiente manera:

  ```
  docker run -i -t -v ~/docker_ubuntu:/host_directory
         ubuntu:20.04 /bin/bash`
  ls /directorio_host/ 
  exit
  ```
5. En lugar de especificar un volumen con el indicador `-v`, es posible especificarlo como una instrucción en el Dockerfile, como en el siguiente ejemplo:  

  ```
  VOLUME /directorio_host 
   ```
 

En este caso, si ejecutamos el contenedor Docker sin el indicador `-v`, la ruta del contenedor `/directorio_host` se asignará al directorio predeterminado del host para volúmenes, `/var/lib/docker/vfs/`. 

Esta es una buena solución si entregas una aplicación como una imagen y sabes que requiere almacenamiento permanente por algún motivo (por ejemplo, almacenar registros de aplicaciones).  

 Los volúmenes de Docker pueden ser mucho más complicados, especialmente en el caso de las bases de datos.  

Un enfoque muy común para la gestión de datos con Docker es introducir una capa adicional, en forma de contenedores de volumen de datos. 

Un contenedor de volumen de datos es un contenedor Docker cuyo único propósito es declarar un volumen. 

Luego, otros contenedores pueden usarlo (con la opción `--volumes-from <container>`) en lugar de declarar el volumen directamente. Lee más en https://docs.docker.com/storage/volumes/.  

### Uso de nombres en Docker  

Hasta ahora, cuando hemos operado en contenedores, siempre hemos usado nombres generados automáticamente. Este enfoque tiene algunas ventajas, como que los nombres sean únicos (sin conflictos de nombres) y automáticos. 

En muchos casos, sin embargo, es mejor dar un nombre descriptivo a un contenedor o una imagen.  

#### Contenedores de nombres 

Hay dos buenas razones para nombrar un contenedor: la conveniencia y la posibilidad de automatización. Veamos por qué, de la siguiente manera:  

- Conveniencia: es más sencillo realizar cualquier operación sobre un contenedor direccionando por su nombre que comprobando los hashes o el nombre autogenerado.
- Automatización: a veces, nos gustaría depender del nombre específico de un contenedor. 

Por ejemplo, nos gustaría tener contenedores que dependan unos de otros y que estén enlazados unos con otros. Por lo tanto, necesitamos saber sus nombres.  

Para nombrar un contenedor, usamos el parámetro `--name` de la siguiente manera:  

```
docker run -d --name tomcat tomcat 
```

Podemos verificar (con `docker ps`) que el contenedor tenga un nombre significativo. Además, como resultado cualquier operación se puede realizar utilizando el 
nombre del contenedor como en el siguiente ejemplo:  `docker logs tomcat`  


Ten en cuenta que cuando se nombra un contenedor, no pierde su identidad. Todavía podemos dirigirnos al contenedor por su ID de hash generado automáticamente, tal como lo hicimos antes.  

**Nota:** Un contenedor siempre tiene un ID y un nombre. Puede ser abordado por cualquiera de ellos y ambos son únicos.  


### Etiquetado de imágenes  

Las imágenes se pueden etiquetar. Ya hicimos esto cuando creamos nuestras propias imágenes, por ejemplo, en el caso de construir la imagen `hola_mundo_python` como se ilustra aquí:  

```
docker build -t hola_mundo_python . 
```
 
El indicador `-t` describe la etiqueta de la imagen. Si no lo usamos, la imagen se construirá sin ninguna etiqueta y como resultado tendríamos que redireccionar por su ID (hash) para poder ejecutar el contenedor. 

La imagen puede tener varias etiquetas y deben seguir esta convención de nomenclatura:  

```
<registry_address>/<image_name>:<version> 
```

Una etiqueta consta de las siguientes partes:  

- `registry_address`: IP y puerto del registro o el nombre de alias
- `image_name`: nombre de la imagen que se construye, por ejemplo, ubuntu
- `versión`: una versión de la imagen en cualquier forma, por ejemplo, 20.04, 20170310 

Si una imagen se mantiene en el registro oficial de Docker Hub, podemos omitir la dirección del registro. Es por eso que ejecutamos la imagen Tomcat sin ningún prefijo. 

La última versión siempre se etiqueta como la última y también se puede omitir, por lo que ejecutamos la imagen de Tomcat sin ningún sufijo.  

### Limpieza en Docker

A lo largo de esta actividad, hemos creado una serie de contenedores e imágenes. Sin embargo, esto es solo una pequeña parte de lo que verá en escenarios de la vida real. 

Incluso cuando los contenedores no se están ejecutando, deben almacenarse en el host de Docker. Esto puede provocar rápidamente que se exceda el espacio de almacenamiento y se detenga la máquina. 

¿Cómo podemos abordar esta preocupación?

#### Limpieza de contenedores  

Primero, veamos los contenedores que están almacenados en nuestra máquina. Estos son los pasos que debemos seguir:  


1. Para imprimir todos los contenedores (independientemente de su estado), podemos usar el comando docker `ps -a` de la siguiente manera:  `docker ps -a`
2. Para eliminar un contenedor detenido, podemos usar el comando docker rm (si un contenedor se está ejecutando, primero debemos detenerlo), de la siguiente manera:  

  ```
  docker rm 47ba1c0ba90e 
  ```
3. Si queremos eliminar todos los contenedores detenidos, podemos usar el siguiente comando:  

  ```
  docker container prune 
   ```
4. También podemos adoptar un enfoque diferente y pedirle al contenedor que se elimine tan pronto como se detenga usando el indicador `--rm`, como en el siguiente ejemplo:  

 ```
 docker run --rm hello-world 
```

En la mayoría de los escenarios de la vida real, no usamos contenedores detenidos y se dejan solo con fines de depuración.  

#### Limpieza de imágenes  

Limpiar las imágenes es tan importante como limpiar los contenedores. Pueden ocupar mucho espacio, especialmente en el caso del proceso de CD, cuando cada compilación termina en una nueva imagen de Docker. 

Esto puede resultar rápidamente en un error de no dejar espacio en el dispositivo.  

Los pasos son indicados aquí:  


1. Para verificar todas las imágenes en el contenedor de Docker, podemos usar el comando de imágenes de Docker (`docker images`), de la siguiente manera:  `docker images` 

2. Para eliminar una imagen, podemos llamar al siguiente comando:  

 ```
 docker rmi 48b5124b2768 
 ```

3. En el caso de las imágenes, el proceso de limpieza automática es un poco más complejo. Las imágenes no tienen estados, por lo que no podemos pedirles que se eliminen cuando no se usan.
   Una estrategia común sería configurar un trabajo de limpieza de `cron`, que elimina todas las imágenes antiguas ya no utilizadas. 

 Podríamos hacer esto usando el siguiente comando: `docker image prune` 

**Nota**: Si tenemos contenedores que usan volúmenes, entonces, además de imágenes y contenedores, vale la pena pensar en limpiar volúmenes. La forma más fácil de hacer esto es usar el comando `docker volume prune`.  

**Nota**: Usa el comando `docker system prune` para eliminar todos los contenedores, imágenes y redes no utilizados. Además, puedes agregar el parámetro `–volumes` para limpiar volúmenes. 

 
### Descripción general de los comandos de Docker  


Todos los comandos de Docker se pueden encontrar ejecutando el siguiente comando de ayuda: `docker help` 


Para ver todas las opciones de cualquier comando de Docker en particular, podemos usar la ayuda de `docker help <command>`, como en el siguiente ejemplo:  

```
docker help run 
```

También hay una muy buena explicación de todos los comandos de Docker en la página oficial de Docker en https://docs.docker.com/engine/reference/commandline/docker/.   

Vale la pena leerlo, o al menos echarle un vistazo.  

 ### Ejercicios 

1. Ejecuta CouchDB como un contenedor de Docker y publica su puerto, de la siguiente manera 

- Ejecuta el contenedor.
- Publica el puerto de CouchDB.
- Abre el navegador y verifica que CouchDB esté disponible.  

**Sugerencia:** Puedes usar el comando `docker search` para encontrar la imagen de CouchDB. 

2. Crea una imagen de Docker con un servicio REST y responda Hola CC-3S2  al localhost:8080/hola. Utiliza el lenguaje y el framework que prefieras.  

Estos son los pasos que debe seguir:  

- Crea una aplicación de servicio web
- Crea un Dockerfile para instalar dependencias y librerías.
- Construye la imagen.
- Ejecuta el contenedor que está publicando el puerto
- Comprueba que se está ejecutando correctamente utilizando el navegador (o curl). 


 
