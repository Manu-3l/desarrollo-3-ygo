# LABORATORIO 1 DE DESARROLLO DE SOFTWARE III

## Explicación

Este laboratorio se hizo en base al juego de cartas coleccionables conocido como Yu-Gi-Oh! teniendo de objetivo poner a prueba lo visto durante las ultimas clases en cuanto al desarrollo usando APIs, en este caso para hacer un juego simple en el que llamamos a los datos de la API YGOProDeck los cuales usaremos para detalles visuales y mecanicos. El punto del juego es comparar cartas que son llamadas para ser usadas desde la API, en este caso 3 de ellas que tienen que ser del tipo monstruo (Ya sea de efecto, normal o del Extra Deck). El usuario podrá escoger cualquiera de estas tres cartas y elegir si usará su ATK o DEF para compararlas con las de la maquina. La interfaz del programa utiliza Java Swing para simular el duelo

Un aspecto clave de la aplicación es el uso de la API YGOProDeck para obtener las cartas. Mediante el cliente HttpClient, la aplicación consulta el endpoint randomcard.php (que redirige a cardinfo.php), obtiene un JSON con los datos de la carta y filtra aquellas que sean tipo Monster. A partir de la información recibida —nombre, puntos de ataque (ATK), puntos de defensa (DEF) e imagen— se construyen los objetos Card que representan a cada monstruo. Esto permite que el duelo no use datos estáticos, sino que siempre se alimente de cartas reales y variadas de la base de datos oficial de Yu-Gi-Oh!, haciendo que cada partida sea distinta.

## Guia de usuario
1- Al iniciar el programa desde el main se abre la pantalla principal, al principio solo tiene un botón y el log
2- Al presionar este botón el programa llama los datos de la API para robar 3 cartas aleatorias
3- Al terminar de cargar ahora el programa muestra las siguientes cosas:
- En la parte superior hay tres cartas boca abajo con un color grisaceo, son las cartas de la maquina y no son elegibles por el usuario
- Abajo estan las 3 cartas del usuario con los nombres y estadisticas del Monstruo
4- El usuario debe de escoger una de estas tres cartas, para posteriormente luego de que salga un mensaje escoger si usa su ATK o DEF
5- Se lleva a cabo la logica para que la maquina escoja que carta usar, al hacerlo se calcula quien tiene la mayor caracteristica.
- Si es ATK se compara con el ATK de la maquina
- Si es DEF se compara con el ATK de la maquina tambien.
6- Una vez se muestra quien gano el turno del duelo se debe de presionar el botón de continuar.
7- Al hacer esto se muestra la carta usada de la maquina arriba, siendo inusable tanto por el usuario como la IA, pero tambien la carta que escogió el usuario le sucede lo mismo.
8- Esto se repite hasta que la maquina o el usuario gana 2 veces.

## Pantalla inicial
<img width="917" height="425" alt="image" src="https://github.com/user-attachments/assets/3a1bae90-1c87-485e-8fd5-a89532b73680" />

## Cargando las cartas
<img width="917" height="425" alt="image" src="https://github.com/user-attachments/assets/f925d06c-31dc-437c-b1aa-00e11f1f8c69" />

## Pantalla inicial con las cartas del usuario y la maquina
<img width="917" height="425" alt="image" src="https://github.com/user-attachments/assets/5ef58c91-2d78-4b3c-b38e-b4821da2492c" />

## Pantalla para seleccionar la posición de batalla
<img width="917" height="425" alt="image" src="https://github.com/user-attachments/assets/e1bcefd8-ba26-407e-80b0-a81a79e2886c" />

## Pantalla para declarar al ganador del duelo
<img width="425" height="917" alt="image" src="https://github.com/user-attachments/assets/2033e265-2733-488e-b544-934415f902ba" />

## Log de combates
<img width="826" height="780" alt="image" src="https://github.com/user-attachments/assets/6954abc4-cf0e-4be0-b04a-5887b94c90e6" />

## Cartas del usuario y de la maquina quedan boca arriba e inutilizables
<img width="885" height="977" alt="image" src="https://github.com/user-attachments/assets/70081570-91b9-4b80-8ea2-fce59adbb986" />
