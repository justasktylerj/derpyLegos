package bot.model;

import lejos.hardware.ev3.LocaleEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.navigation.MovePilot;
import lejos.utility.Delay;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.EV3UltrasonicSensor;


public class EV3Bot 
{
	private String botMessage;
	private int xPosition;
	private int yPosition;
	private long waitTime;
	
	private MovePilot botPilot;
	private EV3UltrasonicSensor distanceSensor;
	private EV3TouchSensor backTouch;
	private float [] ultrasonicSamples;
	private float [] touchSamples;
	
	public EV3Bot()
	{
		this.botMessage = "bot";
		this.xPosition = 50;
		this.yPosition = 50;
		this.waitTime = 4000;
		
		distanceSensor = new EV3UltrasonicSensor(LocalEV3.get().getPort("S1"));
		backTouch = new EV3UltrasonicSensor(LocalEV3.get().getPort("S2"));
		
		setupPilot();
		
		displayMessage();
	
	}
	
	private void setupPilot()
	{
		Wheel leftWheel = WheeledChassis.modelWheel(Motor.A, 43.3).offset(-72);
		Wheel rightWheel = WheeledChassis.modelWheel(Motor.B, 43.3).offset(-72);
		WheeledChassis chassis = WheeledChassis(new Wheel[]{leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAl);
	}
	
	private void driveRoom()
	{
		ultrasonicSamples =new float [distanceSensor.sampleSize()];
		distanceSensor.fetchSample(ultrasonicSamples, 0);
		if(ultrasonicSamples[0] < 2.5)
		{
			//short
			botPilot.travel(20.00);
		}
		else
		{
			//long
			botPilot.travel(254.00);
		}
		
		displayMessage("driveRoom");
	}
	
	private void displayMessage(String message)
	{
		LCD.drawString(botMessage, xPosition, yPosition);
		Delay.msDelay(waitTime);
	}
	
	
}
