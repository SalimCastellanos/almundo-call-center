# prueba técnica almundo.com - Call Center

La prueba fue terminada por completo, parte inicial y puntos extras.

Dar alguna solución sobre qué pasa con una llamada cuando no hay
ningún empleado libre.

	Es posible en el manager (AcuerdoPagoSDManagerImpl) activar o desactivar la bandera ESPERAR_OPERADOR_DISPONIBLE
	en caso de estar activa el sistema retendrá la llamada hasta que exista un operador disponible, también hay un parámetro
	que indica muerto para volver a preguntar TIME_ANSWER_OPERATOR_AVAILABLE. En caso de que ESPERAR_OPERADOR_DISPONIBLE 
	este desactivado (false), el sistema finalizara la llama y responderá con el status OPERADORES_NO_DISPONIBLES
	
Dar alguna solución sobre qué pasa con una llamada cuando entran
más de 10 llamadas concurrentes.

	El sistema utiliza un ThreadPool que garantiza que el sistema solo acepte 10 llamadas concurrentes, esto se puede configurar
	con el parámetro NUMERO_MAXIMO_LLAMADAS_CONCURRENTES, superado ese limite las llamadas quedan en espera. En combinación con el parámetro
	ESPERAR_OPERADOR_DISPONIBLE esto podría dar lugar a varios escenarios diferentes, lo cual depende de número de operadores registrados en el sistema.
	