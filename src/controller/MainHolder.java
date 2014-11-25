package controller;

import java.awt.EventQueue;

public class MainHolder {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Controller c = new Controller();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
