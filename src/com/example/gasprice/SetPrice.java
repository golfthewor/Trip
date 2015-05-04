package com.example.gasprice;

import android.util.Log;

public class SetPrice {

	private String Price;
	private ConnectPtt sv;
	
	public void FindPrice(String gas) {
		// TODO Auto-generated constructor stub

		Log.d("Log", "SetPrice Gas " + gas);

		try {
			sv = new ConnectPtt();
			Thread.sleep(500); 
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		try {

			if (gas.equals("Gasoline 95")) {
				setPrice(sv.getGasoline95());
			} else if (gas.equals("Gasoline 91")) {
				setPrice(sv.getGasoline91());
			} else if (gas.equals("Diesel")) {
				setPrice(sv.getDiesel());
			} else if (gas.equals("Gasohol 91")) {
				setPrice(sv.getGasohol91());
			} else if (gas.equals("Gasohol E20")) {
				setPrice(sv.getGasoholE20());
			} else if (gas.equals("NGV")) {
				setPrice(sv.getNGV());
			} else if (gas.equals("Gasohol 95")) {
				setPrice(sv.getGasohol95());
			} else if (gas.equals("Gasohol E85")) {
				setPrice(sv.getGasoholE85());
			} else if (gas.equals("HyForce Diesel")) {
				setPrice(sv.getHyForcePremiumDiesel());
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.d("Log", "Fail set price");
		}

	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Log.d("Log", "SetPrice price " + price);
		this.Price = price;
	}

}
