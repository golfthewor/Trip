package com.example.calculate;

import android.util.Log;

public class Calculate {
	
	private double gasPrice;
	private double consumption;
	private double average;
	private double volumn;
	
	public void setValue(String price, String engine){
		
		this.gasPrice = Double.parseDouble(price);
		
		if(engine.equals("100")){
			this.consumption = 0.020747;
		}else if(engine.equals("110")){
			this.consumption = 0.017544;
		}else if(engine.equals("125")){
			this.consumption = 0.015385;
		}else if(engine.equals("150")){
			this.consumption = 0.029499;
		}else if(engine.equals("1500")){
			this.consumption = 0.047619;
		}else if(engine.equals("1800")){
			this.consumption = 0.056497;
		}else if(engine.equals("2000")){
			this.consumption = 0.064516;
		}else if(engine.equals("2400")){
			this.consumption = 0.069444;
		}else if(engine.equals("2200")){
			this.consumption = 0.048031;
		}else if(engine.equals("2500")){
			this.consumption = 0.047596;
		}else if(engine.equals("3000")){
			this.consumption = 0.050050;
		}else if(engine.equals("3200")){
			this.consumption = 0.057372;
		}
		
	}
	
	public double cal(int distsnce){
		
		double newDistance = distsnce/1000;
		
		Log.d("Log","Variable "+gasPrice+" "+newDistance+" "+consumption);
		
		this.volumn = newDistance*consumption;
		Log.d("Log","Volumn "+volumn);
		
		this.average = gasPrice*volumn;
		Log.d("Log","Average "+average);
		
		return average;
		
	}

}
