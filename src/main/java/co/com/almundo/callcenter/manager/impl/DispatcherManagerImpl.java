package co.com.almundo.callcenter.manager.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.almundo.callcenter.dao.OperatorDao;
import co.com.almundo.callcenter.manager.DispatcherManager;
import co.com.almundo.callcenter.models.Operator;
import co.com.almundo.callcenter.models.ResponseModel;
import co.com.almundo.callcenter.models.StatusCall;

@Component
public class DispatcherManagerImpl implements DispatcherManager {

	private static final int NUMERO_MAXIMO_LLAMADAS_CONCURRENTES = 10;

	private static ExecutorService callPool = Executors.newFixedThreadPool(NUMERO_MAXIMO_LLAMADAS_CONCURRENTES);

	private Random random = new Random();

	// Tiempo mínimo llamada en segundos
	private final static int MIN_TIME_CALL = 5;

	// Tiempo máximo llamada en segundos
	private final static int MAX_TIME_CALL = 10;

	// Variable que al activarse pone en espera la llamada hasta que encuentre un
	// operador disponible
	// en caso contrario respondera la llamada con el estatus
	// OPERADORES_NO_DISPONIBLES
	private static boolean ESPERAR_OPERADOR_DISPONIBLE = true;

	// Tiempo muerto para volver a preguntar si hay un operador disponible en
	// segundos
	private final static int TIME_ANSWER_OPERATOR_AVAILABLE = 3;

	@Autowired
	OperatorDao operatorDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.com.almundo.callcenter.manager.DispatcherManager#dispatchCall()
	 */
	@Override
	public ResponseModel dispatchCall() throws InterruptedException, ExecutionException {

		Future<ResponseModel> answer;

		Callable<ResponseModel> callable = new Callable<ResponseModel>() {
			@Override
			public ResponseModel call() {
				List<Operator> availableOperators = getOperatorsAvailable();
				ResponseModel response = new ResponseModel();
				Operator freeOperator = null;

				// Se obtiene tiempo duración de la llamada en segundos
				int delay = getDelayCall();

				if (!availableOperators.isEmpty()) {
					freeOperator = availableOperators.get(0);

					// Al atender la llamada se hace no disponible el operador
					freeOperator.setAvailable(false);
				} 
				// En caso de estar ESPERAR_OPERADOR_DISPONIBLE activado (true), se espera a que exista
				// un operador disponible
				else if (ESPERAR_OPERADOR_DISPONIBLE) {
					while (availableOperators.isEmpty()) {
						waitOperatorAvailable();
						availableOperators = getOperatorsAvailable();
					}

				} 
				// En caso de estar ESPERAR_OPERADOR_DISPONIBLE desactivado (true), 
				// se finaliza la llamada con el estatus OPERADORES_NO_DISPONIBLES
				else {
					response.setStatus(StatusCall.OPERADORES_NO_DISPONIBLES);
					return response;
				}

				processCall(delay);

				response.setOperator(freeOperator);
				response.setCallDurationInSeconds(delay);
				response.setStatus(StatusCall.FINALIZA_OK);

				// Se hace de nuevo disponible el operador para atender otra llamada
				freeOperator.setAvailable(true);

				return response;
			}
		};

		answer = callPool.submit(callable);

		while (!answer.isDone()) {
			Thread.sleep(100);
		}

		ResponseModel answerCall = (ResponseModel) answer.get();

		return answerCall;
	}

	/**
	 * Función encargada de devolver los operadores disponibles en el el siguiente orden de cargos (Charges)
	 * OPERADOR, SUPERVISOR, DIRECTOR;
	 * 
	 * @return
	 */
	private List<Operator> getOperatorsAvailable() {
		return operatorDao.getOperators().stream()
				.filter(operator -> operator.isAvailable())
				.sorted(Comparator.comparing(Operator::getCharge))
				.collect(Collectors.toList());
	}

	private int getDelayCall() {
		int diff = MAX_TIME_CALL - MIN_TIME_CALL;
		int i = random.nextInt(diff + 1);
		i += MIN_TIME_CALL;

		return i;
	}

	private void waitOperatorAvailable() {
		try {
			Thread.sleep(TIME_ANSWER_OPERATOR_AVAILABLE * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void processCall(int delay) {
		try {
			Thread.sleep(delay * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static boolean isEsperarOperadorDisponible() {
		return ESPERAR_OPERADOR_DISPONIBLE;
	}

	public static void setEsperarOperadorDisponible(boolean esperarOperadorDisponible) {
		ESPERAR_OPERADOR_DISPONIBLE = esperarOperadorDisponible;
	}
	
	

}
