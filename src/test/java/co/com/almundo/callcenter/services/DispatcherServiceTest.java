package co.com.almundo.callcenter.services;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import co.com.almundo.callcenter.Main;
import co.com.almundo.callcenter.manager.DispatcherManager;
import co.com.almundo.callcenter.models.ResponseModel;
import co.com.almundo.callcenter.models.StatusCall;
import co.com.almundo.callcenter.util.Context;

public class DispatcherServiceTest {

	DispatcherManager callCenter = (DispatcherManager) Context.getBean(DispatcherManager.class);;

	private HttpServer server;
	private WebTarget target;

	@Before
	public void setUp() throws Exception {
		// Se inicicia el servidor
		server = Main.startServer();

		// Se crea cliente para pruebast
		Client c = ClientBuilder.newClient();

		target = c.target(Main.BASE_URI);
	}

	@After
	public void tearDown() throws Exception {
		server.stop();
	}

	/**
	 * Test encargado de probar 10 llamadas concurrentes al Call Center
	 * 
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Test
	public void DispatcherServiceTenCallTest() throws InterruptedException, ExecutionException {

		ExecutorService callPool = Executors.newFixedThreadPool(100);

		Future<StatusCall> answer;

		Callable<StatusCall> callRequest = new Callable<StatusCall>() {
			@Override
			public StatusCall call() {
				ResponseModel responseCall = target.path("dispatcher").request().get(ResponseModel.class);
				return responseCall.getStatus();
			}
		};

		Future<StatusCall> call1 = callPool.submit(callRequest);
		Future<StatusCall> call2 = callPool.submit(callRequest);
		Future<StatusCall> call3 = callPool.submit(callRequest);
		Future<StatusCall> call4 = callPool.submit(callRequest);
		Future<StatusCall> call5 = callPool.submit(callRequest);
		Future<StatusCall> call6 = callPool.submit(callRequest);
		Future<StatusCall> call7 = callPool.submit(callRequest);
		Future<StatusCall> call8 = callPool.submit(callRequest);
		Future<StatusCall> call9 = callPool.submit(callRequest);
		Future<StatusCall> call10 = callPool.submit(callRequest);

		while (!call1.isDone() && !call2.isDone() && !call3.isDone() && !call4.isDone() && !call5.isDone()
				&& !call6.isDone() && !call7.isDone() && !call8.isDone() && !call9.isDone() && !call10.isDone()) {
			Thread.sleep(100);
		}

		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call1.get());
		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call2.get());
		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call3.get());
		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call4.get());
		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call5.get());
		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call6.get());
		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call7.get());
		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call8.get());
		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call9.get());
		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call10.get());
	}

	/**
	 * Test encargado de probar 15 llamadas concurrentes al Call Center
	 * se comprueba que el sistema es capaz de atenderlas pero solo 10 al tiempo
	 * 
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Test
	public void DispatcherService15CallTest() throws InterruptedException, ExecutionException {

		ExecutorService callPool = Executors.newFixedThreadPool(100);

		Future<StatusCall> answer;

		Callable<StatusCall> callRequest = new Callable<StatusCall>() {
			@Override
			public StatusCall call() {
				ResponseModel responseCall = target.path("dispatcher").request().get(ResponseModel.class);
				return responseCall.getStatus();
			}
		};

		Future<StatusCall> call1 = callPool.submit(callRequest);
		Future<StatusCall> call2 = callPool.submit(callRequest);
		Future<StatusCall> call3 = callPool.submit(callRequest);
		Future<StatusCall> call4 = callPool.submit(callRequest);
		Future<StatusCall> call5 = callPool.submit(callRequest);
		Future<StatusCall> call6 = callPool.submit(callRequest);
		Future<StatusCall> call7 = callPool.submit(callRequest);
		Future<StatusCall> call8 = callPool.submit(callRequest);
		Future<StatusCall> call9 = callPool.submit(callRequest);
		Future<StatusCall> call10 = callPool.submit(callRequest);
		Future<StatusCall> call11 = callPool.submit(callRequest);
		Future<StatusCall> call12 = callPool.submit(callRequest);
		Future<StatusCall> call13 = callPool.submit(callRequest);
		Future<StatusCall> call14 = callPool.submit(callRequest);
		Future<StatusCall> call15 = callPool.submit(callRequest);

		while (!call1.isDone() && !call2.isDone() && !call3.isDone() && !call4.isDone() && !call5.isDone()
				&& !call6.isDone() && !call7.isDone() && !call8.isDone() && !call9.isDone() && !call10.isDone()
				&& !call11.isDone() && !call12.isDone() && !call13.isDone() && !call14.isDone() && !call15.isDone()) {
			Thread.sleep(100);
		}

		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call1.get());
		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call2.get());
		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call3.get());
		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call4.get());
		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call5.get());
		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call6.get());
		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call7.get());
		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call8.get());
		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call9.get());
		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call10.get());
		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call11.get());
		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call12.get());
		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call13.get());
		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call14.get());
		assertEquals(StatusCall.FINALIZA_OK, (StatusCall) call15.get());
	}
}
