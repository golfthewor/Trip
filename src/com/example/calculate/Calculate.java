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
			this.consumption = 0.1;
		}else if(engine.equals("110")){
			this.consumption = 0.1;
		}else if(engine.equals("125")){
			this.consumption = 0.1;
		}else if(engine.equals("150")){
			this.consumption = 0.1;
		}else if(engine.equals("1500")){
			this.consumption = 0.1;
		}else if(engine.equals("1800")){
			this.consumption = 0.1;
		}else if(engine.equals("2000")){
			this.consumption = 0.1;
		}else if(engine.equals("2400")){
			this.consumption = 0.1;
		}else if(engine.equals("2200")){
			this.consumption = 0.1;
		}else if(engine.equals("2500")){
			this.consumption = 0.1;
		}else if(engine.equals("3000")){
			this.consumption = 0.1;
		}else if(engine.equals("3200")){
			this.consumption = 0.1;
		}
		
	}
	
	public double cal(double distsnce){
		
		Log.d("Log","Variable "+gasPrice+" "+distsnce+" "+consumption);
		
		this.volumn = distsnce*consumption;
		Log.d("Log","Volumn "+volumn);
		
		this.average = gasPrice*volumn;
		Log.d("Log","Average "+average);
		
		return average;
		
	}

}
