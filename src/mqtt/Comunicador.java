package mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import tm.BebedoresTM;
import vos.Bebida;

import java.util.List;
import java.util.Scanner;


public class Comunicador implements MqttCallback {

    
    private static final String brokerUrl = "tcp://localhost:8083";
    
    private static final String clientId = "Bar1";

    private static final String topic = "Test";
    
    public static BebedoresTM tm=new BebedoresTM();
    
    private  MqttClient sampleClient; 

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {

    	Comunicador comunicador=new Comunicador();
    	comunicador.subscribe(topic);
    	
    	Scanner sc=new Scanner(System.in);
		boolean fin=false;
		while(!fin){
			printMenu();
			int option = sc.nextInt();
			switch(option){
			case 1:
				List<Bebida> l=tm.darBebidas();
				for(Bebida b:l)
				{
					System.out.println(b.getNombre()+", Cantidad actual: "+b.getCantidad()+", Precio: "+b.getPrecio());
				}
				break;
			case 2:
				tm.reabastecerCerveza();
				break;
			case 3:
				comunicador.enviarMensaje(clientId+":RellenarAguilAlpes");
				break;
			case 4:
				
				break;
			case 5:	
				fin=true;
				break;
			}


		}
		sc.close();
    }
    
    public Comunicador()
    {
        try {
			sampleClient = new MqttClient(brokerUrl, clientId);
		} catch (MqttException e) {
			e.printStackTrace();
		}
    }

    /**
     * Subscribe.
     *
     * @param topic
     *            the topic
     */
    public void subscribe(String topic) {

        try {

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            System.out.println("checking");

            System.out.println("Conectandose al broker ubicado en: " + brokerUrl);
            sampleClient.connect(connOpts);
            System.out.println("Conexion establecida");

            sampleClient.setCallback(this);
            sampleClient.subscribe(topic);

            System.out.println("Se esta escuchando");

        } catch (MqttException me) {

            System.out.println("Mqtt reason " + me.getReasonCode());
            System.out.println("Mqtt msg " + me.getMessage());
            System.out.println("Mqtt loc " + me.getLocalizedMessage());
            System.out.println("Mqtt cause " + me.getCause());
            System.out.println("Mqtt excep " + me);
        }
    }
    
    public void enviarMensaje(String content)
    {
    	 try {            
            
             System.out.println("Publicando mensaje al topico: " + topic);
             MqttMessage message = new MqttMessage(content.getBytes());
             message.setQos(0);
             sampleClient.publish(topic, message);
             System.out.println("Message published");
                         
         } catch (MqttException me) {
             System.out.println("reason " + me.getReasonCode());
             System.out.println("msg " + me.getMessage());
             System.out.println("loc " + me.getLocalizedMessage());
             System.out.println("cause " + me.getCause());
             System.out.println("excep " + me);
             me.printStackTrace();
             
         }
    	
    }

    
    public void connectionLost(Throwable arg0) {
    	System.out.println("Conexion perdida");
    	arg0.printStackTrace();

    }

    public void deliveryComplete(IMqttDeliveryToken arg0) {

    }

    public void messageArrived(String topic, MqttMessage message) throws Exception {

    	String mensaje=message.toString();
    	String[] datos=mensaje.split(":");
    	
    	if(!datos[0].equals(clientId)) //Revisa que el emisor sea diferente a sí mismo
    	{
    		System.out.println("---------------------------------------");
    		System.out.println(mensaje);
    		System.out.println("---------------------------------------");
    		if(datos[1].equals("RellenarAguilAlpes"))
    		{
    			tm.reabastecerCerveza();
    		}
    	}
    }
    
    private static void printMenu() {
		System.out.println("---------ISIS 2304 - Sistemas Transaccionales ----------");
		System.out.println("---------------------Laboratorio Transacciones Distribuidas----------------------");
		System.out.println("1. Consultar el inventario local");
		System.out.println("2. Reabastecerse localmente de AguilAlpes");
		System.out.println("3. Ordenar a todos los bares que rellenen sus cantidades de AguilAlpes");
		System.out.println("4. Hacer un pedido de 40  botellas de AguilAlpes al sistema de rotondas, si no hay suficientes, pedir al menos 30");
		System.out.println("5. Salir");
		System.out.println("Escriba el número correspondiente a la opción, luego presione enter: (e.g., 1):");

	}

}




