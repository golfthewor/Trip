package com.example.gasprice;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.util.Log;

public class ConnectPtt extends Activity {
	
	private String Gasoline95;
	private String Gasoline91;
	private String Diesel;
	private String Gasohol91;
	private String GasoholE20;
	private String NGV;
	private String Gasohol95;
	private String GasoholE85;
	private String HyForcePremiumDiesel;

	private static String URL = "http://www.pttplc.com/webservice/pttinfo.asmx?WSDL";
	private static String NAMESPACE = "http://www.pttplc.com/ptt_webservice/";
	private static String METHOD_NAME = "CurrentOilPrice";
	private static String SOAP_ACTION = "http://www.pttplc.com/ptt_webservice/CurrentOilPrice";

	private SoapPrimitive Results = null;
	HandleService sv = new HandleService();

	public ConnectPtt(){
		Connect();
	}
	
	public void Connect() {
		Thread s = new Thread() {
			@Override
			public void run() {

				try {
					Log.d("Log","Connecting ptt service");
					SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
					request.addProperty("Language", "EN");

					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
							SoapEnvelope.VER11);
					envelope.dotNet = true;
					envelope.setOutputSoapObject(request);

					HttpTransportSE androidHttpTransport = new HttpTransportSE(
							URL);
					androidHttpTransport.call(SOAP_ACTION, envelope);
					SoapPrimitive resultRequestSOAP = (SoapPrimitive) envelope
							.getResponse();
					Results = resultRequestSOAP;
					
					sv.pareser(Results);
					SetOutput();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};
		s.start();
	}
	
	private void SetOutput() {
		// TODO Auto-generated method stub
		
		try{
			setDiesel(sv.getDiesel());
			setGasoholE20(sv.getGasoholE20());
			setGasoholE85(sv.getGasoholE85());
			setGasoline91(sv.getGasoline91());
			setGasoline95(sv.getGasoline95());
			setGasohol91(sv.getGasohol91());
			setGasohol95(sv.getGasohol95());
			setNGV(sv.getNGV());
			setHyForcePremiumDiesel(sv.getHyForcePremiumDiesel());
			Log.d("Log","Set output complete");
		}catch (Exception e) {
			e.printStackTrace();
			Log.d("Log","Set output fail");
		}
		

	}
	
	public String getGasoline95() {
		return Gasoline95;
	}

	public void setGasoline95(String gasoline95) {
		this.Gasoline95 = gasoline95;
	}

	public String getGasoline91() {
		return Gasoline91;
	}

	public void setGasoline91(String gasoline91) {
		this.Gasoline91 = gasoline91;
	}

	public String getDiesel() {
		return Diesel;
	}

	public void setDiesel(String diesel) {
		this.Diesel = diesel;
	}

	public String getGasohol91() {
		return Gasohol91;
	}

	public void setGasohol91(String gasohol91) {
		this.Gasohol91 = gasohol91;
	}

	public String getGasoholE20() {
		return GasoholE20;
	}

	public void setGasoholE20(String gasoholE20) {
		this.GasoholE20 = gasoholE20;
	}

	public String getNGV() {
		return NGV;
	}

	public void setNGV(String nGV) {
		this.NGV = nGV;
	}

	public String getGasohol95() {
		return Gasohol95;
	}

	public void setGasohol95(String gasohol95) {
		this.Gasohol95 = gasohol95;
	}

	public String getGasoholE85() {
		return GasoholE85;
	}

	public void setGasoholE85(String gasoholE85) {
		this.GasoholE85 = gasoholE85;
	}

	public String getHyForcePremiumDiesel() {
		return HyForcePremiumDiesel;
	}

	public void setHyForcePremiumDiesel(String hyForcePremiumDiesel) {
		this.HyForcePremiumDiesel = hyForcePremiumDiesel;
	}

}
